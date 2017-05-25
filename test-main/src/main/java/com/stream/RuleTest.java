package com.stream;

import com.stream.info.Event;
import com.stream.util.Buileder;
import com.yjf.common.component.ExcelReadGenerator;
import com.yjf.common.component.impl.ExcelReadGeneratorImpl;
import com.yjf.common.util.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by alpha on 2017/5/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication
@Configuration
public class RuleTest {

    private String[] HEADER_INFO = { "merchantUserId", "userId", "event", "bankAccountNo", "tradeAmont", "createTime"};
    private SimpleDateFormat sdftime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void testRule1() throws IOException, ParseException {
       String dataPath = "/rule1/情况1-测试跨月.xlsx";
        fillAndSendData(dataPath);
    }

    public boolean fillAndSendData(String dataPath) throws IOException, ParseException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        FileInputStream fileInputStream = new FileInputStream(dataPath);
        ExcelReadGenerator excelReadGenerator = new ExcelReadGeneratorImpl(0, HEADER_INFO.length,
                fileInputStream, isExcel2003(dataPath));

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

            /*远程调用http接口*/
        }
        return true;
    }

    private boolean isExcel2003(String filePath) {

        if (filePath.matches("^.+\\.(?i)(xls)$")) {
            return true;
        }
        return false;
    }
}
