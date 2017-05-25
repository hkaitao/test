package com.stream.factory;

import com.stream.info.Event;

/**
 * Created by dpingxian on 2017/5/25.
 */
public class TradeSender implements Sender{
    @Override
    public Event send() {
        System.out.println("交易");
        return new Event();
    }
}
