package com.stream;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by Administrator on 2017/5/24.
 */
@Configuration
public class ExecutorConfigration {

    @Bean(name = "dataTaskExecutor")
    public ThreadPoolTaskExecutor dataTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

        threadPoolTaskExecutor.setCorePoolSize(1);


//        threadPoolTaskExecutor .setCorePoolSize(1);
//        threadPoolTaskExecutor.setKeepAliveSeconds(100);
//        threadPoolTaskExecutor.setMaxPoolSize(100);
//        threadPoolTaskExecutor.setQueueCapacity(1000);
//        threadPoolTaskExecutor.setAwaitTerminationSeconds(60);
//        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(Boolean.TRUE);
        return threadPoolTaskExecutor;
    }

}
