package com.songchengzhong.iot_service.service;

import com.songchengzhong.iot_service.entity.DataPoint;
import com.songchengzhong.iot_service.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by songchengzhong on 2017/1/10.
 */
public interface DataPointService {
    boolean insert(DataPoint entity, User user);

    boolean updateByTimestampAndSensorId(User user, long timestamp, DataPoint entity);

    boolean deleteByTimestampAndSensorId(User user, long timestamp, int sensorId);

    /**
     * 通过时间戳获取最近的一次数据
     */
    DataPoint findByTimestampAndSensorId(User user, long timestamp, int sensorId);

    List<DataPoint> findBySensorId(User user, int sensorId);

    List<DataPoint> toPagedListBySensorId(User user, int sensorId, int pageNum, int pageSize);

    List<DataPoint> findByBeginAndEndAndSensorId(User user, int sensorId, long begin, long end);

    /**
     * 获得数值型的传感器数据的json
     *
     * @param dataPoints
     * @return
     */
    Map<String, String> getJsonOfNumbericl(List<DataPoint> dataPoints);

}
