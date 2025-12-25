package com.benefix.employeestarter.config;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

  private static final Logger log = LoggerFactory.getLogger(AsyncConfig.class);

  @Value("${async.executor.core-pool-size:2}")
  private int corePoolSize;

  @Value("${async.executor.max-pool-size:10}")
  private int maxPoolSize;

  @Value("${async.executor.queue-capacity:500}")
  private int queueCapacity;

  @Value("${async.executor.thread-name-prefix:async-}")
  private String threadNamePrefix;

  @Value("${async.executor.await-termination-seconds:30}")
  private int awaitTerminationSeconds;

  @Override
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(corePoolSize);
    executor.setMaxPoolSize(maxPoolSize);
    executor.setQueueCapacity(queueCapacity);
    executor.setThreadNamePrefix(threadNamePrefix);
    executor.setWaitForTasksToCompleteOnShutdown(true);
    executor.setAwaitTerminationSeconds(awaitTerminationSeconds);
    return executor;
  }

  @Override
  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    return (Throwable ex, Method method, Object... params) ->
        log.error("Async error in method {}: {}", method.getName(), ex.getMessage(), ex);
  }
}
