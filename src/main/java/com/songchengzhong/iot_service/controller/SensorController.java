package com.songchengzhong.iot_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.songchengzhong.iot_service.entity.DataPoint;
import com.songchengzhong.iot_service.entity.Sensor;
import com.songchengzhong.iot_service.entity.SensorType;
import com.songchengzhong.iot_service.entity.User;
import com.songchengzhong.iot_service.service.DataPointService;
import com.songchengzhong.iot_service.service.SensorService;
import com.songchengzhong.iot_service.service.SensorTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

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
        if(dataPoints == null){
            System.out.println("null");
        }
        if (sensor == null) {
            return "redirect:/profile";
        } else {
            switch (sensor.getSensorTypeId()) {
                case 1://数值传感器
                    model.addAttribute("option", new ObjectMapper().writeValueAsString(dataPointService.getDataAndDateMap(dataPoints)));
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
            }
            model.addAttribute("sensor", sensor);
            return "sensor/detail";
        }

    }
}
