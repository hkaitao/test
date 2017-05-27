package com.stream;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.hornetq.jms.client.HornetQConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.jndi.JndiTemplate;

import javax.jms.*;
import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by Administrator on 2017/5/27.
 */

@Configuration
public class HqConfiguration {

    @Value("${java.naming.factory.initial}")
    private String initial;

    @Value("${java.naming.provider.url}")
    private String url;

    @Value("${java.naming.factory.url.pkgs}")
    private String pkgs;

    @Value("${JndiName}")
    private String jndiName;

    @Value("${queuename}")
    private String queuename;


    @Bean(name = "queue")
    public Queue queue(){
        Queue queue = HornetQJMSClient.createQueue(queuename);
        return queue;
    }


    @Bean(name=""){

    }




    @Bean(name = "hqConnection")
    public Connection hqConnection(){
        Connection connection = null;
        try {
            long connectionTTL = 60000;
            int consumerWindowSize = 1048576;
            Long clientFailureCheckPeriod =30000L;
            HornetQConnectionFactory  cf ;
            HashMap<String, Object> map = new HashMap<>();
            map.put("host", "192.168.45.143");
            map.put("port", "5445");
            TransportConfiguration server1  =  new
                    TransportConfiguration(NettyConnectorFactory.class.getName(), map);
            cf  = HornetQJMSClient.createConnectionFactoryWithHA(JMSFactoryType.CF,  server1);
            cf.setConnectionTTL(connectionTTL);
            cf.setConsumerWindowSize(consumerWindowSize);
            cf.setClientFailureCheckPeriod(clientFailureCheckPeriod);
            connection = cf.createConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }

    @Bean(name = "hqSession")
    public Session hqSession(@Qualifier("hqConnection")  Connection connection)  {
        Session session = null;
        try {
            session  = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return session;
    }


/*    @Bean(name="hqMessageProducer")
    public MessageProducer hqMessageProducer(@Qualifier("hqSession") Session session,@Qualifier("queue")Queue queue){
        MessageProducer producer = null;
        try {
            producer = session.createProducer(queue);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return producer;
    }*/


    @Bean(name = "jmsTemplate")
    public JmsTemplate jmsTemplate (){
        try {
            Properties prop = new Properties();
            prop.put("java.naming.factory.initial", initial);
            //配置HQ地址
            prop.put("java.naming.provider.url", url);
            prop.put("java.naming.factory.url.pkgs", pkgs);
            JndiTemplate jndiTemplate = new JndiTemplate(prop);
            JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
            bean.setJndiName(jndiName);
            bean.setJndiTemplate(jndiTemplate);
            bean.afterPropertiesSet();
            ConnectionFactory cFactory = (ConnectionFactory) bean.getObject();
            JmsTemplate jmsTemplate = new JmsTemplate(cFactory);
            return jmsTemplate;
        } /*catch (NamingException e) {
            e.printStackTrace();
        }*/ catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
