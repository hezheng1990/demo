package com.example.demo.mapper;

import com.example.demo.entity.Test;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TestDao extends CrudRepository<Test, Integer> {


}