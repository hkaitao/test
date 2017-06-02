package com.stream.info;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alpha on 2017/5/24.
 */
public class Event {

    private String gid;

    private String bizNo;

    private String outBizNo;

    @AttibuteName(name = "frms_user_id")
    private String userId;

    @AttibuteName(name = "frms_trade_type")
    private String event;

    @AttibuteName(name = "frms_pay_type")
    private String dataBizType;

    @AttibuteName(name = "frms_trans_vol")
    private long tradeAmont;

    @AttibuteName(name = "frms_money_direction")
    private String direction;

    private String counterPartyUserId;

    @AttibuteName(name = "frms_customer_id")
    private String merchantUserId;

    @AttibuteName(name = "frms_card_no")
    private String bankAccountNo;

    private String bankAccountNoDigest;

    @AttibuteName(name = "frms_trans_time")
    private Date createTime;

    private Date finshedTime;

    private String dataDisposeSehedule;

    private String dataOperateType;

    private String extraData;

    private String bizCode;

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public String getOutBizNo() {
        return outBizNo;
    }

    public void setOutBizNo(String outBizNo) {
        this.outBizNo = outBizNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDataBizType() {
        return dataBizType;
    }

    public void setDataBizType(String dataBizType) {
        this.dataBizType = dataBizType;
    }

    public long getTradeAmont() {
        return tradeAmont;
    }

    public void setTradeAmont(long tradeAmont) {
        this.tradeAmont = tradeAmont;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getCounterPartyUserId() {
        return counterPartyUserId;
    }

    public void setCounterPartyUserId(String counterPartyUserId) {
        this.counterPartyUserId = counterPartyUserId;
    }

    public String getMerchantUserId() {
        return merchantUserId;
    }

    public void setMerchantUserId(String merchantUserId) {
        this.merchantUserId = merchantUserId;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getBankAccountNoDigest() {
        return bankAccountNoDigest;
    }

    public void setBankAccountNoDigest(String bankAccountNoDigest) {
        this.bankAccountNoDigest = bankAccountNoDigest;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getFinshedTime() {
        return finshedTime;
    }

    public void setFinshedTime(Date finshedTime) {
        this.finshedTime = finshedTime;
    }

    public String getDataDisposeSehedule() {
        return dataDisposeSehedule;
    }

    public void setDataDisposeSehedule(String dataDisposeSehedule) {
        this.dataDisposeSehedule = dataDisposeSehedule;
    }

    public String getDataOperateType() {
        return dataOperateType;
    }

    public void setDataOperateType(String dataOperateType) {
        this.dataOperateType = dataOperateType;
    }


    @Override
    public String toString() {
        return "Event{" +
                "gid='" + gid + '\'' +
                ", bizNo='" + bizNo + '\'' +
                ", outBizNo='" + outBizNo + '\'' +
                ", userId='" + userId + '\'' +
                ", event='" + event + '\'' +
                ", dataBizType='" + dataBizType + '\'' +
                ", tradeAmont=" + tradeAmont +
                ", direction='" + direction + '\'' +
                ", counterPartyUserId='" + counterPartyUserId + '\'' +
                ", merchantUserId='" + merchantUserId + '\'' +
                ", bankAccountNo='" + bankAccountNo + '\'' +
                ", bankAccountNoDigest='" + bankAccountNoDigest + '\'' +
                ", createTime=" + createTime +
                ", finshedTime=" + finshedTime +
                ", dataDisposeSehedule='" + dataDisposeSehedule + '\'' +
                ", dataOperateType='" + dataOperateType + '\'' +
                ", extraData='" + extraData + '\'' +
                ", bizCode='" + bizCode + '\'' +
                '}';
    }

    public Map toMap() throws IllegalAccessException {
        Map<String, Object> map = new HashMap<String, Object>();

        Class clazz = this.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for(Field field : fields){
            boolean isAnnotation = field.isAnnotationPresent(AttibuteName.class);
            String value = String.valueOf(field.get(this));
            if(isAnnotation){
                AttibuteName attibuteName = field.getAnnotation(AttibuteName.class);
                field.setAccessible(true);
                map.put(attibuteName.name(), value);
            }else{
                map.put(field.getName(), value);
            }
        }

        return map;
    }
}
