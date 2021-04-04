package com.example.shard.controller;

import com.example.shard.entity.User;
import com.example.shard.mapper.UserMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Random;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserMapper userMapper;

    @GetMapping("/add")
    public String insert(){
        User user = new User();
        user.setGender("man");
        user.setNickname("选手"+ new Random().nextInt());
        user.setPassword("密码");
        user.setBirthday(LocalDateTime.now());
        userMapper.addUser(user);
        return "success";
    }

    @GetMapping("/all")
    public Object all(){
        return userMapper.findUsers();
    }

}
