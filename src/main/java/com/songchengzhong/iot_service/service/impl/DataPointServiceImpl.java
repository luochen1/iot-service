package com.songchengzhong.iot_service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.songchengzhong.iot_service.entity.DataPoint;
import com.songchengzhong.iot_service.entity.Sensor;
import com.songchengzhong.iot_service.entity.User;
import com.songchengzhong.iot_service.repository.DataPointRepository;
import com.songchengzhong.iot_service.repository.SensorRepository;
import com.songchengzhong.iot_service.service.DataPointService;
import com.songchengzhong.iot_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by songchengzhong on 2017/1/10.
 */
@Service
public class DataPointServiceImpl implements DataPointService {
    @Autowired
    DataPointRepository dataPointRepository;

    @Autowired
    SensorRepository sensorRepository;

    @Autowired
    UserService userService;

    @Override
    public boolean insert(DataPoint entity, User user) {
        if (sensorRepository.findById(entity.getSensorId()).getDevice().getUserId() == user.getId()) {
            if (entity.getCreatedAt() == null) {
                entity.setCreatedAt(new Date());//如果没有创建时间的话
            }
            return dataPointRepository.insert(entity);
        }
        return false;
    }

    @Override
    public boolean updateByTimestampAndSensorId(User user, long timestamp, DataPoint entity) {
        if (sensorRepository.findById(entity.getSensorId()).getDevice().getUserId() == user.getId()) {
            entity.setCreatedAt(new Date(timestamp));
            //entity 应该有sensorId,创建时间,以及 数据value
            return dataPointRepository.updateByCreatedAtAndSensorId(entity);
        }
        return false;
    }

    @Override
    public boolean deleteByTimestampAndSensorId(User user, long timestamp, int sensorId) {
        if (sensorRepository.findById(sensorId).getDevice().getUserId() == user.getId()) {
            DataPoint dataPoint = new DataPoint();
            dataPoint.setCreatedAt(new Date(timestamp));
            dataPoint.setSensorId(sensorId);
            return dataPointRepository.deleteByCreatedAtAndSensorId(dataPoint);
        }
        return false;
    }

    @Override
    public DataPoint findByTimestampAndSensorId(User user, long timestamp, int sensorId) {
        if (sensorRepository.findById(sensorId).getDevice().getUserId() == user.getId()) {
            DataPoint dataPoint = new DataPoint();
            dataPoint.setCreatedAt(new Date(timestamp));
            dataPoint.setSensorId(sensorId);
            return dataPointRepository.findByCreatedAtAndSensorId(dataPoint);
        }
        return null;
    }

    @Override
    public List<DataPoint> findBySensorId(User user, int sensorId) {
        if (sensorRepository.findById(sensorId).getDevice().getUserId() == user.getId()) {
            return dataPointRepository.findBySensorId(sensorId);
        }
        return null;
    }

    @Override
    public List<DataPoint> toPagedListBySensorId(User user, int sensorId, int pageNum, int pageSize) {
        if (sensorRepository.findById(sensorId).getDevice().getUserId() == user.getId()) {
            return dataPointRepository.toPagedListBySensorId(pageNum, pageSize, sensorId);
        }
        return null;
    }

    @Override
    public List<DataPoint> findByBeginAndEndAndSensorId(User user, int sensorId, long begin, long end) {
        if (sensorRepository.findById(sensorId).getDevice().getUserId() == user.getId()) {
            return dataPointRepository.findByBeginAndEndAndSensorId(sensorId, new Date(begin), new Date(end));
        }
        return null;
    }

    @Override
    public Map<String, Object[]> getDataAndDateMap(List<DataPoint> dataPoints) {
        Map<String, Object[]> map = new HashMap<>();
        Object[] data = dataPoints.stream().map(p -> Double.parseDouble(p.getValue())).toArray();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Object[] date = dataPoints.stream()
                .map(p -> dateFormat.format(p.getCreatedAt()))
                .toArray();
        map.put("data", data);
        map.put("date", date);
        return map;
    }

    @Override
    public Map<String, Object> getInTimeData(String socketMsg, Date beginTime, Integer count) {
        String[] strings = socketMsg.split(":");
        if (strings.length == 3) {//有三个参数
            String apikey = strings[1];
            User user = userService.findByApiKey(apikey);//找到用户
            if (user != null) {
                int sensorId = Integer.parseInt(strings[2]);
                Sensor sensor = sensorRepository.findById(sensorId);//找到传感器
                if (sensor != null && sensor.getDevice().getUserId() == user.getId()) {
                    List<DataPoint> dataPoints = dataPointRepository.findByBeginTimeAndSensorId(beginTime, sensorId);
                    if (dataPoints != null && dataPoints.size() > 0) {
                        Map<String, Object[]> dataAndDateMap = getDataAndDateMap(dataPoints);
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            String s = mapper.writeValueAsString(dataAndDateMap);
                            Map<String, Object> resultMap = new HashMap<>();
                            resultMap.put("returnMsg", s);
                            resultMap.put("returnCount", dataPoints.size());
                            return resultMap;
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }
}
