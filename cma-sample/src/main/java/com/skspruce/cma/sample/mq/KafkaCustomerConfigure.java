package com.skspruce.cma.sample.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;

/**
 * kafka消费者相关配置
 * Created by sk-cailicheng on 2017/10/12.
 */
@Configuration
@EnableKafka
@ComponentScan
public class KafkaCustomerConfigure {
    @Value(value = "${spring.kafka.listener.concurrency}")
    private Integer concurrency;

    @Autowired
    private ConsumerFactory consumerFactory;

    @Bean
    public ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(concurrency);
        factory.setBatchListener(true);
        factory.getContainerProperties()
                .setAckMode(AbstractMessageListenerContainer.AckMode.MANUAL);
        return factory;
    }
}
