package com.example.demo.controller;

import cn.hutool.http.HttpResponse;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserDao;
import com.example.demo.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private HttpServletResponse httpResponse;

    @Autowired
    private HttpServletRequest request;


    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserDao userDao;

    public static Map<String,User> userMap = new HashMap<>();


    public static String getUserName(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String values = request.getHeader("x-header");
            User user = userMap.get(values);
            if (user != null) {
                return user.getName();
            }
        }
        return null;
    }


    public User getUser() {
        String token = request.getHeader("x-header");
        return userMap.get(token);
    }


    @PostMapping("/login")
    public Result<String> login(@RequestBody User user) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.where(cb.and(
                cb.equal(root.get("name"), user.getName()),
                cb.equal(root.get("password"), user.getPassword())
        ));

        List<User> userList = entityManager.createQuery(query).getResultList();

        if(userList.size() > 0) {
            String key = UUID.randomUUID().toString().replace("-","");
            userMap.put(key,user);

            Cookie cookie = new Cookie("x-header", key);
            cookie.setPath("/");
            cookie.setMaxAge(7*24*60*60); // 设置 Cookie 的过期时间，单位为秒
            httpResponse.addCookie(cookie);

            return Result.success(key);
        }


        return Result.failed("failed");
    }


    @PostMapping("/registerOrLogin")
    public Result<String> register(@RequestBody User user) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.where(cb.and(
                cb.equal(root.get("name"), user.getName())
        ));

        List<User> userList = entityManager.createQuery(query).getResultList();

        if(userList.size() > 0) {
            return login(user);
        }else{

            User user2 = userDao.save(user);
            String key = UUID.randomUUID().toString().replace("-","");

            userMap.put(key,user2);

            Cookie cookie = new Cookie("x-header", key);
            cookie.setMaxAge(7*24*60*60); // 设置 Cookie 的过期时间，单位为秒
            httpResponse.addCookie(cookie);
            return Result.success(key);
        }



    }


}
