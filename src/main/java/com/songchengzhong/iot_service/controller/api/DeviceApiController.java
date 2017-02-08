package com.songchengzhong.iot_service.controller.api;

import com.songchengzhong.iot_service.entity.Device;
import com.songchengzhong.iot_service.entity.User;
import com.songchengzhong.iot_service.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by songchengzhong on 2017/1/5.
 */
@RestController
@RequestMapping(value = "/api/devices", produces = "application/json;charset=UTF-8")//防止resp乱码
public class DeviceApiController {
    @Autowired
    DeviceService deviceService;
    @Autowired(required = false)
    HttpServletRequest request;

    @PostMapping
    public ResponseEntity<Object> insert(@RequestBody Device device) {
        User user = (User) request.getAttribute("user");
        if (deviceService.insert(device, user)) {
            return new ResponseEntity<>(device.getId(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody Device device, @PathVariable Integer id) {
        User user = (User) request.getAttribute("user");
        device.setId(id);
        if (deviceService.update(device, user)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        User user = (User) request.getAttribute("user");
        if (deviceService.delete(id, user)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Integer id) {
        User user = (User) request.getAttribute("user");
        Device device = deviceService.findById(id, user);
        if (device != null) {
            return new ResponseEntity<>(device, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        User user = (User) request.getAttribute("user");
        List<Device> devices = deviceService.findAll(user);
        if (devices != null && devices.size() > 0) {
            return new ResponseEntity<>(devices, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
