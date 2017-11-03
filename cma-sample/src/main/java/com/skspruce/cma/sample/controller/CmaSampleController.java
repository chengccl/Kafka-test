package com.skspruce.cma.sample.controller;

import com.skspruce.cma.sample.mq.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sk-cailicheng on 2017/10/30.
 */
@RestController
@RequestMapping("/sample")
public class CmaSampleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CmaSampleController.class);

    /**
     *
     */
    @RequestMapping(path = "/receiveAndSend", method = RequestMethod.GET)
    @ResponseBody
    public String receive() {
        return "总共接收到 " + KafkaConsumer.sum + " 条数据，总共发送 " + KafkaConsumer.send + " 条数据。";
    }

    /**
     *
     */
    @RequestMapping(path = "/cleanSum", method = RequestMethod.GET)
    public void clean() {
        KafkaConsumer.sum.set(0);
        KafkaConsumer.send.set(0);
    }
}
