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
import org.springframework.context.annotation.Primary;
import org.springframework.jms.connection.CachingConnectionFactory;
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

    @Value("${queuename}")
    private String queuename;


    @Bean(name = "queue")
    public Queue queue(){
        Queue queue = HornetQJMSClient.createQueue(queuename);
        return queue;
    }


    @Bean(name="hornetQConnectionFactory")
    public HornetQConnectionFactory hornetQConnectionFactory(){
        long connectionTTL = 60000;
        int consumerWindowSize = 1048576;
        Long clientFailureCheckPeriod = 30000L;
        HornetQConnectionFactory  cf ;
        HashMap<String, Object> map = new HashMap<>();
        map.put("host", "192.168.45.143");
        map.put("port", "5445");
        TransportConfiguration server1  =  new
                TransportConfiguration(NettyConnectorFactory.class.getName(), map);
        cf  = HornetQJMSClient.createConnectionFactoryWithHA(JMSFactoryType.XA_CF,  server1);
        cf.setConnectionTTL(connectionTTL);
        cf.setConsumerWindowSize(consumerWindowSize);
        cf.setClientFailureCheckPeriod(clientFailureCheckPeriod);
        return cf;
    }


    @Bean
    @Primary
    public CachingConnectionFactory cachedConnectionFactory(@Qualifier("hornetQConnectionFactory") ConnectionFactory factory) {
        CachingConnectionFactory cFactory = new CachingConnectionFactory();
        cFactory.setTargetConnectionFactory(factory);
        cFactory.setReconnectOnException(true);
        /*cFactory.setExceptionListener(new ExceptionListener() {

			@Override
			public void onException(JMSException arg0) {
				arg0.printStackTrace();
			}
		});
        cFactory.setSessionCacheSize(sessionSize);
        cFactory.setCacheConsumers(true);
        cFactory.setCacheProducers(true);*/
        return cFactory;
    }


    @Bean
    @Primary
    public JmsTemplate jmsTemplate(@Qualifier("cachedConnectionFactory") CachingConnectionFactory cFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(cFactory);
        jmsTemplate.setReceiveTimeout(200);
        return jmsTemplate;
    }
}
