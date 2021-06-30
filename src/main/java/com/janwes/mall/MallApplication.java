package com.janwes.mall;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.mall
 * @date 2021/6/29 14:03
 * @description
 */
@SpringBootApplication
public class MallApplication {

    private static final Logger log = LoggerFactory.getLogger(MallApplication.class);

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        ConfigurableApplicationContext context = SpringApplication.run(MallApplication.class, args);
        long time = (System.currentTimeMillis() - startTime) / 1000;

        String message1 = "启动完成-耗时：%ds，链接：http://%s:%s/swagger-ui.html";
        String message2 = "启动完成-耗时：%ds，链接：http://%s:%s/doc.html";
        // 服务端口号
        String port = context.getEnvironment().getProperty("server.port");
        // 服务地址
        String address = context.getEnvironment().getProperty("server.address");

        log.info(String.format(message1, time, address, port));
        log.info(String.format(message2, time, address, port));
    }
}
