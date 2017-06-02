package com.stream.factory;

import com.stream.info.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by dpingxian on 2017/5/25.
 */
public class EventFactory {

    private static List<Provider> factoryList = new ArrayList<>();
    private static  Random random = new Random();

    static {
        factoryList.add(new DepositFactory());
        factoryList.add(new WithdrawFactory());
        factoryList.add(new TradeFacrory());
    }

    public static Event buildEvent(){
        Event e =  factoryList.get(getRandomNum(0,factoryList.size())).produce().send();
        e.setTradeAmont(getRandomNum(1,999999));
        return e;
    }

    public static  int getRandomNum(int min,int max){
        return random.nextInt(max)%(max-min+1) + min;
    }

    public static void main(String[] args) {
        for (int i=0;i<20;i++){
            EventFactory.buildEvent();
        }

    }
}
