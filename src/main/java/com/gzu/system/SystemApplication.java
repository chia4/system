package com.gzu.system;

import com.gzu.system.service.IUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SystemApplication {


    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }

}
