package com.songchengzhong.iot_service.repository;

import com.songchengzhong.iot_service.entity.SensorAction;

import java.util.List;

/**
 * Created by songchengzhong on 2017/3/20.
 */
public interface SensorActionRepository extends BaseRepository<SensorAction> {
    List<SensorAction> findBySensorId(int sensorId);
}
