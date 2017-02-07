package com.songchengzhong.iot_service.repository;

import com.songchengzhong.iot_service.entity.DataPoint;

import java.util.Date;
import java.util.List;

/**
 * Created by songchengzhong on 2017/1/2.
 */
public interface DataPointRepository {

    boolean insert(DataPoint entity);

    boolean update(DataPoint entity);

    boolean delete(long id);

    DataPoint findById(long id);

    List<DataPoint> findAll();

    List<DataPoint> toPagedList(int pageNum, int pageSize);

    List<DataPoint> findBySensorId(int sensorId);

    List<DataPoint> toPagedListBySensorId(int pageNum, int pageSize,int sensorId);

    List<DataPoint> findByBeginAndEndAndSensorId(int sensorId, Date begin, Date end);

    DataPoint findByCreatedAtAndSensorId(DataPoint entity);

    boolean updateByCreatedAtAndSensorId(DataPoint entity);

    boolean deleteByCreatedAtAndSensorId(DataPoint entity);
}
