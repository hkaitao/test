package com.yiji.test;

import com.yiji.boot.core.YijiBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by alpha on 2017/5/24.
 */
/*
@RunWith(SpringJUnit4ClassRunner.class)
*/
@SpringApplicationConfiguration(classes = { TestBase.class })
@YijiBootApplication(sysName = "yiji-adk-test", heraEnable = false, httpPort = 0)
@Configuration
public class TestBase {
}
