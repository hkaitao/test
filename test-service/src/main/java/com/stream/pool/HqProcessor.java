package com.stream.pool;

import com.alibaba.fastjson.JSONObject;
import com.stream.hq.HqProducer;
import com.stream.hq.HqSender;
import com.stream.util.HttpClientNewSender;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

import javax.jms.JMSException;

/**
 * Created by Administrator on 2017/5/25.
 */
public class HqProcessor<T> implements Runnable{

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private T t ;

    private HqProducer hqProducer;

    public HqProcessor(T t, HqProducer hqProducer) {
        this.t = t;
        this.hqProducer = hqProducer;
    }

    @Override
    public void run() {
        //请求风控
//        HttpClientNewSender.send(t);
        //发送HQ
/*        try {
            hqProducer.send(JSONObject.toJSONString(t));
        } catch (JMSException e) {
            e.printStackTrace();
        }*/
    }
}
