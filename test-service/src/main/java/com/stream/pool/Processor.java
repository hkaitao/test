package com.stream.pool;

import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * Created by Administrator on 2017/5/25.
 */
public class Processor<T> implements Runnable{

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private T t ;

    public Processor(T t) {
        this.t = t;
    }

    @Override
    public void run() {
        logger.info(t.toString());
    }
}
