package com.example.shard;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.example.shard.mapper")
public class ShardApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardApplication.class, args);
    }

}
