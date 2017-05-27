package com.stream;

import com.alibaba.fastjson.JSON;
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
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @BeforeClass
    public static void initEnv() {
        System.setProperty("spring.profiles.active", PROFILE);
    }

    @Test
    public void testRule1() throws IOException, ParseException {
        boolean result1 = sendToStreamData("rule1/发送到流立方的数据.xlsx");
        boolean result2 = checkVerifiedData("rule1/验证的数据.xlsx");
        Assert.assertTrue("规则1通过\n", result2 == true);

    }

    @Test
    public void testRule2() throws IOException, ParseException {
        boolean result1 = sendToStreamData("rule2/发送到流立方的数据.xlsx");
        boolean result2 = checkVerifiedData("rule2/验证的数据.xlsx");
        Assert.assertTrue("规则2通过\n", result2 == true);

    }

    @Test
    public void testRule3() throws IOException, ParseException {
        boolean result1 = sendToStreamData("rule3/发送到流立方的数据.xlsx");
        boolean result2 = checkVerifiedData("rule3/验证的数据.xlsx");
        Assert.assertTrue("规则3通过\n", result2 == true);

    }

    @Test
    public void testRule4() throws IOException, ParseException {
        boolean result1 = sendToStreamData("rule4/发送到流立方的数据.xlsx");
        boolean result2 = checkVerifiedData("rule4/验证的数据.xlsx");
        Assert.assertTrue("规则4通过\n", result2 == true);

    }

    @Test
    public void testRule5() throws IOException, ParseException {
        boolean result1 = sendToStreamData("rule5/发送到流立方的数据.xlsx");
        boolean result2 = checkVerifiedData("rule5/验证的数据.xlsx");
        Assert.assertTrue("规则5通过\n", result2 == true);

    }

    @Test
    public void testRule6() throws IOException, ParseException {
        boolean result1 = sendToStreamData("rule6/发送到流立方的数据.xlsx");
        boolean result2 = checkVerifiedData("rule6/验证的数据.xlsx");
        Assert.assertTrue("规则6通过\n", result2 == true);

    }

    @Test
    public void testRule7() throws IOException, ParseException {
        boolean result1 = sendToStreamData("rule7/发送到流立方的数据.xlsx");
        boolean result2 = checkVerifiedData("rule7/验证的数据.xlsx");
        Assert.assertTrue("规则7通过\n", result2 == true);

    }

    @Test
    public void testRule8() throws IOException, ParseException {
        boolean result1 = sendToStreamData("rule8/发送到流立方的数据.xlsx");
        boolean result2 = checkVerifiedData("rule8/验证的数据.xlsx");
        Assert.assertTrue("规则8通过\n", result2 == true);

    }

    @Test
    public void testRule9() throws IOException, ParseException {
        boolean result1 = sendToStreamData("rule9/发送到流立方的数据.xlsx");
        boolean result2 = checkVerifiedData("rule9/验证的数据.xlsx");
        Assert.assertTrue("规则9通过\n", result2 == true);

    }

    @Test
    public void testRule() throws IOException, ParseException {
       String jsonstr =
               "    {\n" +
               "        \"@type\": \"cn.com.bsfit.frms.obj.AuditResult\",\n" +
               "        \"customization\": {\n" +
               "            \"@type\": \"java.util.HashMap\"\n" +
               "        },\n" +
               "        \"items\": [],\n" +
               "        \"notifyPolicy\": {\n" +
               "            \"code\": \"NO\",\n" +
               "            \"name\": \"无通知\",\n" +
               "            \"priority\": 0\n" +
               "        },\n" +
               "        \"retCode\": \"200\",\n" +
               "        \"risks\": [\n" +
               "            {\n" +
               "                \"comments\": \"\",\n" +
               "                \"createTime\": \"2017-04-20 11:07:07\",\n" +
               "                \"customization\": {\n" +
               "                    \"@type\": \"java.util.HashMap\"\n" +
               "                },\n" +
               "                \"notifyPolicy\": {\n" +
               "                    \"code\": \"NO\",\n" +
               "                    \"name\": \"无通知\",\n" +
               "                    \"priority\": 0\n" +
               "                },\n" +
               "                \"riskTypes\": [\n" +
               "                    \"异常操作\"\n" +
               "                ],\n" +
               "                \"ruleName\": \"242 : 11111 : 用户注册时间为空测试\",\n" +
               "                \"rulePackageName\": \"cs\",\n" +
               "                \"score\": 45,\n" +
               "                \"uuid\": \"e9c602d3-9992-4a77-95a2-1b204827dafa\",\n" +
               "                \"verifyPolicy\": {\n" +
               "                    \"code\": \"MARK\",\n" +
               "                    \"failControl\": \"NO\",\n" +
               "                    \"name\": \"标记交易\",\n" +
               "                    \"priority\": 50,\n" +
               "                    \"succControl\": \"NO\"\n" +
               "                },\n" +
               "                \"weight\": 1\n" +
               "            },\n" +
               "            {\n" +
               "                \"comments\": \"\",\n" +
               "                \"createTime\": \"2017-04-20 11:07:07\",\n" +
               "                \"customization\": {\n" +
               "                    \"@type\": \"java.util.HashMap\"\n" +
               "                },\n" +
               "                \"notifyPolicy\": {\n" +
               "                    \"code\": \"NO\",\n" +
               "                    \"name\": \"无通知\",\n" +
               "                    \"priority\": 0\n" +
               "                },\n" +
               "                \"riskTypes\": [\n" +
               "                    \"异常操作\"\n" +
               "                ],\n" +
               "                \"ruleName\": \"249 : 99999 : 用户首次交易成功测试\",\n" +
               "                \"rulePackageName\": \"cs\",\n" +
               "                \"score\": 80,\n" +
               "                \"uuid\": \"e9c602d3-9992-4a77-95a2-1b204827dafa\",\n" +
               "                \"verifyPolicy\": {\n" +
               "                    \"code\": \"MARK\",\n" +
               "                    \"failControl\": \"NO\",\n" +
               "                    \"name\": \"标记交易\",\n" +
               "                    \"priority\": 50,\n" +
               "                    \"succControl\": \"NO\"\n" +
               "                },\n" +
               "                \"weight\": 1\n" +
               "            },\n" +
               "            {\n" +
               "                \"comments\": \"\",\n" +
               "                \"createTime\": \"2017-04-20 11:07:07\",\n" +
               "                \"customization\": {\n" +
               "                    \"@type\": \"java.util.HashMap\"\n" +
               "                },\n" +
               "                \"notifyPolicy\": {\n" +
               "                    \"code\": \"NO\",\n" +
               "                    \"name\": \"无通知\",\n" +
               "                    \"priority\": 0\n" +
               "                },\n" +
               "                \"riskTypes\": [\n" +
               "                    \"异常操作\"\n" +
               "                ],\n" +
               "                \"ruleName\": \"312 : gzxl : 规则训练测试专用\",\n" +
               "                \"rulePackageName\": \"cs\",\n" +
               "                \"score\": 66,\n" +
               "                \"uuid\": \"e9c602d3-9992-4a77-95a2-1b204827dafa\",\n" +
               "                \"verifyPolicy\": {\n" +
               "                    \"code\": \"MARK\",\n" +
               "                    \"failControl\": \"NO\",\n" +
               "                    \"name\": \"标记交易\",\n" +
               "                    \"priority\": 50,\n" +
               "                    \"succControl\": \"NO\"\n" +
               "                },\n" +
               "                \"weight\": 1\n" +
               "            }\n" +
               "        ],\n" +
               "        \"score\": 66,\n" +
               "        \"uuid\": \"e9c602d3-9992-4a77-95a2-1b204827dafa\",\n" +
               "        \"verifyPolicy\": {\n" +
               "            \"code\": \"MARK\",\n" +
               "            \"failControl\": \"NO\",\n" +
               "            \"name\": \"标记交易\",\n" +
               "            \"priority\": 50,\n" +
               "            \"succControl\": \"NO\"\n" +
               "        }\n" +
               "    }\n";
        JSONObject jsonArray = JSON.parseObject(jsonstr);
        System.out.println(jsonArray.get("notifyPolicy"));

    }

    public boolean sendToStreamData(String dataPath) throws IOException, ParseException {
        List<Event> eventList = parseExcel(dataPath);
        for(Event event : eventList){
            /*发送到流立方*/
            hqSender.send(JSONObject.toJSONString(event));
        }
        return true;
    }

    public boolean checkVerifiedData(String dataPath) throws IOException, ParseException {
        List<Event> eventList = parseExcel(dataPath);
        int flag = 0;
        for(Event event : eventList){
            /*验证每次调用的结果是否和预先设置的一致*/
            flag++;
            String jsonstr = HttpClientNewSender.send(event);
            JSONObject jsonObject = JSON.parseObject(jsonstr);
            String ruleName = (String) jsonObject.get("ruleName");

            if(event.getExtraData().equals("notTriggered")){
                /*不应该触发而触发了数据，说明错误了*/
                if(StringUtils.isBlank(ruleName)){
                    continue;
                }else{
                    logger.error(String.format("验证文件:%s，不该触发规则，却触发了规则，第%s条数据", dataPath, flag));
                    return false;
                }
            }else{
                if(StringUtils.isBlank(ruleName)){
                    /*改触发规则，不触发，也错误*/
                    logger.error(String.format("验证文件:%s，该触发规则，却不触发了规则，第%s条数据", dataPath, flag));
                    return false;
                }

                String[] ruleNamePropertis = ruleName.split(":");
                if(event.getExtraData().equalsIgnoreCase(ruleNamePropertis[1].trim())){
                    continue;
                }else{
                    logger.error(String.format("验证文件:%s，规则触发错误，第%s条数据，触发的规则", dataPath, flag, ruleName));
                    return false;
                }
            }
        }
        return true;
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
                BigDecimal bigDecimal = new BigDecimal(rowValue[4]);
                event.setTradeAmont(bigDecimal);
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
