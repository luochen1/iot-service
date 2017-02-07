package RepositoryTest;

import com.songchengzhong.iot_service.config.RootConfig;
import com.songchengzhong.iot_service.entity.User;
import com.songchengzhong.iot_service.service.EmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by songchengzhong on 2017/1/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
public class EmailRepositoryTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void sendRegisterCheckEmail() {
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("756452318@qq.com");
        emailService.sendRegisterCheckEmail(user);
    }
}
