package com.yue.csrf.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class CSRFController {

    //借助XSS当作支付系统

    @RequestMapping("/toPay")
    public String toPayView(HttpServletResponse response){
        return "pay";
    }

}
