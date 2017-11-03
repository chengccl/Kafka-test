package com.skspruce.ac.sample.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skspruce.ac.sample.config.AcSampleConfig;
import com.skspruce.ac.sample.model.Info;
import com.skspruce.ac.sample.mq.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * Created by sk-cailicheng on 2017/10/30.
 */
@Component
public class SendService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendService.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private AcSampleConfig acSampleConfig;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void server(int total) {
        //组装下发json
        Info info = new Info();
        info.setIp(acSampleConfig.getIp());
        info.setMac(acSampleConfig.getMac());

        String json = null;
        try {
            json = objectMapper.writeValueAsString(info);
        } catch (JsonProcessingException e) {
            LOGGER.error("transform error.", e);
        }
        for (int i = 0; i < total; i++) {
            //发送到kafka
            try {
                kafkaProducer.sendData(acSampleConfig.getSendTopic(), json.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("kafka send data failed, topic[" + acSampleConfig.getSendTopic() + "], info[" + json + "].", e);
            }
        }
    }
}
