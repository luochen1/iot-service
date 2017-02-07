package com.songchengzhong.iot_service.repository;

import com.songchengzhong.iot_service.entity.User;

import java.util.List;

/**
 * Created by songchengzhong on 2017/1/2.
 */
public interface UserRepository extends BaseRepository<User> {
    User findByEmail(String email);

    User findByApiKey(String apiKey);
}

