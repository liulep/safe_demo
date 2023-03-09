package com.yue.mysql.controller;

import com.yue.common.annotation.PageNotes;
import com.yue.common.annotation.RequestNotes;
import com.yue.mysql.entity.User;
import com.yue.mysql.mapper.MysqlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PageNotes(description = "1111")
public class MysqlController {

    @Autowired
    private MysqlMapper mapper;

    @GetMapping("/")
    @RequestNotes(description = "首页",returnType = RequestMapping.class)
    public String view(){
        return "index";
    }

    @GetMapping("/login")
    public String login(User user){
        String sql="select count(*) from db_user where username='"+user.getUsername()+"' and password='"+user.getPassword()+"';";
        int login = mapper.isLogin(sql);
        if(login!=0){
            return "success";
        }
        return "fail";
    }
}
