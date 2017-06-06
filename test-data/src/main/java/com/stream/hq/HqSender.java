package com.stream.hq;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2017/5/27.
 */
@Service("hqSender")
public class HqSender implements InitializingBean{

    private static Log logger = LogFactory.getLog(HqSender.class);

    @Autowired
    private Queue queue;

    private AtomicLong count = new AtomicLong(0);

    public void send(JmsTemplate jmsTemplate,String json){

        jmsTemplate.send(queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(json);
            }
        });
        count.incrementAndGet();
    }

    public TimerTask buildTimerTask(){
        return new StaticsTask();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Timer timer = new Timer();
        timer.schedule(buildTimerTask(), 3*1000);
    }

    private class StaticsTask extends TimerTask{

        @Override
        public void run() {
            logger.info("**********1分钟内发送数量为：" + count.get());
            count.set(0L);
        }
    }
}
