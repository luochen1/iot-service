package com.songchengzhong.iot_service.controller;

import com.songchengzhong.iot_service.entity.User;
import com.songchengzhong.iot_service.service.UserService;
import com.songchengzhong.iot_service.viewmodel.RegisterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;

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
        if (user != null && user.isActive()) {
            Date lastLoginTime = user.getLoginTime();
            user.setLoginTime(new Date());
            userService.update(user);
            user.setLoginTime(lastLoginTime);
            session.setAttribute("user", user);
            return "redirect:/profile";
        } else {
            return "redirect:/login";
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
            return "redirect:/login";
        } else {
            model.addAttribute("msg", "email重复啦");
            model.addAttribute("user", registerUser);
            return "home/register";
        }
    }

    //验证邮箱
    @GetMapping("/register/active")
    public String registerVerify(String email, String code, HttpSession session) {
        User user = userService.verifyActiveCode(email, code);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/profile";
        } else {
            return "email/error";
        }
    }

    //注销
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}
