package com.stream.util;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by alpha on 2017/5/25.
 */
public class RabbitMqSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public <T> void send(T sendEntity){
        String josnStr = JSON.toJSONString(sendEntity);

        amqpTemplate.convertAndSend(josnStr);
    }

    /*
    使用其他的序列化软件
    public <T> byte[] serilize(T sendEntity){

    }*/
}
