package com.skspruce.cma.sample.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skspruce.cma.sample.config.CmaSampleConfig;
import com.skspruce.cma.sample.model.Info;
import org.apache.kafka.common.utils.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
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
    private CmaSampleConfig cmaSampleConfig;

    @Autowired
    private KafkaProducer kafkaProducer;

    private ObjectMapper objectMapper = new ObjectMapper();

    public static AtomicInteger sum = new AtomicInteger(0);
    public static AtomicInteger send = new AtomicInteger(0);

    /**
     * kafka监听如果有mac数据，则根据mac查找到对应数据，判断并下发数据
     */
    @KafkaListener(group = "${spring.kafka.consumer.group-id}",
            topics = "${cma.sample.send.topic}",
            containerFactory = "kafkaListenerContainerFactory")
    public void onMacMessage(List<Bytes> messageList, Acknowledgment ack) {

        if (CollectionUtils.isEmpty(messageList)) {
            ack.acknowledge();
            return;
        }

        messageList.parallelStream().forEach(message -> {
            try {
                Info info = objectMapper.readValue(message.toString(), Info.class);

                String topic = info.getMac().replace(":", "-");
                String json = objectMapper.writeValueAsString(info);
                sum.incrementAndGet();
                //发送到kafka
                try {
                    kafkaProducer.sendData(topic, json.getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("kafka send data failed, topic[" + topic + "], info[" + json + "].", e);
                }
            } catch (Exception e) {
                LOGGER.error("topic[send_info], message[" + message + "].", e);
            }
        });
        ack.acknowledge();
    }
}
