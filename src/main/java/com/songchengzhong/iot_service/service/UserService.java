package com.songchengzhong.iot_service.service;

import com.songchengzhong.iot_service.entity.User;
import com.songchengzhong.iot_service.view_model.RegisterUser;

import java.util.List;

/**
 * Created by songchengzhong on 2017/1/3.
 */
public interface UserService {

    //注册
    boolean register(RegisterUser registerUser);

    //登陆
    User login(String email, String password);

    //通过ApiKey找到用户
    User findByApiKey(String apiKey);

    User findByEmail(String email);

    boolean insert(User entity);

    boolean update(User entity);

    boolean delete(int id);

    User findById(int id);

    List<User> findAll();

    List<User> toPagedList(int pageNum, int pageSize);

}
