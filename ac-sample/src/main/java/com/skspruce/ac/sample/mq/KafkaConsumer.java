package com.skspruce.ac.sample.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skspruce.ac.sample.config.AcSampleConfig;
import org.apache.kafka.common.utils.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 处理需要下发的数据
 * Created by sk-cailicheng on 2017/9/30.
 */
@Component
public class KafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private AcSampleConfig acSampleConfig;

    private ObjectMapper objectMapper = new ObjectMapper();

    public static AtomicInteger sum = new AtomicInteger(0);

    public static int count = 0;

    /**
     * kafka监听如果有mac数据，则根据mac查找到对应数据，判断并下发数据
     */
    @KafkaListener(group = "${spring.kafka.consumer.group-id}",
            topics = "${ac.sample.listener.topic}",
            containerFactory = "kafkaListenerContainerFactory")
    public void onMacMessage(List<Bytes> messageList, Acknowledgment ack) {
        if (CollectionUtils.isEmpty(messageList)) {
            ack.acknowledge();
            return;
        }

        messageList.forEach(message -> {
            try {
//                Info info = objectMapper.readValue(message.toString(), Info.class);
//
//                Assert.isTrue(info.getIp().equals(acSampleConfig.getIp()), "ip error");
//                Assert.isTrue(info.getMac().equals(acSampleConfig.getMac()), "mac error");
                sum.incrementAndGet();

            } catch (Exception e) {
                LOGGER.error("topic[" + acSampleConfig.getListenerTopic() + "], message[" + message + "].", e);
            }
        });
        ack.acknowledge();
    }
}
