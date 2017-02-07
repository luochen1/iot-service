package com.songchengzhong.iot_service.repository;

import java.util.List;

/**
 * Created by songchengzhong on 2017/1/3.
 */
public interface BaseRepository<T> {
    boolean insert(T entity);

    boolean update(T entity);

    boolean delete(int id);

    T findById(int id);

    List<T> findAll();

    List<T> toPagedList(int pageNum, int pageSize);
}
