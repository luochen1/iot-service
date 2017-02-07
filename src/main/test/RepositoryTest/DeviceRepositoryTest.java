package RepositoryTest;

import com.songchengzhong.iot_service.config.RootConfig;
import com.songchengzhong.iot_service.entity.Device;
import com.songchengzhong.iot_service.entity.User;
import com.songchengzhong.iot_service.repository.DeviceRepository;
import com.songchengzhong.iot_service.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * Created by songchengzhong on 2017/1/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
public class DeviceRepositoryTest {
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    UserRepository userRepository;


    @Test
    public void insert() {
        List<User> users = userRepository.findAll();
        int size = users.size();

        for (int i = 1; i < 10000; i++) {
            Device device = new Device();
            device.setName("测试设备" + i + "号");
            device.setUserId(users.get((int) (Math.random() * size)).getId());
            device.setCreatedAt(new Date());
            device.setLongitude(Math.random() * 360 - 180);
            device.setLatitude(Math.random() * 180 - 90);
            deviceRepository.insert(device);
        }
    }

    @Test
    public void update() {
        Device device = new Device();
        device.setId(1);
        device.setName("AAA");
        device.setUserId(20);
        device.setCreatedAt(new Date());
        device.setLongitude(Math.random() * 360 - 180);
        device.setLatitude(Math.random() * 180 - 90);
        deviceRepository.update(device);
    }

    @Test
    public void delete() {
        deviceRepository.delete(2);
    }

    @Test
    public void find() {
        deviceRepository.findAll();
    }
}
