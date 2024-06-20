package com.example.demo.controller;

import com.example.demo.entity.Article;
import com.example.demo.entity.User;
import com.example.demo.mapper.ArticleDao;
import com.example.demo.mapper.UserDao;
import com.example.demo.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin("https://www.hezheng.xyz")
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleDao articleDao;


    @PostMapping("/add")
    public Result add(@RequestBody Article article) {
        articleDao.save(article);
        return Result.success("写入成功");
    }

    @GetMapping("/selectAll")
    public Result<List<Article>> selectAll() {
        return Result.success(articleDao.findAll());
    }








}
