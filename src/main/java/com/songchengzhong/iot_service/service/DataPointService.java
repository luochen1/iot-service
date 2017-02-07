package com.songchengzhong.iot_service.service;

import com.songchengzhong.iot_service.entity.DataPoint;
import com.songchengzhong.iot_service.entity.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by songchengzhong on 2017/1/10.
 */
public interface DataPointService {
    /**
     * 插入数据
     * @param entity
     * @param user
     * @return
     */
    boolean insert(DataPoint entity, User user);

    /**
     * 更新数据
     * @param user
     * @param timestamp
     * @param entity
     * @return
     */
    boolean updateByTimestampAndSensorId(User user, long timestamp, DataPoint entity);

    /**
     * 删除数据
     * @param user
     * @param timestamp
     * @param sensorId
     * @return
     */
    boolean deleteByTimestampAndSensorId(User user, long timestamp, int sensorId);

    /**
     * 通过"时间戳"获取最近的一次数据
     * @param user
     * @param timestamp
     * @param sensorId
     * @return
     */
    DataPoint findByTimestampAndSensorId(User user, long timestamp, int sensorId);

    /**
     * 通过"传感器的id"找到所有的数据
     * @param user
     * @param sensorId
     * @return
     */
    List<DataPoint> findBySensorId(User user, int sensorId);

    /**
     * 通过"传感器的id"进行数据的分页查询
     * @param user
     * @param sensorId
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<DataPoint> toPagedListBySensorId(User user, int sensorId, int pageNum, int pageSize);

    /**
     * 通过"开始时间long"和"结束时间long"获得数据
     * @param user
     * @param sensorId
     * @param begin
     * @param end
     * @return
     */
    List<DataPoint> findByBeginAndEndAndSensorId(User user, int sensorId, long begin, long end);

    /**
     * 获得数值型的传感器数据的Map
     * key:data value:[1,2,3,412,5,36,36,747,747]
     * key:date value:[2015-02-16 11:23:36]
     * @param dataPoints
     * @return
     */
    Map<String, Object[]> getDataAndDateMap(List<DataPoint> dataPoints);

    /**
     * 通过Socket发来的信息进行即时数据的获取
     * @param socketMsg
     * @return
     */
    Map<String,Object> getInTimeData(String socketMsg, Date beginTime,Integer count);
}
