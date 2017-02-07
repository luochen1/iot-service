package com.songchengzhong.iot_service.service.impl;

import com.oracle.tools.packager.IOUtils;
import com.songchengzhong.iot_service.common.Strings;
import com.songchengzhong.iot_service.common.UUIDs;
import com.songchengzhong.iot_service.entity.Device;
import com.songchengzhong.iot_service.entity.User;
import com.songchengzhong.iot_service.repository.DeviceRepository;
import com.songchengzhong.iot_service.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

/**
 * Created by songchengzhong on 2017/1/5.
 */
@Service
public class DeviceServiceImpl implements DeviceService, ServletContextAware {
    @Autowired
    DeviceRepository deviceRepository;

    ServletContext servletContext;

    @Override
    public boolean insert(Device entity, User user) {
        entity.setCreatedAt(new Date());
        entity.setUserId(user.getId());
        entity.setImageURL("/img/empty.jpg");
        return deviceRepository.insert(entity) ? true : false;
    }

    @Override
    public boolean update(Device entity, User user) {
        Device device = deviceRepository.findById(entity.getId());//通过id找到数据库中的设备
        if (device.getUserId() == user.getId()) {
            device.setName(entity.getName());
            device.setDescription(entity.getDescription());
            device.setCity(entity.getCity());
            device.setLongitude(entity.getLongitude());
            device.setLatitude(entity.getLatitude());
            return deviceRepository.update(device);//更新查询出来的那个entity
        }
        return false;
    }

    @Override
    public boolean delete(int id, User user) {
        Device device = deviceRepository.findById(id);
        if (device.getUserId() == user.getId()) {
            return deviceRepository.delete(id);
        }
        return false;
    }

    @Override
    public Device findById(int id, User user) {
        Device device = deviceRepository.findById(id);
        if (device.getUserId() == user.getId()) {
            return device;
        }
        return null;
    }

    @Override
    public List<Device> findAll(User user) {
        return deviceRepository.findByUserId(user.getId());
    }

    @Override
    public List<Device> toPagedList(int pageNum, int pageSize, User user) {
        return deviceRepository.toPagedListByUserId(pageNum, pageSize, user.getId());
    }

    @Override
    public List<Device> findByUserId(int userId) {
        return deviceRepository.findByUserId(userId);
    }

    @Override
    public boolean uploadImage(Part image, int deviceId, User user) {
        Device device = findById(deviceId, user);
        if (device != null) {
            //将之前存在的图片删除
            if (!Strings.isNullOrEmpty(device.getImageURL()) && !device.getImageURL().equals("/img/empty.jpg")) {
                try {
                    if (Files.exists(Paths.get(servletContext.getRealPath(device.getImageURL())))) {
                        Files.delete(Paths.get(servletContext.getRealPath(device.getImageURL())));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //进行保存
            String fileName = UUIDs.getUUID();
            String[] imageSpitNames = image.getSubmittedFileName().split("\\.");
            String realPath = "/img/upload/" + fileName + "." + imageSpitNames[imageSpitNames.length - 1];
            try {
                String fullRealPath = servletContext.getRealPath("/img/upload");
                if (Files.notExists(Paths.get(fullRealPath))) {
                    Files.createDirectories(Paths.get(fullRealPath));
                }
                //FileCopyUtils.copy(image.getInputStream(),Files.newOutputStream(Paths.get(servletContext.getRealPath(realPath))));
                image.write(servletContext.getRealPath(realPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //更新数据库中的图片数据
            device.setImageURL(realPath);
            return deviceRepository.update(device) ? true : false;
        }
        return false;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
