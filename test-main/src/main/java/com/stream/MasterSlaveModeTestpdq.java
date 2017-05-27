package com.stream;

import java.util.HashMap;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.hornetq.jms.client.HornetQConnectionFactory;

public class MasterSlaveModeTestpdq {

    public static void main(String[] args) throws JMSException {
        //from the official website
        long connectionTTL = 60000;
        int consumerWindowSize = 1048576;
        Long clientFailureCheckPeriod =30000L;

        //初始化连接池和队列，连接池和队列作为全局变量使用
        HornetQConnectionFactory  cf  = null;
        Queue queue = null;
        try{
            // Step 1. Directly instantiate the JMS Queue object.
            queue = HornetQJMSClient.createQueue("FrmsDSQueue");
            // Step 2. Instantiate the TransportConfiguration object which contains the knowledge of what transport to use,
            // The server port etc.
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("host", "192.168.45.143");
            map.put("port", "5445");
            TransportConfiguration  server1  =  new
                    TransportConfiguration(NettyConnectorFactory.class.getName(), map);
/*            HashMap<String, Object> map2 = new HashMap<String, Object>();
            map2.put("host", "192.168.25.54");
            map2.put("port", "5445");
            TransportConfiguration  server2  =  new
                    TransportConfiguration(NettyConnectorFactory.class.getName(), map2);*/
            // Step 3 Directly instantiate the JMS ConnectionFactory object using that TransportConfiguration
            cf  = HornetQJMSClient.createConnectionFactoryWithHA(JMSFactoryType.CF,  server1);
            cf.setConnectionTTL(connectionTTL);
            cf.setConsumerWindowSize(consumerWindowSize);
            cf.setClientFailureCheckPeriod(clientFailureCheckPeriod);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("连接池或队列初始化错误，无法发送消息");
            return;
        }

        //有消息需要发送时使用以下代码
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        try{
            // Step 4.Create a JMS Connection
            connection = cf.createConnection();

            // Step 5. Create a JMS Session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Step 6. Create a JMS Message Producer
            producer = session.createProducer(queue);

            // Step 7. Create a Text Message
            TextMessage message = session.createTextMessage("This is a text message");

            System.out.println("Sent message: " + message.getText());

            // Step 8. Send the Message
            producer.send(message);

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