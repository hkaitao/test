package com.stream;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
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
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.client.RestTemplate;

import javax.jms.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    @Primary
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


    @Bean(name="cachedConnectionFactory")
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

    @Bean(name="jmsTemplate")
    public JmsTemplate jmsTemplate(@Qualifier("cachedConnectionFactory") CachingConnectionFactory cFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(cFactory);
        jmsTemplate.setReceiveTimeout(200);
        jmsTemplate.setMessageIdEnabled(Boolean.FALSE);
        jmsTemplate.setMessageTimestampEnabled(Boolean.FALSE);
        return jmsTemplate;
    }


    @Bean(name="cachedConnectionFactory1")
    public CachingConnectionFactory cachedConnectionFactory1(@Qualifier("hornetQConnectionFactory") ConnectionFactory factory) {
        CachingConnectionFactory cFactory = new CachingConnectionFactory();
        cFactory.setTargetConnectionFactory(factory);
        cFactory.setReconnectOnException(true);
        return cFactory;
    }

    @Bean(name="jmsTemplate1")
    public JmsTemplate jmsTemplate1(@Qualifier("cachedConnectionFactory1") CachingConnectionFactory cFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(cFactory);
        jmsTemplate.setReceiveTimeout(200);
        jmsTemplate.setMessageIdEnabled(Boolean.FALSE);
        jmsTemplate.setMessageTimestampEnabled(Boolean.FALSE);
        return jmsTemplate;
    }


    @Bean(name="cachedConnectionFactory2")
    public CachingConnectionFactory cachedConnectionFactory2(@Qualifier("hornetQConnectionFactory") ConnectionFactory factory) {
        CachingConnectionFactory cFactory = new CachingConnectionFactory();
        cFactory.setTargetConnectionFactory(factory);
        cFactory.setReconnectOnException(true);
        return cFactory;
    }


    @Bean(name="jmsTemplate2")
    public JmsTemplate jmsTemplate2(@Qualifier("cachedConnectionFactory2") CachingConnectionFactory cFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(cFactory);
        jmsTemplate.setReceiveTimeout(200);
        jmsTemplate.setMessageIdEnabled(Boolean.FALSE);
        jmsTemplate.setMessageTimestampEnabled(Boolean.FALSE);
        return jmsTemplate;
    }



    @Bean(name="cachedConnectionFactory3")
    public CachingConnectionFactory cachedConnectionFactory3(@Qualifier("hornetQConnectionFactory") ConnectionFactory factory) {
        CachingConnectionFactory cFactory = new CachingConnectionFactory();
        cFactory.setTargetConnectionFactory(factory);
        cFactory.setReconnectOnException(true);
        return cFactory;
    }

    @Bean(name="jmsTemplate3")
    public JmsTemplate jmsTemplate3(@Qualifier("cachedConnectionFactory3") CachingConnectionFactory cFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(cFactory);
        jmsTemplate.setReceiveTimeout(200);
        jmsTemplate.setMessageIdEnabled(Boolean.FALSE);
        jmsTemplate.setMessageTimestampEnabled(Boolean.FALSE);
        return jmsTemplate;
    }


/*    @Bean(name="cachedConnectionFactory4")
    public CachingConnectionFactory cachedConnectionFactory4(@Qualifier("hornetQConnectionFactory") ConnectionFactory factory) {
        CachingConnectionFactory cFactory = new CachingConnectionFactory();
        cFactory.setTargetConnectionFactory(factory);
        cFactory.setReconnectOnException(true);
        return cFactory;
    }

    @Bean(name="jmsTemplate4")
    public JmsTemplate jmsTemplate4(@Qualifier("cachedConnectionFactory4") CachingConnectionFactory cFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(cFactory);
        jmsTemplate.setReceiveTimeout(200);
        return jmsTemplate;
    }*/


/*    @Bean(name="cachedConnectionFactory5")
    public CachingConnectionFactory cachedConnectionFactory5(@Qualifier("hornetQConnectionFactory") ConnectionFactory factory) {
        CachingConnectionFactory cFactory = new CachingConnectionFactory();
        cFactory.setTargetConnectionFactory(factory);
        cFactory.setReconnectOnException(true);
        return cFactory;
    }

    @Bean(name="jmsTemplate5")
    public JmsTemplate jmsTemplate5(@Qualifier("cachedConnectionFactory5") CachingConnectionFactory cFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(cFactory);
        jmsTemplate.setReceiveTimeout(200);
        return jmsTemplate;
    }*/


/*    @Bean(name="cachedConnectionFactory6")
    public CachingConnectionFactory cachedConnectionFactory6(@Qualifier("hornetQConnectionFactory") ConnectionFactory factory) {
        CachingConnectionFactory cFactory = new CachingConnectionFactory();
        cFactory.setTargetConnectionFactory(factory);
        cFactory.setReconnectOnException(true);
        return cFactory;
    }

    @Bean(name="jmsTemplate6")
    public JmsTemplate jmsTemplate6(@Qualifier("cachedConnectionFactory6") CachingConnectionFactory cFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(cFactory);
        jmsTemplate.setReceiveTimeout(200);
        return jmsTemplate;
    }*/


/*    @Bean(name="cachedConnectionFactory7")
    public CachingConnectionFactory cachedConnectionFactory7(@Qualifier("hornetQConnectionFactory") ConnectionFactory factory) {
        CachingConnectionFactory cFactory = new CachingConnectionFactory();
        cFactory.setTargetConnectionFactory(factory);
        cFactory.setReconnectOnException(true);
        return cFactory;
    }

    @Bean(name="jmsTemplate7")
    public JmsTemplate jmsTemplate7(@Qualifier("cachedConnectionFactory7") CachingConnectionFactory cFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(cFactory);
        jmsTemplate.setReceiveTimeout(200);
        return jmsTemplate;
    }*/


    @Bean(name="restTemplate")
    public RestTemplate restTemplate(){
        int poolSize = 4;
        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager();
        connMgr.setMaxTotal(poolSize + 1);
        connMgr.setDefaultMaxPerRoute(poolSize);
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connMgr).build();
        RestTemplate template = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        FastJsonHttpMessageConverter fastjson = new FastJsonHttpMessageConverter();
        fastjson.setFeatures(SerializerFeature.WriteClassName, SerializerFeature.BrowserCompatible, SerializerFeature.DisableCircularReferenceDetect);
        converters.add(fastjson);
        template.setMessageConverters(converters);
        return template;
    }

}
