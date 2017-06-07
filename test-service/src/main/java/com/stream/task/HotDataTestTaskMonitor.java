package com.stream.task;

import com.stream.factory.EventFactory;
import com.stream.info.Event;
import com.stream.time.SimsTime;
import com.stream.util.HttpClientNewSender;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dpingxian on 2017/5/25.
 */
@Component("hotDataTestTaskMonitor")
public class HotDataTestTaskMonitor implements ApplicationListener<ContextRefreshedEvent> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private volatile boolean running =  Boolean.FALSE;

    private int m1_c = 10;

    private int m1_t = 10000;

    private int m2_c = 9;

    private int m2_t = 10001;

    private int m3_c = 11;

    private int m3_t = 9091;

    private long money = 10000L;



    private String[] merchantUserId = new String[] { "M1000000000000000210","M1100000000000000211","M1200000000000000212" };

    @Autowired
    private RandomDataTask randomDataTask;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(running){
/*            Thread thread0 = new Thread(new TaskWorker(10,100000,0));
            thread0.setName("task-monitor" + 0);
            thread0.setDaemon(true);
            thread0.start()*/;

            /*Thread thread0 = new Thread(new TaskWorker(m1_c,m1_t,0));
            thread0.setName("task-monitor" + 0);
            thread0.setDaemon(true);
            thread0.start();*/

            //每次均超过日限
            Thread thread1 = new Thread(new TaskWorker(m2_c,m2_t,1));
            thread1.setName("task-monitor" + 1);
            thread1.setDaemon(true);
            thread1.start();

        /*    //超过月限
            Thread thread2 = new Thread(new TaskWorker(m3_c,m3_t,2));
            thread2.setName("task-monitor" + 2);
            thread2.setDaemon(true);
            thread2.start();*/


            /*Thread thread0 = new Thread(new TaskWorker(10,100000,0));
            thread0.setName("task-monitor" + 0);
            thread0.setDaemon(true);
            thread0.start();

            Thread thread1 = new Thread(new TaskWorker(9,100001,1));
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
            long time = SimsTime.getLong("2015-01-02 00:00:01");
            for (int j= 0;j<month;j++){
                long daysum=0;
                time = time + 86400000;  //加一天
                for (int i=0;i<day;i++){
                    Event e = EventFactory.buildEvent();
                    try {
                        Long ta = money;
                        e.setTradeAmont(ta);
                        sum = sum + ta;
                        daysum = daysum+ta;
                        e.setMerchantUserId(merchantUserId[merchantUserIdIndex]);
                        e.setCreateTime(new Date(time + i));

                        if(month==m2_c && day==m2_t && i==day-1){
                            long delay = 50;
                            sleep(delay);
                            logger.info("日上限_探头数据：{},延迟{}ms发送", e.toString(), delay);
                            randomDataTask.exec(e);
//                            send(e);
                        }else if(month==m3_c && day==m3_t && j==month-1 && i== day-1){
                            long delay = 50;
                            sleep(delay);
                            logger.info("月上限_探头数据：{},延迟{}ms发送", e.toString(), delay);
                            randomDataTask.exec(e);
//                            send(e);
                        }else{
                            randomDataTask.exec(e);
//                            send(e);
                        }

                    } catch (Throwable throwable) {
                        logger.error("出现意外错误",throwable);
                    }

                }
//                logger.info("商户号：{}，日金额：{}",merchantUserId[merchantUserIdIndex],daysum);
            }

//            logger.info("商户号：{}，总金额：{}",merchantUserId[merchantUserIdIndex],sum);
        }
    }


    private void sleep(long millisecond){
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void send(Event t){
        try {
            HttpClientNewSender.send(((Event) t).toMap());
        }catch (Exception e){
            logger.error("调用风控业务处理失败",e);
        }
        try {
            List list = new ArrayList<>();
            list.add(t);
           /* String json = JSONObject.toJSONString(list);
            hqSender.send(jmsTemplate,json);*/
//            logger.info("发送数据:{}",list.toString());
            RestTemplateSender.sendOrderQueue(list);
        }catch (Exception e){
            logger.error("发送队列业务处理失败",e);
        }
    }
}
