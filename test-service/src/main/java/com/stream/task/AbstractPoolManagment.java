package com.stream.task;

import com.stream.pool.PoolManagement;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2017/5/25.
 */
public abstract class AbstractPoolManagment<T> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected PoolManagement poolManagement;

    public abstract void exec(T t);
}
