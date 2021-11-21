package com.yuki.experiment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.yuki.experiment.framework.mapper")
public class SoftwareDesignApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoftwareDesignApplication.class, args);
        System.out.println("启动成功");

    }

}
