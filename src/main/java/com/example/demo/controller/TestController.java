package com.example.demo.controller;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson2.JSON;
import com.example.demo.entity.Test;
import com.example.demo.mapper.TestDao;
import com.example.demo.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.UUID;

@CrossOrigin("https://www.hezheng.xyz")
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    TestDao testDao;

    @GetMapping("/add")
    public String add(@RequestParam("code") String code,@RequestParam("name") String name){
        return testDao.save(new Test(code,name)) != null ? "ok" : "error";
    }

    @GetMapping("/query")
    public Result<List<Test>> query(){
        return Result.success(testDao.findAll());
    }




    private static final String path = "/home/file/";

    private static final String password = "20231027";

    @PostMapping("/file")
    public Result<Boolean> upload(@RequestParam("file") MultipartFile file,@RequestParam String password) throws IOException {
        if(!this.password.equals(password)){
            return Result.failed("密码不正确");
        }

        String name = generateFileName(file.getOriginalFilename());
        FileOutputStream fileWrite = new FileOutputStream(path + name);
        IoUtil.copy(file.getInputStream(),fileWrite);
        fileWrite.close();
        return Result.success(true);
    }



    @GetMapping("/listFiles")
    public Result<List<String>> listFiles() {
        return Result.success(new File(path).list());
    }


    private String getExtension/*扩展名*/(String fileName) {
        if (fileName == null) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成文件名
     */
    private String generateFileName(String fileName) {
        String extension = getExtension(fileName);
        return UUID.randomUUID().toString().replace("-", "") + extension;
    }

}
