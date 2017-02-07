package com.songchengzhong.iot_service.controller.api;

import com.songchengzhong.iot_service.entity.Sensor;
import com.songchengzhong.iot_service.entity.User;
import com.songchengzhong.iot_service.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by songchengzhong on 2017/1/10.
 */
@Controller
@RequestMapping(value = "/api/devices/{deviceId}/sensors", produces = "application/json;charset=UTF-8")//防止resp乱码
public class SensorApiController {
    @Autowired
    SensorService sensorService;
    @Autowired(required = false)
    HttpServletRequest request;

    @PostMapping
    public ResponseEntity<Object> insert(@RequestBody Sensor sensor, @PathVariable Integer deviceId) {
        User user = (User) request.getAttribute("user");
        sensor.setDeviceId(deviceId);
        if (sensorService.insert(sensor, user)) {
            return new ResponseEntity<>(sensor.getId(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody Sensor sensor, @PathVariable Integer deviceId, @PathVariable Integer id) {
        User user = (User) request.getAttribute("user");
        sensor.setId(id);
        sensor.setDeviceId(deviceId);
        if (sensorService.update(sensor, user)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer deviceId, @PathVariable Integer id) {
        User user = (User) request.getAttribute("user");
        if (sensorService.delete(id, user, deviceId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Integer deviceId, @PathVariable Integer id) {
        User user = (User) request.getAttribute("user");
        Sensor device = sensorService.findById(id, user, deviceId);
        if (device != null) {
            return new ResponseEntity<>(device, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Object> findAll(@PathVariable Integer deviceId, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        List<Sensor> devices = sensorService.findAll(user, deviceId);
        if (devices != null && devices.size() > 0) {
            return new ResponseEntity<>(devices, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
