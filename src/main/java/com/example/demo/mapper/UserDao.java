package com.example.demo.mapper;

import com.example.demo.entity.Test;
import com.example.demo.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserDao extends CrudRepository<User, Integer> {

    User findByName(String name);


}