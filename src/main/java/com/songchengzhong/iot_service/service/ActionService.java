package com.songchengzhong.iot_service.service;

import com.songchengzhong.iot_service.entity.Action;
import com.songchengzhong.iot_service.entity.DataPoint;
import com.songchengzhong.iot_service.entity.User;
import com.songchengzhong.iot_service.viewmodel.AddAction;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by songchengzhong on 2017/3/20.
 */
public interface ActionService {
    List<Action> findAllActionByUser(User user);

    void deleteById(int id);

    /**
     * 添加用户的动作
     *
     * @param user
     * @param addAction
     */
    void add(User user, AddAction addAction, String contextPath);

    /**
     * 根据传过来的数据点和对应的用户  触发动作
     *
     * @param user
     * @param dataPoint
     */
    void TriggerAction(User user, DataPoint dataPoint);
}
