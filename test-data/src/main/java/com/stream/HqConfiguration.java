package com.stream;

import org.hornetq.api.jms.HornetQJMSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.jndi.JndiTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.naming.NamingException;
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
