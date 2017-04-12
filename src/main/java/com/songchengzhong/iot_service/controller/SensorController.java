package com.songchengzhong.iot_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.songchengzhong.iot_service.entity.*;
import com.songchengzhong.iot_service.service.ActionService;
import com.songchengzhong.iot_service.service.DataPointService;
import com.songchengzhong.iot_service.service.SensorService;
import com.songchengzhong.iot_service.service.SensorTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by songchengzhong on 2017/1/17.
 */
@Controller
@RequestMapping("/sensor")
public class SensorController {
    @Autowired(required = false)
    HttpSession session;

    @Autowired
    SensorTypeService sensorTypeService;

    @Autowired
    SensorService sensorService;

    @Autowired
    ActionService actionService;

    @Autowired
    DataPointService dataPointService;

    @GetMapping("/create")
    public String create(@RequestParam Integer deviceId, Model model) {
        if (!model.containsAttribute("sensor")) {
            model.addAttribute("sensor", new Sensor());
        }
        List<SensorType> sensorTypes = sensorTypeService.findAll();
        model.addAttribute("deviceId", deviceId);
        model.addAttribute("sensorTypes", sensorTypes);
        return "sensor/create";
    }

    @PostMapping("/create")
    public String create(Sensor sensor, Model model) {
        User user = (User) session.getAttribute("user");
        if (sensorService.insert(sensor, user)) {
            model.addAttribute("deviceId", sensor.getDeviceId());
            return "redirect:/device/detail/{deviceId}";
        } else {
            model.addAttribute("msg", "添加失败~");
            return "redirect:/sensor/create";
        }
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) throws JsonProcessingException {
        User user = (User) session.getAttribute("user");
        Sensor sensor = sensorService.findById(id, user);
        List<DataPoint> dataPoints = sensor.getDataPoints();//找到所有的数据
        if (dataPoints == null) {
            System.out.println(this.getClass().toString() + ":datail():dataPoints:null");
        }
        if (sensor == null) {
            return "redirect:/profile";
        } else {
            switch (sensor.getSensorTypeId()) {
                case 1://数值传感器
                    model.addAttribute("option", new ObjectMapper().writeValueAsString(dataPointService.getDataAndDateMap(dataPoints)));
                    break;
                case 2://开关
                    DataPoint dataPoint = dataPointService.findByTimestampAndSensorId(user, new Date().getTime(), sensor.getId());
                    model.addAttribute("isOpen", dataPoint != null ? dataPoint.getValue() : "0");
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
            }
            model.addAttribute("actions", actionService.findAllActionByUser(user));
            model.addAttribute("sensor", sensor);
            return "sensor/detail";
        }
    }

    @PostMapping(value = "/change-switch", produces = "application/json")
    @ResponseBody
    public Map<String, Object> changeSwitch(@RequestParam Integer sensorId, @RequestParam String isOpen) {
        User user = (User) session.getAttribute("user");
        String before = isOpen;
        boolean b = dataPointService.insert(new DataPoint(0, isOpen + "", new Date(), sensorId, null), user);
        Map<String, Object> map = new HashMap<>();
        map.put("result", b);
        if (b) {
            map.put("value", isOpen);
        } else {
            map.put("value", before);
        }
        return map;
    }

    @PostMapping("/new-sensor-action")
    public String addSensorAction(SensorAction sensorAction) {
        sensorService.addSensorAction(sensorAction);
        return "redirect:/sensor/detail/" + sensorAction.getSensorId();
    }
}
