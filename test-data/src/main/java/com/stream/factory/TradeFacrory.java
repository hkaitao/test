package com.stream.factory;

/**
 * Created by dpingxian on 2017/5/25.
 */
public class TradeFacrory implements Provider{
    @Override
    public Sender produce() {
        return new TradeSender();
    }
}
