package com.mb.ext.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ComponentScan(basePackages = "com.mb")
@EntityScan(basePackages = ("com.mb"))
@ImportResource("classpath:spring-properties.xml")
@ServletComponentScan("com.mb.framework.web.filter")
public class B2B2CApplication {

    public static void main(String[] args) {
        SpringApplication.run(B2B2CApplication.class, args);
    }

}
