package com.songchengzhong.iot_service.repository;

import com.songchengzhong.iot_service.entity.Sensor;

import java.util.List;

/**
 * Created by songchengzhong on 2017/1/2.
 */
public interface SensorRepository extends BaseRepository<Sensor> {

    /**
     * 通过设备Id找到传感器
     *
     * @param deviceId
     * @return
     */
    List<Sensor> findByDeviceId(int deviceId);


    List<Sensor> toPagedListByDeviceId(int pageNum, int pageSize, int deviceId);
}
