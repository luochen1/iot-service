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
import com.songchengzhong.iot_service.socket.DatapointHandler;
import com.songchengzhong.iot_service.view_model.SocketUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
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
    public void sendSocketMsg(String mapKey, DataPoint dataPoint) throws IOException {
        WebSocketSession session = DatapointHandler.sessionMap.get(mapKey);
        if (session != null) {
            session.sendMessage(new TextMessage("{\"data\":" + dataPoint.getValue()
                    + ",\"date\":\"" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(dataPoint.getCreatedAt()) + "\"}"));
        }
    }
}
