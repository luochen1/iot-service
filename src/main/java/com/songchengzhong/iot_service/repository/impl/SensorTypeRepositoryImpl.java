package com.songchengzhong.iot_service.repository.impl;

import com.songchengzhong.iot_service.entity.Sensor;
import com.songchengzhong.iot_service.entity.SensorType;
import com.songchengzhong.iot_service.repository.SensorTypeRepository;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by songchengzhong on 2017/1/9.
 */
@Repository
public class SensorTypeRepositoryImpl implements SensorTypeRepository {
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Override
    public boolean insert(SensorType entity) {
        return false;
    }

    @Override
    public boolean update(SensorType entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public SensorType findById(int id) {
        return null;
    }

    @Override
    public List<SensorType> findAll() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<SensorType> result = session.selectList("com.songchengzhong.iot_service.mapper.SensorTypeMapper.findAll");
            return result;
        } finally {
            session.close();
        }
    }

    @Override
    public List<SensorType> toPagedList(int pageNum, int pageSize) {
        return null;
    }
}
