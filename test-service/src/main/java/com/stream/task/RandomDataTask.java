package com.stream.task;



import com.stream.hq.HqProducer;
import com.stream.hq.HqSender;
import com.stream.info.Event;
import com.stream.pool.HqProcessor;
import com.stream.pool.Processor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by dpingxian on 2017/5/25.
 */

@Service("randomDataTask")
public class RandomDataTask extends AbstractPoolManagment<Event> implements ApplicationContextAware{


/*    @Autowired
    @Qualifier("hqProducer")
    private HqProducer hqProducer;*/

    private Random random = new Random();

    private List<JmsTemplate> jmsTemplateSet = new ArrayList<>();


    @Autowired
    @Qualifier("hqSender")
    private HqSender hqSender;


    @Override
    public void exec(Event event) {
        JmsTemplate jmsTemplate = getJmsTemplate();
        poolManagement.start(new Processor<>(event,jmsTemplate,hqSender));
    }

    private JmsTemplate getJmsTemplate() {
        int max = jmsTemplateSet.size();
        int index = random.nextInt(max)%(max+1);
        return jmsTemplateSet.get(index);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        String[] strnames = applicationContext.getBeanNamesForType(JmsTemplate.class);
        for (String strname : strnames) {
            jmsTemplateSet.add((JmsTemplate) applicationContext.getBean(strname));
        }
    }
}
    