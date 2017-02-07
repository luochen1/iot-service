package RepositoryTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.songchengzhong.iot_service.config.RootConfig;
import com.songchengzhong.iot_service.entity.User;
import com.songchengzhong.iot_service.repository.UserRepository;
import com.songchengzhong.iot_service.common.UUIDs;
import com.songchengzhong.iot_service.view_model.WeiXinResp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by songchengzhong on 2017/1/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void insert() {
        for (int i = 10; i < 99; i++) {
            User user = new User();
            user.setEmail("7564523" + i + "@qq.com");
            user.setUsername("测试账号" + i);
            user.setPassword("123456");
            user.setIntroduction("暂无说明");
            user.setActiveCode(UUIDs.getUUID());
            user.setActive(true);
            user.setApikey(UUIDs.getUUID());
            user.setCreatedAt(new Date());
            userRepository.insert(user);
        }
    }

    @Test
    public void update() {
        User user = new User();
        user.setId(6);
        user.setEmail("756452310@qq.com");
        user.setUsername("update");
        user.setPassword("11111");
        user.setApikey(UUIDs.getUUID());
        userRepository.update(user);
    }

    @Test
    public void delete() {
        userRepository.delete(6);
    }

    @Test
    public void find(){
        userRepository.findById(11);
//        userRepository.findByEmail("756452318@qq.com");
//        userRepository.toPagedList(1,10);
    }

    @Test
    public void test(){
        XmlMapper mapper = new XmlMapper();
        WeiXinResp weiXinResp = new WeiXinResp("oQtuNxCiWYV6ZHTT094dS8fgDIMA", "gh_394f967b5122", new Date().getTime(), "text", "白痴~");
        String value = null;
        try {
            value = mapper.writeValueAsString(weiXinResp);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("响应的消息:" + value);
    }
}

