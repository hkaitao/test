package com.stream.hq;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hornetq.jms.client.HornetQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.jms.*;

/**
 * Created by Administrator on 2017/5/27.
 */
@Service("hqProducer")
public class HqProducer {

    private static Log logger = LogFactory.getLog(HqProducer.class);

    @Autowired
    @Qualifier("hornetQConnectionFactory")
    private HornetQConnectionFactory  hornetQConnectionFactory;

    @Autowired
    @Qualifier("queue")
    private Queue queue;

    public void send(String json) throws JMSException {
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        try{
            connection = hornetQConnectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            producer = session.createProducer(queue);
            producer.send(session.createTextMessage(json));
        }catch(Exception e){
            e.printStackTrace();
            logger.error("发送消息出错");
        }finally{
            if(producer!= null){
                producer.close();
            }
            if(session != null){
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        }



    }
}
