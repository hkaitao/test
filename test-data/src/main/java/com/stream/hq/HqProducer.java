package com.stream.hq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.jms.*;

/**
 * Created by Administrator on 2017/5/27.
 */
@Service("hqProducer")
public class HqProducer {

    @Autowired
    @Qualifier("hqSession")
    private Session hqSession;

    @Autowired
    @Qualifier("queue")
    private Queue queue;

    public void send(String json){
        try {
            MessageProducer producer = hqSession.createProducer(queue);
            producer.send(hqSession.createTextMessage(json));
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
