package com.stream.util;

import com.stream.info.Event;
import com.yjf.common.component.ExcelReadGenerator;
import com.yjf.common.component.impl.ExcelReadGeneratorImpl;
import com.yjf.common.util.RandomStringUtils;
import com.yjf.common.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by alpha on 2017/6/2.
 */
public class ExcelParser {

    private static Log logger = LogFactory.getLog(ExcelParser.class);
    private static String[] HEADER_INFO = { "merchantUserId", "userId", "event", "bankAccountNo", "tradeAmont", "createTime", "dataDisposeSehedule", "verifiedData", "bizCode"};
    private static SimpleDateFormat sdftime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static List<Event> parseExcel(String dataPath, Class clazz) throws IOException, ParseException {
        ClassLoader classLoader = clazz.getClassLoader();
        BufferedInputStream bufferedInputStream = (BufferedInputStream) classLoader.getResourceAsStream(dataPath);
        ExcelReadGenerator excelReadGenerator = new ExcelReadGeneratorImpl(0, HEADER_INFO.length,
                bufferedInputStream, isExcel2003(dataPath));
        List<Event> eventList = new ArrayList<Event>();
        String randomstr = RandomStringUtils.random(3);

        excelReadGenerator.nextRowValue();

        for (int i = 0; i < excelReadGenerator.totalRowNum(); i++) {
            String[] rowValue = excelReadGenerator.nextRowValue();
            Event event = Buileder.buildEvent();
            if(StringUtils.isNotBlank(rowValue[0])){
                event.setMerchantUserId(rowValue[0] + randomstr);
            }

            if(StringUtils.isNotBlank(rowValue[1])){
                event.setUserId(rowValue[1] + randomstr);
            }

            if(StringUtils.isNotBlank(rowValue[2])){
                event.setEvent(rowValue[2]);

                if(StringUtils.equals(rowValue[2], "DEPOSIT")){
                    event.setDirection("IN");
                    event.setDataBizType("DEDUCT");
                }else if(StringUtils.equals(rowValue[2], "WITHDRAW")){
                    event.setDirection("OUT");
                }
            }

            if(StringUtils.isNotBlank(rowValue[3])){
                event.setBankAccountNo(rowValue[3] + randomstr);
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

            if(StringUtils.isNotBlank(rowValue[8])){
                event.setBizCode(rowValue[8]);
            }

            eventList.add(event);
        }
        return eventList;
    }

    private static boolean isExcel2003(String filePath) {

        if (filePath.matches("^.+\\.(?i)(xls)$")) {
            return true;
        }
        return false;
    }
}
