package com.skspruce.ac.sample.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sk-cailicheng on 2017/10/30.
 */
@Configuration
public class AcSampleConfig {
    @Value("${ac.sample.listener.topic}")
    private String listenerTopic;

    @Value("${ac.sample.send.topic}")
    private String sendTopic;

    @Value("${ac.sample.ip}")
    private String ip;

    @Value("${ac.sample.mac}")
    private String mac;

    public String getListenerTopic() {
        return listenerTopic;
    }

    public String getSendTopic() {
        return sendTopic;
    }

    public String getIp() {
        return ip;
    }

    public String getMac() {
        return mac;
    }
}
