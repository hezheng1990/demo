package com.example.demo.mapper;

import com.example.demo.entity.Article;
import com.example.demo.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ArticleDao extends CrudRepository<Article, Integer> {


}