package com.songchengzhong.iot_service.controller.api;

import com.songchengzhong.iot_service.entity.DataPoint;
import com.songchengzhong.iot_service.entity.User;
import com.songchengzhong.iot_service.service.DataPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by songchengzhong on 2017/1/11.
 */
@RestController
@RequestMapping(value = "/api/devices/{deviceId}/sensors/{sensorId}/datapoints", produces = "application/json;charset=UTF-8"/*防止resp乱码*/)
public class DataPointApiController {
    @Autowired
    DataPointService dataPointService;
    @Autowired(required = false)
    HttpServletRequest request;

    /**
     * @param dataPoint 数据点(value,createdAt可为空)
     * @param sensorId  传感器的id
     * @return
     */
    @PostMapping
    public ResponseEntity<Object> insert(@RequestBody DataPoint dataPoint, @PathVariable Integer sensorId) {
        User user = (User) request.getAttribute("user");
        dataPoint.setSensorId(sensorId);//设置传感器的id
        if (dataPointService.insert(dataPoint, user)) {
            return new ResponseEntity<>(dataPoint.getCreatedAt(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 更新
     *
     * @param dataPoint
     * @param sensorId
     * @param timestamp
     * @return
     */
    @PutMapping("/{timestamp}")
    public ResponseEntity<Object> updateByTimestamp(@RequestBody DataPoint dataPoint, @PathVariable Integer sensorId, @PathVariable Long timestamp) {
        User user = (User) request.getAttribute("user");
        dataPoint.setSensorId(sensorId);
        if (dataPointService.updateByTimestampAndSensorId(user, timestamp, dataPoint)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 删除
     *
     * @param sensorId
     * @param timestamp
     * @return
     */
    @DeleteMapping("/{timestamp}")
    public ResponseEntity<Object> deleteByTimestamp(@PathVariable Integer sensorId, @PathVariable Long timestamp) {
        User user = (User) request.getAttribute("user");
        if (dataPointService.deleteByTimestampAndSensorId(user, timestamp, sensorId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 通过时间戳来获取最近一条数据
     *
     * @param sensorId
     * @param timestamp
     * @return
     */
    @GetMapping("/{timestamp}")
    public ResponseEntity<Object> findByTimestamp(@PathVariable Integer sensorId, @PathVariable Long timestamp) {
        User user = (User) request.getAttribute("user");
        DataPoint dataPoint = dataPointService.findByTimestampAndSensorId(user, timestamp, sensorId);
        if (dataPoint != null) {
            return new ResponseEntity<>(dataPoint.getValue(), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 获得所有数据
     *
     * @param sensorId
     * @return
     */
    @GetMapping
    public ResponseEntity<Object> findAll(@PathVariable Integer sensorId) {
        User user = (User) request.getAttribute("user");
        List<DataPoint> dataPoints = dataPointService.findBySensorId(user, sensorId);
        if (dataPoints != null && dataPoints.size() > 0) {
            return new ResponseEntity<>(dataPoints, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 分页
     *
     * @param sensorId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/num/{pageNum}/size/{pageSize}")
    public ResponseEntity<Object> toPagedList(@PathVariable Integer sensorId, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        User user = (User) request.getAttribute("user");
        List<DataPoint> dataPoints = dataPointService.toPagedListBySensorId(user, sensorId, pageNum, pageSize);
        if (dataPoints != null && dataPoints.size() > 0) {
            return new ResponseEntity<>(dataPoints, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 开始时间,结束时间
     *
     * @param sensorId
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("begin/{begin}/end/{end}")
    public ResponseEntity<Object> findByBeginAndEnd(@PathVariable Integer sensorId, @PathVariable Long begin, @PathVariable Long end) {
        User user = (User) request.getAttribute("user");
        List<DataPoint> dataPoints = dataPointService.findByBeginAndEndAndSensorId(user, sensorId, begin, end);
        if (dataPoints != null && dataPoints.size() > 0) {
            return new ResponseEntity<>(dataPoints, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
