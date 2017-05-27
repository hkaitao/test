package com.stream.task;



import com.stream.hq.HqSender;
import com.stream.info.Event;
import com.stream.pool.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dpingxian on 2017/5/25.
 */

@Service("randomDataTask")
public class RandomDataTask extends AbstractPoolManagment<Event> {

    @Autowired
    private HqSender hqSender;


    @Override
    public void exec(Event event) {
        poolManagement.start(new Processor<>(event,hqSender));
    }

}
    