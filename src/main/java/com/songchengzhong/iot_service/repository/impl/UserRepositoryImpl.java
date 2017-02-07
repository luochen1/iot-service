package com.songchengzhong.iot_service.repository.impl;

import com.songchengzhong.iot_service.entity.User;
import com.songchengzhong.iot_service.repository.UserRepository;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by songchengzhong on 2017/1/3.
 */
@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    SqlSessionFactory sqlSessionFactory;


    @Override
    public boolean insert(User entity) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            int result = session.insert("com.songchengzhong.iot_service.mapper.UserMapper.insert", entity);
            session.commit();
            return result > 0;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(User entity) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            int result = session.update("com.songchengzhong.iot_service.mapper.UserMapper.update", entity);
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
            int result = session.delete("com.songchengzhong.iot_service.mapper.UserMapper.delete", id);
            session.commit();
            return result > 0;
        } finally {
            session.close();
        }
    }

    @Override
    public User findById(int id) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            User result = session.selectOne("com.songchengzhong.iot_service.mapper.UserMapper.findById", id);
            return result;
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> findAll() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            List<User> result = sqlSession.selectList("com.songchengzhong.iot_service.mapper.UserMapper.findAll");
            return result;
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public List<User> toPagedList(int pageNum, int pageSize) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Map<String, Integer> map = new HashMap<>();
            map.put("skipNum", (pageNum - 1) * pageSize);
            map.put("pageSize", pageSize);
            List<User> result = sqlSession.selectList("com.songchengzhong.iot_service.mapper.UserMapper.toPagedList", map);
            return result;
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public User findByEmail(String email) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            User entity = session.selectOne("com.songchengzhong.iot_service.mapper.UserMapper.findByEmail", email);
            return entity;
        } finally {
            session.close();
        }
    }

    @Override
    public User findByApiKey(String apiKey) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            User entity = session.selectOne("com.songchengzhong.iot_service.mapper.UserMapper.findByApiKey", apiKey);
            return entity;
        } finally {
            session.close();
        }
    }
}
