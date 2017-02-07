package com.songchengzhong.iot_service.service;

import com.songchengzhong.iot_service.entity.SensorType;

import java.util.List;

/**
 * Created by songchengzhong on 2017/1/17.
 */
public interface SensorTypeService {
    List<SensorType> findAll();
}
