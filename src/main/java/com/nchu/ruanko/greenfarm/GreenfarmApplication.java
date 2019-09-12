package com.nchu.ruanko.greenfarm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author GreenFarm
 */
@SpringBootApplication
@MapperScan("com.nchu.ruanko.greenfarm.dao")
public class GreenfarmApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreenfarmApplication.class, args);
    }

}