package com.stream.pool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/5/25.
 */
@Service("poolManagement")
public class PoolManagement {

    @Autowired
    @Qualifier("dataTaskExecutor")
    private ThreadPoolTaskExecutor schduleProcessPool;

    public void start(Runnable r) {
        schduleProcessPool.execute(r);
    }
}
