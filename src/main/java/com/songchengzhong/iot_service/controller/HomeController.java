package com.songchengzhong.iot_service.controller;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.songchengzhong.iot_service.entity.User;
import com.songchengzhong.iot_service.service.UserService;
import com.songchengzhong.iot_service.view_model.RegisterUser;
import com.songchengzhong.iot_service.view_model.WeiXinRec;
import com.songchengzhong.iot_service.view_model.WeiXinResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;

/**
 * Created by songchengzhong on 2017/1/1.
 */
@Controller
@RequestMapping(path = {"/"})
public class HomeController {

    @Autowired
    UserService userService;

    //登录页面
    @GetMapping(path = {"/login", "/"})
    public String login() {
        return "home/login";
    }

    //登录
    @PostMapping("/login")
    public String login(String email, String password, HttpSession session) {
        User user = userService.login(email, password);
        if (user == null) {
            return "redirect:/login";
        } else {
            Date lastLoginTime = user.getLoginTime();
            user.setLoginTime(new Date());
            userService.update(user);
            user.setLoginTime(lastLoginTime);
            session.setAttribute("user", user);
            return "redirect:/profile";
        }
    }

    //注册页面
    @GetMapping("/register")
    public String showRegister(Model model) {
        RegisterUser registerUser = new RegisterUser();
        model.addAttribute("user", registerUser);
        return "home/register";
    }

    //注册
    @PostMapping("/register")
    public String register(@Valid RegisterUser registerUser, Errors errors, Model model, HttpSession session) {
        if (!errors.hasErrors() && userService.register(registerUser)) {
            User user = userService.findByEmail(registerUser.getEmail().trim());
            session.setAttribute("user", user);
            return "redirect:/profile";//重定向
        } else {
            model.addAttribute("msg", "email重复啦");
            model.addAttribute("user", registerUser);
            return "home/register";
        }
    }

    //注销
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }


}
