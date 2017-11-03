package com.skspruce.ac.sample.controller;

import com.skspruce.ac.sample.mq.KafkaConsumer;
import com.skspruce.ac.sample.service.SendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Created by sk-cailicheng on 2017/10/30.
 */
@RestController
@RequestMapping("/sample")
public class AcSampleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AcSampleController.class);

    @Autowired
    private SendService sendService;

    /**
     * @param total
     */
    @RequestMapping(path = "/total/{total}", method = RequestMethod.POST)
    public void sendTotal(@PathVariable Integer total) {
        LocalDateTime start = LocalDateTime.now();
        LOGGER.info("开始时间: " + start);

        KafkaConsumer.sum.set(0);
        sendService.server(total);

        LocalDateTime end = LocalDateTime.now();
        LOGGER.info("结束时间: " + end);

        LOGGER.info("花费时间: " + Duration.between(start, end).toMillis() + " 毫秒.");
    }

    /**
     *
     */
    @RequestMapping(path = "/receive", method = RequestMethod.GET)
    @ResponseBody
    public String receive() {
        return "总共接收到 " + KafkaConsumer.sum + " 条数据。";
    }
}
