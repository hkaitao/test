package com.stream.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.com.bsfit.frms.ds.pojo.DSOnlineOrder;
import com.stream.util.SpringUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;
import com.yjf.common.util.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2017/6/6.
 */
@Service("restTemplateSender")
public class RestTemplateSender implements ApplicationListener<ContextRefreshedEvent>,InitializingBean {

    protected static final Logger logger = LoggerFactory.getLogger(RestTemplateSender.class);

    protected static RestTemplate restTemplate;

    private static String url = "http://192.168.45.142:8700/push/sync";

    private static AtomicLong count_sec = new AtomicLong(0);

    private static AtomicLong count_min = new AtomicLong(0);

    private static Long processTime;

    private Timer timer = new Timer();

    public static void sendOrderQueue(List<Object> objs){
        long t1 = System.currentTimeMillis();
        List<Object> pojoList = conversionToDS(objs);
        restTemplate.postForEntity(url, new HttpEntity<>(pojoList), null);
        processTime = System.currentTimeMillis() - t1;
        count_sec.incrementAndGet();
        count_min.incrementAndGet();
    }


    private static List<Object> conversionToDS(List<Object> objs){
        List<Object> dsOrderlist = new ArrayList<>();
        String orderType = null;
        DSOnlineOrder dsOrder = null;
        try {
            for(Object obj : objs){
//                logger.info("orderObject推送结果:" + obj.toString());
                JSONObject json = JSON.parseObject(JSONObject.toJSONString(obj));
                if(json.containsKey("event")){
                    orderType = json.get("event")==null ? null :((Object)json.get("event")).toString();
                    orderType = conversionType(orderType);//将业务场景标示转为对应的业务编码
                }

                if(StringUtils.isEmpty(orderType)){
                    System.out.println("Assemble DSOrderObject fail, orderType is empty or not exist.");
                    continue;
                }
                dsOrder = loadDsOrder(json,orderType);
                if(dsOrder != null){
                    dsOrderlist.add(dsOrder);
                    dsOrder = null;
                }
            }
        } catch (Exception e) {
            System.out.println("Assemble DSOrderObject error!"+ e);
        }
        return dsOrderlist;
    }


    private static DSOnlineOrder loadDsOrder(JSONObject json,String businessType) {
        DSOnlineOrder dsOrder = new DSOnlineOrder();
        if(json == null || businessType == null) return null;
        dsOrder.setTradeType(businessType);//业务类型：1-充值，2-提现，3-交易
        //流水号
        String orderId = json.get("bizNo") == null ? null : (String)json.get("bizNo");
        if(StringUtils.isNotEmpty(orderId)){
            dsOrder.setOrderId(orderId);
        }

        //支付类型：1-代扣充值，2-线下充值，3-单笔提现，4-转账到卡单笔，5-及时到账，6-站内转账
        String payType = json.get("dataBizType") == null ? null : (String)json.get("dataBizType");
        if(StringUtils.isNotEmpty(payType)){
            switch (payType) {
                case "DEDUCT":
                    dsOrder.setPayType("1");
                    break;
                case "OFFLINE":
                    dsOrder.setPayType("2");
                    break;
                case "WITHDRAW_SINGLE":
                    dsOrder.setPayType("3");
                    break;
                case "WITHDRAW_CARD_SINGLE":
                    dsOrder.setPayType("4");
                    break;
                case "FAST_PAY":
                    dsOrder.setPayType("5");
                    break;
                case "TRANSFER":
                    dsOrder.setPayType("6");
                    break;
                default:
                    break;
            }
        }
        String userId = json.get("userId") == null ? null : (String)json.get("userId");
        if(StringUtils.isNotEmpty(userId)){//用户号
            dsOrder.setPayCustUserId(userId);
        }

        String cardNo = json.get("bankAccountNo") == null ?
                json.get("bankAccountNoDigest") == null ? null:json.get("bankAccountNoDigest").toString()
                :json.get("bankAccountNo").toString();
        if(StringUtils.isNotEmpty(cardNo)){//银行卡号
            dsOrder.setPayCustCardNo(cardNo);
        }

        Long transAmount = json.get("tradeAmont")==null ? null :Long.parseLong(((Object) json.get("tradeAmont")).toString());
        if(transAmount != null){//金额
            dsOrder.setTransAmount(transAmount*10);//精确到厘（原本是分所以扩大10倍）
        }

        String merchantId = json.get("merchantUserId") == null ? null : (String)json.get("merchantUserId");
        if(StringUtils.isNotEmpty(merchantId)){//商户号
            dsOrder.setCustomerId(merchantId);
        }

        String direction = json.get("direction") == null ? null : (String)json.get("direction");
        if(StringUtils.isNotEmpty(direction)){//资金方向
            if("IN".equals(direction)){
                dsOrder.setMoneyDirection("1");
            }else if("OUT".equals(direction)){
                dsOrder.setMoneyDirection("2");
            }else{
                dsOrder.setMoneyDirection(direction);
            }
        }

        Long createTime = json.get("createTime")==null ? null : Long.parseLong(json.get("createTime").toString());
        if(createTime != null){//交易时间
            if(createTime.toString().length() == 10){
                dsOrder.setTransTime(new Date(createTime *1000L));
            }else{
                dsOrder.setTransTime(new Date(createTime));
            }
        }


        String transStatus = json.get("dataDisposeSehedule") == null ? null : (String)json.get("dataDisposeSehedule");
        if(StringUtils.isNotEmpty(transStatus)){//交易状态
            if("SUCCESSED".equals(transStatus)){
                dsOrder.setStatus("1");
            }else if("FAILED".equals(transStatus)){
                dsOrder.setStatus("2");
            }else{
                System.out.println("交易状态不符合条件！");
            }
        }
        return dsOrder;
    }

    private static String conversionType(String businessType){
        String type = null;
        switch (businessType) {//业务类型：1-充值，2-提现，3-交易
            case "DEPOSIT":
                type = "1";
                break;
            case "WITHDRAW":
                type = "2";
                break;
            case "TRADE":
                type = "3";
                break;
            default:
                break;
        }
        return type;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        restTemplate = (RestTemplate) SpringUtil.getObject("restTemplate");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        timer.schedule(new StaticsTaskSec(), 0,1000);
        timer.schedule(new StaticsTaskMin(),0,60*1000);
    }


    private class StaticsTaskSec extends TimerTask {

        @Override
        public void run() {
            logger.info("**********流立方每秒发送数量为：{},抽样耗时:{}",count_sec.get(),processTime);
            count_sec.set(0L);
        }
    }

    private class StaticsTaskMin extends TimerTask{

        @Override
        public void run() {
            logger.info("**********流立方每分钟发送数量为：" + count_min.get());
            count_min.set(0L);
        }
    }
}
