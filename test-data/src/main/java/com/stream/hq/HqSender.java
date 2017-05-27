package com.stream.hq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.*;

/**
 * Created by Administrator on 2017/5/27.
 */
@Service("hqSender")
public class HqSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue queue;


    public void send(String json){
        jmsTemplate.send(queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(json);
            }
        });
    }
}
