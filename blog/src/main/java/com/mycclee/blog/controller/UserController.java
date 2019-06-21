package com.mycclee.blog.controller;

import com.mycclee.blog.beans.User;
import com.mycclee.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author 52489
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ModelAndView list(Model model) {
        List<User> userList = userService.getUserList();
        model.addAttribute("title", "用户管理");
        model.addAttribute("userList", userList);
        return new ModelAndView("user/list", "userModel", model);
    }

    //创建表单页面
    @GetMapping("/add")
    public ModelAndView createForm(Model model) {
        List<User> userList = userService.getUserList();
        model.addAttribute("title", "创建用户");
        model.addAttribute("user", new User());
        return new ModelAndView("user/add", "userModel", model);
    }

    @PostMapping
    public ModelAndView addOrUpdateUser(User user) {
        userService.saveOrUpdateUser(user);
        return new ModelAndView("redirect:/user");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        userService.removeUser(id);
        return new ModelAndView("redirect:/user");
    }

    @GetMapping("/modify/{id}")
    public ModelAndView update(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user",user);
        model.addAttribute("title","修改用户");
        return new ModelAndView("user/edit","userModel",model);
    }

}
