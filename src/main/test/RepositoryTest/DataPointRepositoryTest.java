package RepositoryTest;

import com.songchengzhong.iot_service.config.RootConfig;
import com.songchengzhong.iot_service.entity.DataPoint;
import com.songchengzhong.iot_service.entity.Device;
import com.songchengzhong.iot_service.entity.Sensor;
import com.songchengzhong.iot_service.repository.DataPointRepository;
import com.songchengzhong.iot_service.repository.SensorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by songchengzhong on 2017/1/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
public class DataPointRepositoryTest {
    @Autowired
    SensorRepository sensorRepository;
    @Autowired
    DataPointRepository dataPointRepository;

    @Test
    public void insert() {
        List<Sensor> sensors = sensorRepository.findAll();
        int size = sensors.size();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 500; i++) {
            DataPoint dataPoint = new DataPoint();
            dataPoint.setSensorId(15);
            calendar.add(Calendar.SECOND, 10);
            dataPoint.setCreatedAt(calendar.getTime());
            dataPoint.setValue(Math.random() * 10 + 20 + "");
            dataPointRepository.insert(dataPoint);
        }
    }

    @Test
    public void update() {
        DataPoint dataPoint = new DataPoint();
        dataPoint.setId(49920);
        dataPoint.setSensorId(50005);
        dataPoint.setCreatedAt(new Date());
        dataPoint.setValue("{\"value\"=6666}");
        dataPointRepository.update(dataPoint);
    }

    @Test
    public void delete() {
    }

    @Test
    public void find() {
        DataPoint dataPoint = dataPointRepository.findById(49922);
        Sensor sensor = dataPoint.getSensor();
        List<DataPoint> dataPoints = dataPointRepository.findBySensorId(sensor.getId());
        System.out.println(dataPoints.size());
//        sensor.getDataPoints().size();
//        SensorType sensorType = sensor.getSensorType();
        Device device = sensor.getDevice();
        device.getUser();
    }

    @Test
    public void sha1() {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = messageDigest.digest("123456".getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for(int i =0;i<bytes.length;i++){
                int value = bytes[i]&0xFF;//将有符号的bytes转换为int,向上转型
                if(value<16){
                    stringBuilder.append("0");
                }
                stringBuilder.append(Integer.toHexString(value));
            }
            System.out.println(stringBuilder.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void wxTest() throws UnsupportedEncodingException {
        String s = URLEncoder.encode("http://sczero.ngrok.cc/weixin/test", "UTF-8");
        System.out.println(s);
    }
}
