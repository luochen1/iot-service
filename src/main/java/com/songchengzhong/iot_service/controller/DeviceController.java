package com.songchengzhong.iot_service.controller;

import com.songchengzhong.iot_service.common.Strings;
import com.songchengzhong.iot_service.entity.Device;
import com.songchengzhong.iot_service.entity.User;
import com.songchengzhong.iot_service.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created by songchengzhong on 2017/1/15.
 */
@Controller
@RequestMapping("/device")
public class DeviceController {
    @Autowired
    DeviceService deviceService;

    @Autowired(required = false)
    HttpSession session;

    //显示创建设备页面
    @GetMapping("/create")
    public String create(Model model) {
        if (!model.containsAttribute("device")) {
            model.addAttribute("device", new Device());
        }
        return "device/create";
    }

    //创建设备
    @PostMapping("/create")
    public String create(Device device, RedirectAttributes model) {
        boolean result = deviceService.insert(device, (User) session.getAttribute("user"));
        if (result) {
            model.addAttribute("deviceId", device.getId());
            return "redirect:/device/detail/{deviceId}";
        } else {
            model.addFlashAttribute("msg", "添加失败,请检查~");
            return "redirect:/device/create";
        }
    }

    //展示设备列表
    @GetMapping(path = {"/detail/{deviceId}", "/detail"})
    public String detail(@PathVariable(required = false) Integer deviceId, Model model) {
        User user = (User) session.getAttribute("user");
        List<Device> devices = deviceService.findByUserId(user.getId());
        if (devices.size() > 0) {
            if (deviceId == null) {
                model.addAttribute("selectedDevice", devices.get(devices.size() - 1));
            } else {
                Optional<Device> device = devices.stream().filter(p -> p.getId() == deviceId).findFirst();
                model.addAttribute("selectedDevice", device.orElse(devices.get(0)));
            }
            model.addAttribute("devices", devices);
        } else {
            model.addAttribute("msg", "暂无任何设备!");
        }
        return "device/detail";
    }

    //修改设备页面
    @GetMapping("/edit/{deviceId}")
    public String edit(@PathVariable Integer deviceId, Model model) {
        User user = (User) session.getAttribute("user");
        Device device = null;
        if (!model.containsAttribute("device")) {
            device = deviceService.findById(deviceId, user);
        }
        if (device == null) {
            return "redirect:/profile";
        } else {
            model.addAttribute("device", device);
            return "device/edit";
        }
    }

    //修改设备
    @PostMapping("/edit/{deviceId}")
    public String edit(@PathVariable Integer deviceId, Device device, RedirectAttributes model) {
        User user = (User) session.getAttribute("user");
        device.setId(deviceId);
        model.addAttribute("deviceId", deviceId);
        if (deviceService.update(device, user)) {
            return "redirect:/device/detail/{deviceId}";
        } else {
            model.addFlashAttribute("device", device);
            return "redirect:/device/edit/{deviceId}";
        }
    }

    //删除设备
    @GetMapping("/delete/{deviceId}")
    public String delete(@PathVariable Integer deviceId) {
        User user = (User) session.getAttribute("user");
        deviceService.delete(deviceId, user);
        return "redirect:/device/detail";
    }

    //上传设备图片
    @PostMapping("/image-upload/{deviceId}")
    public String image(@RequestPart Part imageFile, @PathVariable Integer deviceId, HttpServletRequest request) throws IOException {
        if (!Strings.isNullOrEmpty(imageFile.getSubmittedFileName()) && imageFile.getSize() > 0) {
            User user = (User) session.getAttribute("user");
            deviceService.uploadImage(imageFile, deviceId, user);
        }
        return "redirect:/device/detail/" + deviceId;
    }
}
