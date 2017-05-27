package com.stream;

import com.stream.info.Event;
import com.stream.util.Buileder;
import com.yjf.common.component.ExcelReadGenerator;
import com.yjf.common.component.impl.ExcelReadGeneratorImpl;
import com.yjf.common.util.StringUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    private String[] HEADER_INFO = { "merchantUserId", "userId", "event", "bankAccountNo", "tradeAmont", "createTime", "verifiedData"};
    private SimpleDateFormat sdftime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String PROFILE = "stest";

    @BeforeClass
    public static void initEnv() {
        System.setProperty("spring.profiles.active", PROFILE);
    }

    @Test
    public void testRule1() throws IOException, ParseException {
        boolean result1 = sendToStreamData("rule1/发送到流立方的数据.xlsx");
        boolean result2 = checkVerifiedData("rule1/验证的数据.xlsx");
        Assert.assertTrue("规则1通过\n", result2 == true);

        /*dataPath = "rule1/情况2-测试相邻月.xlsx";
        boolean result2 = sendToStreamData(dataPath);
        Assert.assertTrue("规则1-测试相邻月通过\n", result2 == true);*/
    }

    public boolean sendToStreamData(String dataPath) throws IOException, ParseException {
        List<Event> eventList = parseExcel(dataPath);
        for(Event event : eventList){
            /*发送到流立方*/
        }
        return true;
    }

    public boolean checkVerifiedData(String dataPath) throws IOException, ParseException {
        List<Event> eventList = parseExcel(dataPath);
        for(Event event : eventList){
            /*验证每次调用的结果是否和预先设置的一致*/
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
                event.setExtraData(rowValue[6]);
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
