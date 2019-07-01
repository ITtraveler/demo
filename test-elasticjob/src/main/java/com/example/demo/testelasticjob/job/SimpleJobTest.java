/**
 * beedo.com Inc.
 * Copyright (c) 2018- All Rights Reserved.
 */
package com.example.demo.testelasticjob.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

/**
 * @author guosheng.huang
 * @version SimpleJobTest.java, v 0.1 2019年05月11日 18:49 guosheng.huang Exp $
 */
@Component
@Slf4j
public class SimpleJobTest implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("-----任务开始执行-----" + LocalTime.now().toString());

        log.info(shardingContext.toString());

        log.info("-----任务执行完成-----" + LocalTime.now().toString());
    }
}
