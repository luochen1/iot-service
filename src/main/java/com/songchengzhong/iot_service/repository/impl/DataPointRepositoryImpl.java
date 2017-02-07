package com.songchengzhong.iot_service.repository.impl;

import com.songchengzhong.iot_service.entity.DataPoint;
import com.songchengzhong.iot_service.entity.Device;
import com.songchengzhong.iot_service.repository.DataPointRepository;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by songchengzhong on 2017/1/9.
 */
@Repository
public class DataPointRepositoryImpl implements DataPointRepository {
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Override
    public boolean insert(DataPoint entity) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            int result = session.insert("com.songchengzhong.iot_service.mapper.DataPointMapper.insert", entity);
            session.commit();
            return result > 0;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(DataPoint entity) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            int result = session.update("com.songchengzhong.iot_service.mapper.DataPointMapper.update", entity);
            session.commit();
            return result > 0;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(long id) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            int result = session.delete("com.songchengzhong.iot_service.mapper.DataPointMapper.delete", id);
            session.commit();
            return result > 0;
        } finally {
            session.close();
        }
    }

    @Override
    public DataPoint findById(long id) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            DataPoint result = session.selectOne("com.songchengzhong.iot_service.mapper.DataPointMapper.findById", id);
            session.commit();
            return result;
        } finally {
            session.close();
        }
    }

    @Override
    public List<DataPoint> findAll() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            List<DataPoint> result = sqlSession.selectList("com.songchengzhong.iot_service.mapper.DataPointMapper.findAll");
            return result;
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public List<DataPoint> toPagedList(int pageNum, int pageSize) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Map<String, Integer> map = new HashMap<>();
            map.put("skipNum", (pageNum - 1) * pageSize);
            map.put("pageSize", pageSize);
            List<DataPoint> result = sqlSession.selectList("com.songchengzhong.iot_service.mapper.DataPointMapper.toPagedList", map);
            return result;
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 通过sensorId找到所有的数据点
     *
     * @param sensorId
     * @return
     */
    @Override
    public List<DataPoint> findBySensorId(int sensorId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            List<DataPoint> result = sqlSession.selectList("com.songchengzhong.iot_service.mapper.DataPointMapper.findBySensorId", sensorId);
            return result;
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public List<DataPoint> toPagedListBySensorId(int pageNum, int pageSize, int sensorId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("skipNum", (pageNum - 1) * pageSize);
            map.put("pageSize", pageSize);
            map.put("sensorId", sensorId);
            List<DataPoint> result = sqlSession.selectList("com.songchengzhong.iot_service.mapper.DataPointMapper.toPagedListBySensorId", map);
            return result;
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public List<DataPoint> findByBeginAndEndAndSensorId(int sensorId, Date begin, Date end) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("sensorId", sensorId);
            map.put("begin", begin);
            map.put("end", end);
            List<DataPoint> result = sqlSession.selectList("com.songchengzhong.iot_service.mapper.DataPointMapper.findByBeginAndEndAndSensorId", map);
            return result;
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public DataPoint findByCreatedAtAndSensorId(DataPoint entity) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            DataPoint result = sqlSession.selectOne("com.songchengzhong.iot_service.mapper.DataPointMapper.findByCreatedAtAndSensorId", entity);
            return result;
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public boolean updateByCreatedAtAndSensorId(DataPoint entity) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            int result = sqlSession.update("com.songchengzhong.iot_service.mapper.DataPointMapper.updateByCreatedAtAndSensorId", entity);
            return result > 0;
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public boolean deleteByCreatedAtAndSensorId(DataPoint entity) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            int result = sqlSession.delete("com.songchengzhong.iot_service.mapper.DataPointMapper.deleteByCreatedAtAndSensorId", entity);
            return result > 0;
        } finally {
            sqlSession.close();
        }
    }
}
