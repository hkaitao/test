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

    private AtomicLong count_sec = new AtomicLong(0);

    private AtomicLong count_min = new AtomicLong(0);

    private Timer timer = new Timer();

    public void send(JmsTemplate jmsTemplate,String json){

        jmsTemplate.send(queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(json);
            }
        });
        count_sec.incrementAndGet();
        count_min.incrementAndGet();
    }

    public TimerTask buildTimerTask(){
        return new StaticsTaskSec();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        timer.schedule(buildTimerTask(), 0,1000);
        timer.schedule(new StaticsTaskMin(),0,60*1000);
    }

    private class StaticsTaskSec extends TimerTask{

        @Override
        public void run() {
            logger.info("**********流立方每秒发送数量为：" + count_sec.get());
            count_sec.set(0L);
        }
    }

    private class StaticsTaskMin extends TimerTask{

        @Override
        public void run() {
            logger.info("**********流立方每分钟发送数量为：" + count_min.get());
            count_min.set(0L);
        }
    }
}
