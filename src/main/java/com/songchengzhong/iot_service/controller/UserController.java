package com.songchengzhong.iot_service.controller;

import com.songchengzhong.iot_service.entity.User;
import com.songchengzhong.iot_service.service.UserService;
import com.songchengzhong.iot_service.common.UUIDs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by songchengzhong on 2017/1/15.
 */
@Controller
@RequestMapping("/profile")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired(required = false)
    HttpSession session;

    //显示账户设置页面
    @GetMapping
    public String profile(Model model) {
        int userId = ((User) session.getAttribute("user")).getId();
        User user = userService.findById(userId);
        session.setAttribute("user", user);
        model.addAttribute("user", user);
        return "user/profile";
    }

    //重新生成api-key
    @PostMapping(path = "/generate-apikey", produces = "text/plain;charset='utf-8'")
    @ResponseBody
    public String generateApikey(String apikey) {
        User sessionUser = (User) session.getAttribute("user");
        String newApiKey = UUIDs.getUUID().toString();
        User user = userService.findById(sessionUser.getId());
        if (user.getApikey().equals(apikey)) {
            user.setApikey(newApiKey);
            boolean result = userService.update(user);
            if (result) {
                return newApiKey;
            } else {
                return "好像出错了,请刷新本页面再试";
            }
        } else {
            return "不匹配的用户名/APIKEY";
        }
    }

    //更新个人说明
    @PostMapping("/update")
    public String update(String introduction) {
        User sessionUser = (User) session.getAttribute("user");
        User user = userService.findById(sessionUser.getId());
        user.setIntroduction(introduction);
        userService.update(user);
        return "redirect:/profile";
    }

    //修改密码页面
    @GetMapping("/password")
    public String password(Model model) {
        return "user/password";
    }

    //修改密码
    @PostMapping("/password")
    public String password(String oldPassword, String newPassword, RedirectAttributes model) {
        int userId = ((User) session.getAttribute("user")).getId();
        User user = userService.findById(userId);
        String password = DigestUtils.md5DigestAsHex(oldPassword.trim().getBytes());
        if (user.getPassword().equals(password)) {
            user.setPassword(DigestUtils.md5DigestAsHex(newPassword.trim().getBytes()));
            userService.update(user);
            model.addFlashAttribute("msg", "密码修改成功~");
            return "redirect:/profile/password";
        } else {
            model.addFlashAttribute("msg", "好像原密码输错了哟~");
            return "redirect:/profile/password";
        }
    }
}
