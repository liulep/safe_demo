package com.yue.mysql.controller;

import com.yue.mysql.entity.User;
import com.yue.mysql.mapper.MysqlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MysqlController {

    @Autowired
    private MysqlMapper mapper;

    @RequestMapping("/")
    public String view(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(User user){
        String sql="select count(*) from db_user where username='"+user.getUsername()+"' and password='"+user.getPassword()+"';";
        int login = mapper.isLogin(sql);
        if(login!=0){
            return "success";
        }
        return "fail";
    }
}
