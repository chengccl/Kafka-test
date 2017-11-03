package com.skspruce.cma.sample.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sk-cailicheng on 2017/10/30.
 */
@Configuration
public class CmaSampleConfig {
    @Value("${cma.sample.send.topic}")
    private String sendTopic;

    public String getSendTopic() {
        return sendTopic;
    }
}
