package com.stream.pool;

import com.alibaba.fastjson.JSONObject;
import com.stream.hq.HqProducer;
import com.stream.hq.HqSender;
import com.stream.info.Event;
import com.stream.util.HttpClientNewSender;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

/**
 * Created by Administrator on 2017/5/25.
 */
public class Processor<T> implements Runnable{

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private T t ;

    private HqSender hqSender;

    private JmsTemplate jmsTemplate;

    public Processor(T t,JmsTemplate jmsTemplate, HqSender hqSender) {
        this.t = t;
        this.jmsTemplate = jmsTemplate;
        this.hqSender = hqSender;
    }

    @Override
    public void run() {
//        HttpClientNewSender.send(t);
        hqSender.send(jmsTemplate,JSONObject.toJSONString(t));
    }
}
