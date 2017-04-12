package com.songchengzhong.iot_service.service.impl;

import com.songchengzhong.iot_service.entity.Device;
import com.songchengzhong.iot_service.entity.Sensor;
import com.songchengzhong.iot_service.entity.SensorAction;
import com.songchengzhong.iot_service.entity.User;
import com.songchengzhong.iot_service.repository.DeviceRepository;
import com.songchengzhong.iot_service.repository.SensorActionRepository;
import com.songchengzhong.iot_service.repository.SensorRepository;
import com.songchengzhong.iot_service.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by songchengzhong on 2017/1/10.
 */
@Service
public class SensorServiceImpl implements SensorService {
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private SensorActionRepository sensorActionRepository;

    @Override
    public boolean insert(Sensor entity, User user) {
        entity.setCreatedAt(new Date());
        Device device = deviceRepository.findById(entity.getDeviceId());
        System.out.println(device.getUserId());
        if (device.getUserId() == user.getId()) {
            return sensorRepository.insert(entity);
        }
        return false;
    }

    @Override
    public boolean update(Sensor entity, User user) {
        Device device = deviceRepository.findById(entity.getDeviceId());
        if (device.getUserId() == user.getId()) {
            Sensor sensor = sensorRepository.findById(entity.getId());
            if (sensor.getDevice().getUserId() == user.getId()) {
                sensor.setName(entity.getName());
                sensor.setDescription(entity.getDescription());
                sensor.setUnit(entity.getUnit());
                sensor.setSymbol(entity.getSymbol());
                return sensorRepository.update(sensor);//更新查询出来的那个entity
            }
        }
        return false;
    }

    @Override
    public boolean delete(int id, User user, int deviceId) {
        Device device = deviceRepository.findById(deviceId);
        if (device.getUserId() == user.getId()) {
            Sensor sensor = sensorRepository.findById(id);
            if (sensor.getDevice().getUserId() == user.getId()) {
                return sensorRepository.delete(id);
            }
        }
        return false;
    }

    @Override
    public Sensor findById(int id, User user) {
        Sensor sensor = sensorRepository.findById(id);
        if (sensor.getDevice().getUserId() == user.getId()) {
            return sensor;
        } else {
            return null;
        }
    }

    @Override
    public Sensor findById(int id, User user, int deviceId) {
        Device device = deviceRepository.findById(deviceId);
        if (device.getUserId() == user.getId()) {
            Sensor sensor = sensorRepository.findById(id);
            if (sensor.getDevice().getUserId() == user.getId()) {
                return sensorRepository.findById(id);
            }
        }
        return null;
    }

    @Override
    public List<Sensor> findAll(User user, int deviceId) {
        Device device = deviceRepository.findById(deviceId);
        if (device.getUserId() == user.getId()) {
            return sensorRepository.findByDeviceId(deviceId);
        }
        return null;
    }

    @Override
    public List<Sensor> toPagedList(int pageNum, int pageSize, User user, int deviceId) {
        Device device = deviceRepository.findById(deviceId);
        if (device.getUserId() == user.getId()) {
            return sensorRepository.toPagedListByDeviceId(pageNum, pageSize, deviceId);
        }
        return null;
    }

    @Override
    public List<Sensor> findSwitchSensorByUser(User user) {
        List<Device> devices = deviceRepository.findByUserId(user.getId());
        List<Sensor> sensors = new LinkedList<>();
        for (Device device : devices) {
            sensors.addAll(device.getSensors().stream().filter(p -> p.getSensorTypeId() == 2).collect(Collectors.toList()));
        }
        return sensors;
    }

    @Override
    public void addSensorAction(SensorAction sensorAction){
        sensorActionRepository.insert(sensorAction);
    }
}
