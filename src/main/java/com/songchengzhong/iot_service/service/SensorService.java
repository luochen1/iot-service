package com.songchengzhong.iot_service.service;

import com.songchengzhong.iot_service.entity.Sensor;
import com.songchengzhong.iot_service.entity.User;

import java.util.List;

/**
 * Created by songchengzhong on 2017/1/10.
 */
public interface SensorService {
    boolean insert(Sensor entity, User user);

    boolean update(Sensor entity, User user);

    boolean delete(int id, User user, int deviceId);

    Sensor findById(int id, User user);

    Sensor findById(int id, User user, int deviceId);

    List<Sensor> findAll(User user, int deviceId);

    List<Sensor> toPagedList(int pageNum, int pageSize, User user, int deviceId);
}
