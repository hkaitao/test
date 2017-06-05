package com.stream.pool;

import com.alibaba.fastjson.JSONObject;
import com.stream.hq.HqProducer;
import com.stream.hq.HqSender;
import com.stream.info.Event;
import com.stream.util.HttpClientNewSender;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import java.util.ArrayList;
import java.util.List;

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
        try {
            HttpClientNewSender.send(((Event)t).toMap());
        }catch (Exception e){
            logger.error("调用风控业务处理失败",e);
        }
        try {
            List list = new ArrayList<>();
            list.add(t);
            String json = JSONObject.toJSONString(list);
            logger.info("发送数据:{}",json);
            hqSender.send(jmsTemplate,json);
        }catch (Exception e){
            logger.error("发送队列业务处理失败",e);
        }


    }
}
