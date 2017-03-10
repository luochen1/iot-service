package com.songchengzhong.iot_service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.songchengzhong.iot_service.common.Strings;
import com.songchengzhong.iot_service.entity.User;
import com.songchengzhong.iot_service.repository.UserRepository;
import com.songchengzhong.iot_service.service.EmailService;
import com.songchengzhong.iot_service.service.UserService;
import com.songchengzhong.iot_service.common.UUIDs;
import com.songchengzhong.iot_service.view_model.RegisterUser;
import com.songchengzhong.iot_service.view_model.SocketUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Date;
import java.util.List;

/**
 * Created by songchengzhong on 2017/1/3.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    //注册
    @Override
    public boolean register(RegisterUser registerUser) {
        User user = userRepository.findByEmail(registerUser.getEmail().trim());
        if (user == null) {
            user = new User();
            user.setUsername(registerUser.getUsername().trim());
            user.setEmail(registerUser.getEmail().trim());
            try {
                user.setPassword(DigestUtils.md5DigestAsHex(registerUser.getPassword().trim().getBytes("UTF-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            user.setCreatedAt(new Date());
            user.setActive(true);
            user.setActiveCode(UUIDs.getUUID());
            user.setApikey(UUIDs.getUUID());
            if (userRepository.insert(user)) {
//                emailService.sendRegisterCheckEmail(user);
                //TODO
                return true;
            }
        }
        return false;
    }

    //登陆
    @Override
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            boolean result = false;
            try {
                result = user.getPassword().equals(DigestUtils.md5DigestAsHex(password.trim().getBytes("UTF-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (result) {
                return user;
            }
        }
        return null;
    }

    @Override
    public SocketUser findByApiKey(String apiKey) throws JsonProcessingException {
        String str = "apiKey:" + apiKey;
        Jedis jedis = new Jedis("localhost", 6379);
        String json = jedis.get(str);//从redis中获取apikey
        if (Strings.isNullOrEmpty(json)) {
            User user = userRepository.findByApiKey(apiKey);
            SocketUser socketUser = null;
            if (user != null) {
                socketUser = new SocketUser(user);
                //将从数据库中的user写入redis,利用json序列化
                jedis.set(str, new ObjectMapper().writeValueAsString(socketUser));
                jedis.expire(str, 10 * 60);
            }
            return socketUser;
        } else {
            ObjectMapper mapper = new ObjectMapper();
            User user = null;
            try {
                user = mapper.readValue(json, User.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new SocketUser(user);
        }
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean insert(User entity) {
        if (userRepository.insert(entity)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean update(User entity) {
        if (userRepository.update(entity)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        if (userRepository.delete(id)) {
            return true;
        }
        return false;
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> toPagedList(int pageNum, int pageSize) {
        return userRepository.toPagedList(pageNum, pageSize);
    }
}
