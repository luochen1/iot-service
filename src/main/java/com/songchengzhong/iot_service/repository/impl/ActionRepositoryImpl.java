package com.songchengzhong.iot_service.repository.impl;

import com.songchengzhong.iot_service.entity.Action;
import com.songchengzhong.iot_service.repository.ActionRepository;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by songchengzhong on 2017/3/20.
 */
@Repository
public class ActionRepositoryImpl implements ActionRepository {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public boolean insert(Action entity) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            sqlSession.insert("com.songchengzhong.iot_service.mapper.ActionMapper.insert", entity);
            sqlSession.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            sqlSession.close();
        }

    }

    @Override
    public boolean update(Action entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            sqlSession.delete("com.songchengzhong.iot_service.mapper.ActionMapper.delete", id);
            sqlSession.commit();
            return true;
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
        return false;
    }

    @Override
    public Action findById(int id) {
        return null;
    }

    @Override
    public List<Action> findAll() {
        return null;
    }

    @Override
    public List<Action> toPagedList(int pageNum, int pageSize) {
        return null;
    }


    @Override
    public List<Action> findByUserId(int userId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            List<Action> actions = sqlSession.selectList("com.songchengzhong.iot_service.mapper.ActionMapper.findByUserId", userId);
            return actions;
        } finally {
            sqlSession.close();
        }

    }
}
