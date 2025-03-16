package com.xyu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.xyu.Service.messageServiceImpl;
@SpringBootTest
class DemoAiApplicationTests {
    @Autowired
    messageServiceImpl messageServiceimpl;

    @Test
    void contextLoads() {
        String messageService1 = messageServiceimpl.qurryall();
    }

}
