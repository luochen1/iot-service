package com.songchengzhong.iot_service.repository.impl;

import com.songchengzhong.iot_service.entity.Sensor;
import com.songchengzhong.iot_service.repository.SensorRepository;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by songchengzhong on 2017/1/9.
 * TODO
 */
@Repository
public class SensorRepositoryImpl implements SensorRepository {
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Override
    public boolean insert(Sensor entity) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            int result = session.insert("com.songchengzhong.iot_service.mapper.SensorMapper.insert", entity);
            session.commit();
            return result > 0;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Sensor entity) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            int result = session.update("com.songchengzhong.iot_service.mapper.SensorMapper.update", entity);
            session.commit();
            return result > 0;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(int id) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            int result = session.delete("com.songchengzhong.iot_service.mapper.SensorMapper.delete", id);
            session.commit();
            return result > 0;
        } finally {
            session.close();
        }
    }

    @Override
    public Sensor findById(int id) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            Sensor result = session.selectOne("com.songchengzhong.iot_service.mapper.SensorMapper.findById", id);
            return result;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Sensor> findAll() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Sensor> result = session.selectList("com.songchengzhong.iot_service.mapper.SensorMapper.findAll");
            return result;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Sensor> toPagedList(int pageNum, int pageSize) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Map<String, Integer> map = new HashMap<>();
            map.put("skipNum", (pageNum - 1) * pageSize);
            map.put("pageSize", pageSize);
            List<Sensor> result = sqlSession.selectList("com.songchengzhong.iot_service.mapper.SensorMapper.toPagedList", map);
            return result;
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 通过设备Id找到传感器
     *
     * @param deviceId
     * @return
     */
    @Override
    public List<Sensor> findByDeviceId(int deviceId) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Sensor> result = session.selectList("com.songchengzhong.iot_service.mapper.SensorMapper.findByDeviceId", deviceId);
            return result;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Sensor> toPagedListByDeviceId(int pageNum, int pageSize, int deviceId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Map<String, Integer> map = new HashMap<>();
            map.put("skipNum", (pageNum - 1) * pageSize);
            map.put("pageSize", pageSize);
            map.put("deviceId", deviceId);
            List<Sensor> result = sqlSession.selectList("com.songchengzhong.iot_service.mapper.SensorMapper.toPagedList", map);
            return result;
        } finally {
            sqlSession.close();
        }
    }
}
