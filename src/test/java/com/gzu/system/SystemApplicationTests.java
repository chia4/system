package com.gzu.system;

import com.gzu.system.mapper.UserLoginMapper;
import com.gzu.system.pojo.UserLogin;
import com.gzu.system.service.IUserLoginService;
import com.gzu.system.service.impl.UserLoginService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
class SystemApplicationTests {
    @Test
    public void testReg(){

    }
}
