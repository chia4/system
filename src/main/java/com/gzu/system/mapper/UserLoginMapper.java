package com.gzu.system.mapper;

import com.gzu.system.pojo.UserLogin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserLoginMapper {
    /**
     * 向数据增加一条user用户
     * @param userLogin
     * @return 1 or 0
     * 1代表插入成功 0代表插入失败
     */
    int insert(UserLogin userLogin);

    /**
     * 根据用户名查询一条记录
     * @return
     */
    UserLogin selectByUserName(String username);

}
