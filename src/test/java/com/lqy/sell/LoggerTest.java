package com.lqy.sell;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Rodriguez
 * 2018/7/11 17:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {

    private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test() { // 系统默认的日志级别就是info
        logger.debug("debug...");
        logger.info("info...");
        logger.error("error...");
    }


    @Test
    public void slf4jByLombok() { // 系统默认的日志级别就是info
        String name = "ronaldo";
        String password = "messi";
        log.debug("debug...");
        log.info("info...");
        // 日志输出变量
        log.info("name: " + name + ",password: " + password);
        log.info("name: {},password: {}", name, password);
        log.error("error...");
        log.warn("warn...");
    }

}
