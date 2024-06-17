package com.flx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages= {"com.flx"})
@EnableScheduling
public class TaiWanFoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaiWanFoodApplication.class, args);
    }

}
