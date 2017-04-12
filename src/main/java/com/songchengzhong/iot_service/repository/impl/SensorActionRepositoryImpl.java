package com.songchengzhong.iot_service.repository.impl;

import com.songchengzhong.iot_service.entity.SensorAction;
import com.songchengzhong.iot_service.repository.SensorActionRepository;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by songchengzhong on 2017/3/20.
 */
@Repository
public class SensorActionRepositoryImpl implements SensorActionRepository {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public boolean insert(SensorAction entity) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            int result = session.insert("com.songchengzhong.iot_service.mapper.SensorActionMapper.insert", entity);
            session.commit();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public boolean update(SensorAction entity) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            int result = session.update("com.songchengzhong.iot_service.mapper.SensorActionMapper.update", entity);
            session.commit();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            int result = session.delete("com.songchengzhong.iot_service.mapper.SensorActionMapper.delete", id);
            session.commit();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public SensorAction findById(int id) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SensorAction result = session.selectOne("com.songchengzhong.iot_service.mapper.SensorActionMapper.findById", id);
            return result;
        } finally {
            session.close();
        }
    }

    @Override
    public List<SensorAction> findAll() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            List<SensorAction> result = sqlSession.selectList("com.songchengzhong.iot_service.mapper.SensorActionMapper.findAll");
            return result;
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public List<SensorAction> toPagedList(int pageNum, int pageSize) {
        //TODO
        return null;
    }

    @Override
    public List<SensorAction> findBySensorId(int sensorId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            List<SensorAction> result = sqlSession.selectList("com.songchengzhong.iot_service.mapper.SensorActionMapper.findBySensorId",sensorId);
            return result;
        } finally {
            sqlSession.close();
        }
    }
}
