package com.stream.task;

import com.stream.info.Event;
import com.stream.util.ExcelParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Random;

/**
 * Created by alpha on 2017/6/2.
 */
@Service
public class RuleTestTask implements ApplicationListener<ContextRefreshedEvent>{

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private RandomDataTask randomDataTask;

    private boolean switchCase = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            Thread thread1 = new Thread(new TestTask(ExcelParser.parseExcel("rule1/rule1.xlsx", this.getClass())));
            Thread thread2 = new Thread(new TestTask(ExcelParser.parseExcel("rule2/rule2.xlsx", this.getClass())));
            Thread thread3 = new Thread(new TestTask(ExcelParser.parseExcel("rule3/rule3.xlsx", this.getClass())));
            Thread thread4 = new Thread(new TestTask(ExcelParser.parseExcel("rule4/rule4.xlsx", this.getClass())));
            Thread thread5 = new Thread(new TestTask(ExcelParser.parseExcel("rule5/rule5.xlsx", this.getClass())));
            Thread thread6 = new Thread(new TestTask(ExcelParser.parseExcel("rule6/rule6.xlsx", this.getClass())));
            Thread thread7 = new Thread(new TestTask(ExcelParser.parseExcel("rule7/rule7.xlsx", this.getClass())));
            Thread thread8 = new Thread(new TestTask(ExcelParser.parseExcel("rule8/rule8.xlsx", this.getClass())));
            Thread thread9 = new Thread(new TestTask(ExcelParser.parseExcel("rule9/rule9.xlsx", this.getClass())));

            if(switchCase) {
                thread1.start();
                thread2.start();
                thread3.start();
                thread4.start();
                thread5.start();
                thread6.start();
                thread7.start();
                thread8.start();
                thread9.start();
            }
        } catch (IOException e) {
            logger.error("解析excel文件出错", e);
        } catch (ParseException e) {
            logger.error("解析excel文件出错", e);
        }

    }

    private class TestTask implements Runnable{
        private List<Event> eventList;

        public TestTask(List<Event> eventList) {
            this.eventList = eventList;
        }

        @Override
        public void run() {
            Random random = new Random();
            for(Event event : eventList){
                randomDataTask.exec(event);

                random.setSeed(System.currentTimeMillis());
                int sleepTime = random.nextInt(10*10);
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    logger.error("程序异常", e);
                }
            }
        }
}


}
