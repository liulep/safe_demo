package com.yue.mysql;

import com.yue.common.annotation.EnableMessageAnnotationScanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class MysqlApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(MysqlApplication.class, args);
    }
}
