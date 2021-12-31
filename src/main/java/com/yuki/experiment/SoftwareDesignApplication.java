package com.yuki.experiment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableTransactionManagement
//@MapperScan("com.yuki.experiment.framework.mapper.mysql")
public class SoftwareDesignApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoftwareDesignApplication.class, args);
        System.out.println("启动成功");

    }

}
