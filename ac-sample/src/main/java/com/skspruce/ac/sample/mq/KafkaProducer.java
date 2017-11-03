package com.skspruce.ac.sample.mq;

import org.apache.kafka.common.utils.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * kafka 生产者
 * Created by sk-cailicheng on 2017/10/12.
 */

@Component
public class KafkaProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<Bytes, Bytes> kafkaTemplate;


    public void sendData(String topic, byte[] bytes) {
        ListenableFuture<SendResult<Bytes, Bytes>> future = kafkaTemplate.send(topic, Bytes.wrap(bytes));
        future.addCallback(new ListenableFutureCallback<SendResult<Bytes, Bytes>>() {
            @Override
            public void onFailure(Throwable ex) {
                LOGGER.error("topic:{" + topic + "} Fail to send message", ex);
            }

            @Override
            public void onSuccess(SendResult<Bytes, Bytes> result) {
//                LOGGER.info("向kafka发送数据完成！");
//                if (LOGGER.isDebugEnabled()) {
//                    LOGGER.debug("Send data successfully. topic:{}, partitions:{}, offset:{}",
//                            topic, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
//                }
            }
        });
    }
}
