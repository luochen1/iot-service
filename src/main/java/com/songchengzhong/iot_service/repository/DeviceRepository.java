package com.songchengzhong.iot_service.repository;

import com.songchengzhong.iot_service.entity.Device;

import java.util.List;

/**
 * Created by songchengzhong on 2017/1/2.
 */
public interface DeviceRepository extends BaseRepository<Device> {
    List<Device> findByUserId(int id);

    List<Device> toPagedListByUserId(int pageNum, int pageSize,int userId);
}
