package com.gzu.system.service;

import com.gzu.system.mapper.UserLoginMapper;
import com.gzu.system.pojo.UserLogin;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Service
public class UserLoginService {

    private UserLoginMapper mapper;


    /**
     * 利用java原生的摘要实现SHA256加密
     *
     * @param str 密码
     * @return 加密后的报文
     */
    public String getSHA256StrJava(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * 将byte转为16进制
     *
     * @param bytes
     * @return
     */
    private String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                // 1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }


    public int register(String username, String password, String type) {
        //1.根据用户名查看用户是否被占用
        UserLogin userLoginBySelect = mapper.selectByUserName(username);
        //如果userLogin不为null
        if (userLoginBySelect != null) {
            return 1;
        }

        //2.没被占用的用户名，将其密码哈希处理
        String hashPassword = getSHA256StrJava(password);

        //3.检查type类型是否正确
        UserLogin.type myType = null;
        try {
            myType = Enum.valueOf(UserLogin.type.class, type.toUpperCase());
        } catch (Exception e) {
            return 2;
        }

//        if(!(myType.equals(UserLogin.type.PEOPLE)||myType.equals(UserLogin.type.PLACE)||myType.equals(UserLogin.type.AGENCY))){
//            return 2;
//        }
        //3.插入user_login,根据情况报错
        try {
            UserLogin userLogin = new UserLogin();
            userLogin.setUsername(username);
            userLogin.setPasswordHash(hashPassword);
            userLogin.setType(myType);
            mapper.insert(userLogin);

        } catch (Exception e) {
            return 2;
        }
        return 0;
    }

    public int login(String username, String password) {

        UserLogin userLogin = null;
        try {
            //1.查询用户是否存在
            userLogin = mapper.selectByUserName(username);
            //用户不存在，返回4
            if (userLogin == null) {
                return 4;
            }
            //2.用户存在,检查密码是否正确
            //密码不正确
            if (!userLogin.getPasswordHash().equals(getSHA256StrJava(password))) {
                return 3;
            }
            //3.密码正确，根据用户类型返回值
            switch (userLogin.getType()) {
                case PEOPLE:
                    return 0;
                case PLACE:
                    return 1;
                case AGENCY:
                    return 2;
            }

        } catch (Exception e) {
            //4.出现其他错误
            return 5;
        }

        //5.异常执行
        return 5;

    }


}
