package com.neo.job;

import com.neo.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: bcl
 * @Description:
 * @Date: Create in 8:52 下午 2021/1/19
 */
@Component
public class NewJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(NewJob.class);

    @PostConstruct
    public void init() throws ExecutionException, InterruptedException {

        CompletableFuture<Boolean> feature = null;
        for (int i = 0; i < 3; i++) {
            if (feature==null || !feature.get()){
                LOGGER.info("-------{}----",i);
                feature = CompletableFuture.supplyAsync(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return false;
                });
            }
        }
    }
}
