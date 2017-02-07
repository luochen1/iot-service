package com.songchengzhong.iot_service.repository.impl;

import com.songchengzhong.iot_service.entity.Device;
import com.songchengzhong.iot_service.repository.DeviceRepository;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by songchengzhong on 2017/1/5.
 */
@Repository
public class DeviceRepositoryImpl implements DeviceRepository {
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Override
    public boolean insert(Device entity) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            int result = session.insert("com.songchengzhong.iot_service.mapper.DeviceMapper.insert", entity);
            session.commit();
            return result > 0;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Device entity) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            int result = session.update("com.songchengzhong.iot_service.mapper.DeviceMapper.update", entity);
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
            int result = session.delete("com.songchengzhong.iot_service.mapper.DeviceMapper.delete", id);
            session.commit();
            return result > 0;
        } finally {
            session.close();
        }
    }

    @Override
    public Device findById(int id) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            Device result = session.selectOne("com.songchengzhong.iot_service.mapper.DeviceMapper.findById", id);
            return result;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Device> findAll() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            List<Device> result = sqlSession.selectList("com.songchengzhong.iot_service.mapper.DeviceMapper.findAll");
            return result;
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public List<Device> toPagedList(int pageNum, int pageSize) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Map<String, Integer> map = new HashMap<>();
            map.put("skipNum", (pageNum - 1) * pageSize);
            map.put("pageSize", pageSize);
            List<Device> result = sqlSession.selectList("com.songchengzhong.iot_service.mapper.DeviceMapper.toPagedList", map);
            return result;
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public List<Device> findByUserId(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            List<Device> result = sqlSession.selectList("com.songchengzhong.iot_service.mapper.DeviceMapper.findByUserId", id);
            return result;
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public List<Device> toPagedListByUserId(int pageNum, int pageSize, int userId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Map<String, Integer> map = new HashMap<>();
            map.put("skipNum", (pageNum - 1) * pageSize);
            map.put("pageSize", pageSize);
            map.put("userId", userId);
            List<Device> result = sqlSession.selectList("com.songchengzhong.iot_service.mapper.DeviceMapper.toPagedList", map);
            return result;
        } finally {
            sqlSession.close();
        }
    }
}
