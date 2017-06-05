package com.stream.factory;

import com.stream.info.Event;
import com.stream.time.SimsTime;
import com.stream.util.Buileder;

import java.util.Date;

/**
 * Created by dpingxian on 2017/5/25.
 */
public class WithdrawSender implements Sender{
    @Override
    public Event send() {
        Event event = Buileder.bulidPayEvent();
        event.setEvent("WITHDRAW");
        event.setBizCode("PAY.TX");
        event.setDataBizType("WITHDRAW_SINGLE");
        event.setDirection("OUT");
        event.setCreateTime(new Date(SimsTime.getTime()));
        return event;
    }
}
