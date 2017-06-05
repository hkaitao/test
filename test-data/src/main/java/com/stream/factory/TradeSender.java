package com.stream.factory;

import com.stream.info.Event;
import com.stream.time.SimsTime;
import com.stream.util.Buileder;

import java.util.Date;

/**
 * Created by dpingxian on 2017/5/25.
 */
public class TradeSender implements Sender{
    @Override
    public Event send() {
        Event event = Buileder.buildEvent();
        event.setEvent("TRADE");
        event.setBizCode("PAY.JY");
        event.setDataBizType("FAST_PAY");
        event.setDirection("OUT");
        event.setCreateTime(new Date(SimsTime.getTime()));
        return event;
    }
}
