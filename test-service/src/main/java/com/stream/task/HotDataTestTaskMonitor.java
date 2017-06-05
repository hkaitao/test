package com.stream.task;

import com.stream.factory.EventFactory;
import com.stream.info.Event;
import com.stream.time.SimsTime;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by dpingxian on 2017/5/25.
 */
@Component("hotDataTestTaskMonitor")
public class HotDataTestTaskMonitor implements ApplicationListener<ContextRefreshedEvent> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private volatile boolean running =  Boolean.TRUE;


    private String[] merchantUserId = new String[] { "M1000000000000000000","M1100000000000000000","M1200000000000000000" };

    @Autowired
    private RandomDataTask randomDataTask;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(running){
/*            Thread thread0 = new Thread(new TaskWorker(10,100000,0));
            thread0.setName("task-monitor" + 0);
            thread0.setDaemon(true);
            thread0.start()*/;

            Thread thread0 = new Thread(new TaskWorker(10,10000,0));
            thread0.setName("task-monitor" + 0);
            thread0.setDaemon(true);
            thread0.start();

            Thread thread1 = new Thread(new TaskWorker(10,10001,1));
            thread1.setName("task-monitor" + 1);
            thread1.setDaemon(true);
            thread1.start();

            Thread thread2 = new Thread(new TaskWorker(11,9091,2));
            thread2.setName("task-monitor" + 2);
            thread2.setDaemon(true);
            thread2.start();


            /*Thread thread0 = new Thread(new TaskWorker(10,100000,0));
            thread0.setName("task-monitor" + 0);
            thread0.setDaemon(true);
            thread0.start();

            Thread thread1 = new Thread(new TaskWorker(10,100001,1));
            thread1.setName("task-monitor" + 1);
            thread1.setDaemon(true);
            thread1.start();

            Thread thread2 = new Thread(new TaskWorker(11,90910,2));
            thread2.setName("task-monitor" + 2);
            thread2.setDaemon(true);
            thread2.start();*/
        }
    }


    private class TaskWorker implements Runnable {

        private int month;
        private int day;
        private int merchantUserIdIndex;

        public TaskWorker(int month, int day, int merchantUserIdIndex) {
            this.month = month;
            this.day = day;
            this.merchantUserIdIndex = merchantUserIdIndex;
        }

        @Override
        public void run() {
            long sum=0;
            long time = SimsTime.getLong("2015-01-02 00:00:00");
            for (int j= 0;j<month;j++){
                long daysum=0;
                time = time + 86400000;  //加一天
                for (int i=0;i<day;i++){
                    try {
                        Event e = EventFactory.buildEvent();
                        e.setTradeAmont(10L);
                        sum = sum + 10;
                        daysum = daysum+10;
                        e.setMerchantUserId(merchantUserId[merchantUserIdIndex]);
                        e.setCreateTime(new Date(time+i));
                        randomDataTask.exec(e);
                    } catch (Throwable throwable) {
                        logger.error("出现意外错误",throwable);
                    }
                }
                logger.info("商户号：{}，日金额：{}",merchantUserId[merchantUserIdIndex],daysum);
            }
            logger.info("商户号：{}，总金额：{}",merchantUserId[merchantUserIdIndex],sum);
        }
    }
}
