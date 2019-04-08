package com.github.kubenext.uaa.config;

import com.github.kubenext.async.ExceptionHandlingAsyncTaskExecutor;
import com.github.kubenext.properties.CommonProperties;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author shangjin.li
 */
@Configuration
@EnableAsync
@EnableScheduling
public class AsyncConfiguration implements AsyncConfigurer, SchedulingConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(AsyncConfiguration.class);

    private final CommonProperties commonProperties;

    public AsyncConfiguration(CommonProperties commonProperties) {
        this.commonProperties = commonProperties;
    }

    @Bean
    public Executor scheduledTaskExecutor() {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(
            commonProperties.getAsync().getCorePoolSize(),
            new BasicThreadFactory.Builder().namingPattern("scheduled-pool-%d").daemon(true).build()
        );
        return executorService;
    }

    @Override
    @Bean(name = "taskExecutor")
    public Executor getAsyncExecutor() {
        logger.debug("Creating Async Task Executor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(commonProperties.getAsync().getCorePoolSize());
        executor.setMaxPoolSize(commonProperties.getAsync().getMaxPoolSize());
        executor.setQueueCapacity(commonProperties.getAsync().getQueueCapacity());
        executor.setThreadNamePrefix("async-executor-");
        return new ExceptionHandlingAsyncTaskExecutor(executor);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {

    }
}
