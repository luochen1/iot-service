package RepositoryTest;

import com.songchengzhong.iot_service.config.RootConfig;
import com.songchengzhong.iot_service.entity.Device;
import com.songchengzhong.iot_service.entity.Sensor;
import com.songchengzhong.iot_service.repository.DeviceRepository;
import com.songchengzhong.iot_service.repository.SensorRepository;
import com.songchengzhong.iot_service.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * Created by songchengzhong on 2017/1/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
public class SensorRepositoryTest {
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SensorRepository sensorRepository;

    @Test
    public void insert() {
        List<Device> devices = deviceRepository.findAll();
        int size = devices.size();
        Sensor sensor = null;
        for (int i = 0; i < 50000; i++) {
            sensor = new Sensor();
            sensor.setName("传感器" + i + "号");
            sensor.setDescription("这是传感器" + i + "号的描述");
            sensor.setSymbol("℃");
            sensor.setUnit("摄氏度");
            sensor.setCreatedAt(new Date());
            sensor.setSensorTypeId((int) (Math.random() * 5) + 1);
            sensor.setDeviceId(devices.get((int) (Math.random() * size)).getId());
            sensorRepository.insert(sensor);
        }

    }

    @Test
    public void update() {
        Sensor sensor = new Sensor();
        sensor.setId(50001);
        sensor.setName("AAAA");
        sensor.setDescription("这是传感器" + "sfas" + "号的描述");
        sensor.setSymbol("℃");
        sensor.setUnit("摄氏度");
        sensor.setCreatedAt(new Date());
        sensor.setSensorTypeId(1);
        sensor.setDeviceId(57);
        sensorRepository.update(sensor);
    }

    @Test
    public void delete() {
        sensorRepository.delete(50002);
    }

    @Test
    public void find() {
        sensorRepository.findByDeviceId(1);
    }
}
