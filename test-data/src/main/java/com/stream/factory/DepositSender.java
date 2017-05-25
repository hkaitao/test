package com.stream.factory;

import com.stream.info.Event;

/**
 * Created by dpingxian on 2017/5/25.
 */
public class DepositSender implements Sender{

    @Override
    public Event send() {
        System.out.println("充值");
        return new Event();
    }
}
