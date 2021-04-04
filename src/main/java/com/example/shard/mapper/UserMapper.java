package com.example.shard.mapper;

import com.example.shard.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


public interface UserMapper {




    @Insert({"insert into user(nickname, birthday, gender,password) values(#{u.nickname}, #{u.birthday, jdbcType=TIMESTAMP}, #{u.nickname}, #{u.password})"})
    @Options(keyProperty="u.id",useGeneratedKeys=true)
    int addUser(@Param("u")User user);

    @Select("select * from user")
    List<User> findUsers();
}
