package com.stream.hq;

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
            // Step 4.Create a JMS Connection
            connection = hornetQConnectionFactory.createConnection();

            // Step 5. Create a JMS Session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Step 6. Create a JMS Message Producer
            producer = session.createProducer(queue);

            // Step 8. Send the Message
            producer.send(session.createTextMessage(json));

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("发送消息出错");
        }finally{
            if(session != null){
                session.close();
            }
            if(producer!= null){
                producer.close();
            }
            if (connection != null)
            {
                connection.close();
            }
        }



    }
}
