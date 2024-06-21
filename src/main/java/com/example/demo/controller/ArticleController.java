package com.example.demo.controller;

import com.example.demo.entity.Article;
import com.example.demo.entity.User;
import com.example.demo.mapper.ArticleDao;
import com.example.demo.mapper.UserDao;
import com.example.demo.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.demo.controller.UserController.getUserName;


@CrossOrigin("https://www.hezheng.xyz")
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleDao articleDao;


    @PostMapping("/add")
    public Result add(@RequestBody Article article) {

        if(article.getCreateTime() == null){
            article.setCreateTime(LocalDateTime.now());
        }

        article.setCreateBy(getUserName());
        articleDao.save(article);
        return Result.success("写入成功");
    }

    @GetMapping("/selectAll")
    public Result<List<Article>> selectAll() {
        return Result.success(articleDao.findAll());
    }








}
