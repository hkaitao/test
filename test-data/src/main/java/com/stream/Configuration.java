package com.stream;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * Created by alpha on 2017/5/25.
 */
@org.springframework.context.annotation.Configuration
public class Configuration {

    @Value("${queue.name}")
    private String queueName;

    @Bean
    public Queue queue(){
        return new Queue(queueName);
    }
}
