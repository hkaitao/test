package com.stream.task;

import com.stream.factory.EventFactory;
import com.stream.info.Event;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/5/25.
 */
@Component("taskMonitor")
public class TaskMonitor  implements ApplicationListener<ContextRefreshedEvent> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private RandomDataTask randomDataTask;

    private volatile boolean running = Boolean.TRUE;

    private static final int threadMaxnum = 3;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (running) {
            for (int i =1 ;i<=threadMaxnum; i++){
                Thread thread = new Thread(new TaskWorker());
                thread.setName("task-monitor" + i);
                thread.setDaemon(true);
                thread.start();
            }
        }
    }

    private class TaskWorker implements Runnable {

        @Override
        public void run() {
            try {
                randomDataTask.exec(EventFactory.buildEvent());
            } catch (Throwable throwable) {
                logger.error("出现意外错误",throwable);
            }
        }

    }

}
