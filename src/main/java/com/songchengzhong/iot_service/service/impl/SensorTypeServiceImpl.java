package com.songchengzhong.iot_service.service.impl;

import com.songchengzhong.iot_service.entity.SensorType;
import com.songchengzhong.iot_service.repository.SensorTypeRepository;
import com.songchengzhong.iot_service.service.SensorTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by songchengzhong on 2017/1/17.
 */
@Service
public class SensorTypeServiceImpl implements SensorTypeService {
    @Autowired
    SensorTypeRepository sensorTypeRepository;

    @Override
    public List<SensorType> findAll() {
        return sensorTypeRepository.findAll();
    }
}
