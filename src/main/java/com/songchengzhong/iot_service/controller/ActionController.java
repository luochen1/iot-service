package com.songchengzhong.iot_service.controller;

import com.songchengzhong.iot_service.entity.Action;
import com.songchengzhong.iot_service.entity.Sensor;
import com.songchengzhong.iot_service.entity.User;
import com.songchengzhong.iot_service.service.ActionService;
import com.songchengzhong.iot_service.service.SensorService;
import com.songchengzhong.iot_service.viewmodel.AddAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * Created by songchengzhong on 2017/3/20.
 */
@Controller
@RequestMapping("/action")
public class ActionController {

    @Autowired
    ActionService actionService;

    @Autowired
    SensorService sensorService;

    /**
     * 显示所有的用户动作
     *
     * @return
     */
    @GetMapping("/list")
    public String list(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        List<Action> actions = actionService.findAllActionByUser(user);
        model.addAttribute("actions", actions);
        return "action/list";
    }

    /**
     * 删除用户设置的动作
     *
     * @param id
     * @return
     */
    @GetMapping("/delete/{actionId}")
    public String delete(@PathVariable("actionId") Optional<Integer> id) {
        actionService.deleteById(id.orElse(0));
        return "redirect:/action/list";
    }

    /**
     * 添加动作界面
     *
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/new")
    public String add(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        List<Sensor> sensors = sensorService.findSwitchSensorByUser(user);
        model.addAttribute("sensors", sensors);
        return "action/add";
    }

    /**
     * Post:添加动作
     *
     * @param addAction
     * @return
     */
    @PostMapping("/new")
    public String add(HttpSession session, AddAction addAction, HttpServletRequest request) {
        User user = (User) session.getAttribute("user");
        actionService.add(user, addAction, request.getContextPath());
        return "redirect:/action/list";
    }
}
