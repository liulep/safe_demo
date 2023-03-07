package com.yue.xss.controller;

import com.yue.xss.entity.PayParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class XSSController {

    @RequestMapping("/one")
    public String toView(Model model,HttpServletResponse response){
        model.addAttribute("keyword","");
        Cookie cookie = new Cookie("cookie","这是你的cookie");
        response.addCookie(cookie);
        return "one";
    }

    @RequestMapping("/test")
    public void test(@RequestParam("p")int p, @RequestParam("q")int q, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter writer = response.getWriter();
        response.setCharacterEncoding("utf-8");
        writer.write("<script>alert(\"反射型 XSS 攻击\")</script>");
        writer.close();

    }

    @RequestMapping("/search")
    public String search(@RequestParam("keyword")String keyword,Model model,HttpServletResponse response){
        model.addAttribute("keyword",keyword);
        Cookie cookie = new Cookie("cookie","这是你的cookie");
        response.addCookie(cookie);
        return "one";
    }

    /**
     * 反射型XSS: 恶意代码存放在URL里
     * 存储型XSS: 恶意代码存放在数据库中
     */

    private Map<String,String> results=new HashMap<>();
    private List<String> lists=new ArrayList<>();

    @RequestMapping("/two")
    public String two(Model model){
        model.addAttribute("lists",lists);
        return "two";
    }

    @RequestMapping("/two/add")
    public String add(@RequestParam("value")String value,Model model){
        lists.add(value);
        model.addAttribute("lists",lists);
        return "two";
    }

    @RequestMapping("/xss")
    public void xss(String c,HttpServletResponse response) throws IOException {
        System.out.println(c);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.println("<script>alert(\"这是XSS攻击！获取到了你的cookie:"+c+"\")</script>");
        writer.close();
    }

    @RequestMapping("/three")
    public String there(HttpServletResponse response){
        Cookie cookie = new Cookie("cookie","这是你的cookie");
        response.addCookie(cookie);
        return "three";
    }

    @RequestMapping("/pay")
    public void pay(PayParam param,HttpServletResponse response,@CookieValue("cookie")String cookie) throws IOException {
        System.out.println(cookie);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        if(!cookie.equals("647528310")){
            writer.println("<script>alert(\"身份验证失败，请重新操作\")</script>");
        }
        else writer.println("<script>alert(\""+param.getFrom()+"成功给"+param.getTo()+"转账"+param.getMoney()+"元\")</script>");
    }

    @RequestMapping("/toPayView")
    public String toPayView(HttpServletResponse response){
        Cookie cookie = new Cookie("cookie","647528310");
        response.addCookie(cookie);
        return "pay";
    }

}
