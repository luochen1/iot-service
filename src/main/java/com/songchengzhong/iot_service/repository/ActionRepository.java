package com.songchengzhong.iot_service.repository;

import com.songchengzhong.iot_service.entity.Action;

import java.util.List;

/**
 * Created by songchengzhong on 2017/1/2.
 */
public interface ActionRepository extends BaseRepository<Action>{
    List<Action> findByUserId(int userId);
}
