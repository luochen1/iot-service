package com.songchengzhong.iot_service.service;

import com.songchengzhong.iot_service.entity.Device;
import com.songchengzhong.iot_service.entity.User;

import javax.servlet.http.Part;
import java.util.List;

/**
 * Created by songchengzhong on 2017/1/5.
 */
public interface DeviceService {
    boolean insert(Device entity, User user);

    boolean update(Device entity, User user);

    boolean delete(int id, User user);

    Device findById(int id, User user);

    List<Device> findAll(User user);

    List<Device> toPagedList(int pageNum, int pageSize, User user);

    List<Device> findByUserId(int userId);

    boolean uploadImage(Part image,int deviceId,User user);
}
