package com.stream.pool;

import com.alibaba.fastjson.JSONObject;
import com.stream.hq.HqSender;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * Created by Administrator on 2017/5/25.
 */
public class Processor<T> implements Runnable{

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private T t ;

    private HqSender hqSender;

    public Processor(T t, HqSender hqSender) {
        this.t = t;
        this.hqSender = hqSender;
    }

    @Override
    public void run() {
//        hqSender.send(JSONObject.toJSONString(t));
    }
}
