package com.stream;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stream.hq.HqSender;
import com.stream.info.Event;
import com.stream.util.Buileder;
import com.stream.util.HttpClientNewSender;
import com.yjf.common.component.ExcelReadGenerator;
import com.yjf.common.component.impl.ExcelReadGeneratorImpl;
import com.yjf.common.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by alpha on 2017/5/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { RuleTest.class })
@SpringBootApplication
@Configuration
public class RuleTest {

    private Log logger = LogFactory.getLog(this.getClass());
    private String[] HEADER_INFO = { "merchantUserId", "userId", "event", "bankAccountNo", "tradeAmont", "createTime", "dataDisposeSehedule", "verifiedData"};
    private SimpleDateFormat sdftime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String PROFILE = "stest";

    @Autowired
    private HqSender hqSender;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private HttpClientNewSender httpClientNewSender;

    @BeforeClass
    public static void initEnv() {
        System.setProperty("spring.profiles.active", PROFILE);
    }

    @Test
    public void testConnection(){
        String request = "[{\n" +
                "\t    \"@type\":\"cn.com.bsfit.frms.obj.AuditObject\",\n" +
                "\t    \"frms_biz_code\": \"PAY.DK\"\n" +
                "    }]";
        String response = HttpClientNewSender.send(request);
        JSONArray jsonArray = JSON.parseArray(response);
        Map<String, Object> jsonmap = (Map<String, Object>) jsonArray.get(0);
        JSONArray jsonArray1 = (JSONArray) jsonmap.get("risks");
        if(jsonArray1 != null && jsonArray1.size() > 0){
            Map<String, Object> jsonmap1 = (Map<String, Object>) jsonArray1.get(0);
            System.out.println(jsonmap1.get("ruleName"));
        }
    }

    @Test
    public void testRule1() throws IOException, ParseException {
        boolean result1 = sendToStreamData("rule1/发送到流立方的数据.xlsx");
        logger.info("****************************开始验证规则1***********************************");
        boolean result2 = checkVerifiedData("rule1/验证数据.xlsx");
        if(result2){
            logger.info("规则1验证通过");
        }else{
            logger.info("规则1验证不可描述");
        }

    }

    @Test
    public void testRule2() throws IOException, ParseException {
        boolean result1 = sendToStreamData("rule2/发送到流立方的数据.xlsx");
        logger.info("****************************开始验证规则2***********************************");
        boolean result2 = checkVerifiedData("rule2/验证数据.xlsx");
        if(result2){
            logger.info("规则2验证通过");
        }else{
            logger.info("规则2验证不可描述");
        }

    }

    @Test
    public void testRule3() throws IOException, ParseException {
        boolean result1 = sendToStreamData("rule3/发送到流立方的数据.xlsx");
        logger.info("****************************开始验证规则3***********************************");
        boolean result2 = checkVerifiedData("rule3/验证数据.xlsx");
        if(result2){
            logger.info("规则3验证通过");
        }else{
            logger.info("规则3验证不可描述");
        }

    }

    @Test
    public void testRule4() throws IOException, ParseException {
        boolean result1 = sendToStreamData("rule4/发送到流立方的数据.xlsx");
        logger.info("****************************开始验证规则4***********************************");
        boolean result2 = checkVerifiedData("rule4/验证数据.xlsx");
        if(result2){
            logger.info("规则4验证通过");
        }else{
            logger.info("规则4验证不可描述");
        }

    }

    @Test
    public void testRule5() throws IOException, ParseException {
        boolean result1 = sendToStreamData("rule5/发送到流立方的数据.xlsx");
        logger.info("****************************开始验证规则5***********************************");
        boolean result2 = checkVerifiedData("rule5/验证数据.xlsx");
        if(result2){
            logger.info("规则5验证通过");
        }else{
            logger.info("规则5验证不可描述");
        }

    }

    @Test
    public void testRule6() throws IOException, ParseException {
        boolean result1 = sendToStreamData("rule6/发送到流立方的数据.xlsx");
        logger.info("****************************开始验证规则6***********************************");
        boolean result2 = checkVerifiedData("rule6/验证的数据.xlsx");
        if(result2){
            logger.info("规则6验证通过");
        }else{
            logger.info("规则6验证不可描述");
        }

    }

    @Test
    public void testRule7() throws IOException, ParseException {
        boolean result1 = sendToStreamData("rule7/发送到流立方的数据.xlsx");
        logger.info("****************************开始验证规则7***********************************");
        boolean result2 = checkVerifiedData("rule7/验证数据.xlsx");
        if(result2){
            logger.info("规则7验证通过");
        }else{
            logger.info("规则7验证不可描述");
        }

    }

    @Test
    public void testRule8() throws IOException, ParseException {
        boolean result1 = sendToStreamData("rule8/发送到流立方的数据.xlsx");
        logger.info("****************************开始验证规则8***********************************");
        boolean result2 = checkVerifiedData("rule8/验证数据.xlsx");
        if(result2){
            logger.info("规则8验证通过");
        }else{
            logger.info("规则8验证不可描述");
        }

    }

    @Test
    public void testRule9() throws IOException, ParseException {
        boolean result1 = sendToStreamData("rule9/发送到流立方的数据.xlsx");
        logger.info("****************************开始验证规则9***********************************");
        boolean result2 = checkVerifiedData("rule9/验证数据.xlsx");
        if(result2){
            logger.info("规则9验证通过");
        }else{
            logger.info("规则9验证不可描述");
        }

    }


    public boolean sendToStreamData(String dataPath) throws IOException, ParseException {
        List<Event> eventList = parseExcel(dataPath);
        for(Event event : eventList){
            /*发送到流立方*/
            hqSender.send(jmsTemplate,JSONObject.toJSONString(event));
        }
        return true;
    }

    public boolean checkVerifiedData(String dataPath) throws IOException, ParseException {
        List<Event> eventList = parseExcel(dataPath);

        for(Event event : eventList){
            if(checkDataOnebyOne(event)){
                continue;
            }else{
                return false;
            }
        }
        return true;
    }

    public boolean checkDataOnebyOne(Event event){
        /*List<Event> events = new ArrayList<Event>();
        events.add(event);*/
        String responseJson = HttpClientNewSender.send(event);
        JSONArray jsonArray = JSON.parseArray(responseJson);
        Map<String,List<String>> ruleNames = new HashMap<String,List<String>>();

        if(jsonArray != null){
            Iterator iterator = jsonArray.iterator();

            while(iterator.hasNext()){
                /*验证每次调用的结果是否和预先设置的一致*/
                Map jsonmap = (Map) iterator.next();
                JSONArray jsonArray1 = (JSONArray) jsonmap.get("risks");

                if(jsonArray1 != null) {
                    Iterator mapIetetator = jsonArray1.iterator();

                    while (mapIetetator.hasNext()) {
                        Map ruleMap = (Map) mapIetetator.next();
                        String ruleName = (String) ruleMap.get("ruleName");
                        String uuid = (String) ruleMap.get("uuid");

                        List<String> ruleNameLsit = ruleNames.get(uuid);

                        if (ruleNameLsit != null) {
                            ruleNameLsit.add(ruleName);
                        } else {
                            ruleNameLsit = new ArrayList<String>();
                            ruleNameLsit.add(ruleName);
                        }
                    }
                }
            }
        }

        for(Map.Entry entry : ruleNames.entrySet()) {
            /*验证每次调用的结果是否和预先设置的一致*/
            List<String> ruleDescriptions = (List<String>) entry.getValue();

            for (String ruleDescription : ruleDescriptions) {
                String[] ruleInfos = ruleDescription.split(":");
                String ruleCode = ruleInfos[1];

                if(ruleCode != null && ruleCode.equalsIgnoreCase(event.getExtraData())){
                    return true;
                }else{
                    continue;
                }
            }
        }

        return false;
    }

    public List<Event> parseExcel(String dataPath) throws IOException, ParseException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        BufferedInputStream bufferedInputStream = (BufferedInputStream) classLoader.getResourceAsStream(dataPath);
        ExcelReadGenerator excelReadGenerator = new ExcelReadGeneratorImpl(0, HEADER_INFO.length,
                bufferedInputStream, isExcel2003(dataPath));
        List<Event> eventList = new ArrayList<Event>();
        excelReadGenerator.nextRowValue();

        for (int i = 0; i < excelReadGenerator.totalRowNum(); i++) {
            String[] rowValue = excelReadGenerator.nextRowValue();
            Event event = Buileder.buildEvent();
            if(StringUtils.isNotBlank(rowValue[0])){
                event.setMerchantUserId(rowValue[0]);
            }

            if(StringUtils.isNotBlank(rowValue[1])){
                event.setUserId(rowValue[1]);
            }

            if(StringUtils.isNotBlank(rowValue[2])){
                event.setEvent(rowValue[2]);

                if(StringUtils.equals(rowValue[2], "DEPOSIT")){
                    event.setDataBizType("DEDUCT");
                }
            }

            if(StringUtils.isNotBlank(rowValue[3])){
                event.setBankAccountNo(rowValue[3]);
            }

            if(StringUtils.isNotBlank(rowValue[4])){
                Long amount = Long.parseLong(rowValue[4]);
                event.setTradeAmont(amount);
            }

            if(StringUtils.isNotBlank(rowValue[5])){
                Date createTime = sdftime.parse(rowValue[5]);
                event.setCreateTime(createTime);
            }

            if(StringUtils.isNotBlank(rowValue[6])){
                event.setDataDisposeSehedule(rowValue[6]);
            }

            if(StringUtils.isNotBlank(rowValue[7])){
                event.setExtraData(rowValue[7]);
            }

            eventList.add(event);
        }
        return eventList;
    }

    private boolean isExcel2003(String filePath) {

        if (filePath.matches("^.+\\.(?i)(xls)$")) {
            return true;
        }
        return false;
    }
}
