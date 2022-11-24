package com.gzu.system;

import com.gzu.system.service.IUserLoginService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SystemApplicationTests {

    @Autowired
    private IUserLoginService userLoginService;

    @Test
    public void testTypeError(){
        //���ʹ��󣬷���2
        int re = userLoginService.register("lwjb", "123", "PEOPLEs");
        System.out.println(re);
    }


    @Test
    public void testRegUserExists(){
        //ע���û���ռ�ã�����1
        int re = userLoginService.register("lwj", "123", "PEOPLE");
        System.out.println(re);
    }


    @Test
    public void testReg(){
        //lwj 123 PEOPLE
        //userLoginService.register("lwj","123","PEOPLE");
        //lwjt 123 PLACE
        //userLoginService.register("lwjt","123","PLACE");
        //lwjtt 123 AGENCY
        userLoginService.register("lwjtt","123","AGENCY");
    }


    @Test
    public void testLoginSuccess(){
        //�ɹ���¼���û�����ΪPEOPLE������0
        int lwj = userLoginService.login("lwj", "123");
        int lwjt = userLoginService.login("lwjt", "123");
        int lwjtt = userLoginService.login("lwjtt", "123");
        System.out.println(lwj);
        System.out.println(lwjt);
        System.out.println(lwjtt);
    }

    @Test
    public void testLoginPasswordError(){
        //������󣬷���3
        int lwj = userLoginService.login("lwj", "1234");
        System.out.println(lwj);
    }

    @Test
    public void testLoginUserNotExists(){
        //�û������ڣ�����4
        int lwja = userLoginService.login("lwja", "123");
        System.out.println(lwja);
    }

    @Test
    public void testOtherError(){

    }



}
