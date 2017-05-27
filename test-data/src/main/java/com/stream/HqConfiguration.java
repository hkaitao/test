package com.stream;

import org.hornetq.api.jms.HornetQJMSClient;
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


    @Bean(name = "queue")
    public Queue queue(){
        Queue queue = HornetQJMSClient.createQueue("HandleOrderQueue");
        return queue;
    }


    @Bean(name = "jmsTemplate")
    public JmsTemplate jmsTemplate (){
        Properties prop = new Properties();
        prop.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
        //配置HQ地址
        prop.put("java.naming.provider.url", "jnp://192.168.25.55:1099");
        prop.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
        JndiTemplate jndiTemplate = new JndiTemplate(prop);
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setJndiName("/ConnectionFactory");
        bean.setJndiTemplate(jndiTemplate);
        try {
            bean.afterPropertiesSet();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        ConnectionFactory cFactory = (ConnectionFactory) bean.getObject();
        JmsTemplate jmsTemplate = new JmsTemplate(cFactory);
        return jmsTemplate;
    }
}
