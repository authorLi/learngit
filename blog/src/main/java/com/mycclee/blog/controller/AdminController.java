package com.mycclee.blog.controller;

import com.mycclee.blog.vo.Menu;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public ModelAndView listUsers(Model model){
        List<Menu> list = new ArrayList<>();
        list.add(new Menu("用户管理","/user"));
        list.add(new Menu("博客管理","/blogs"));
        list.add(new Menu("评论管理","/comments/list"));
        model.addAttribute("list",list);
        return new ModelAndView("admins/index","model",model);
    }
}

