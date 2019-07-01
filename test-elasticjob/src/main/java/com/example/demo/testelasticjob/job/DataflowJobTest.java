/**
 * beedo.com Inc.
 * Copyright (c) 2018- All Rights Reserved.
 */
package com.example.demo.testelasticjob.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author guosheng.huang
 * @version DataflowJobTest.java, v 0.1 2019年05月24日 21:07 guosheng.huang Exp $
 */
@Component
public class DataflowJobTest implements DataflowJob<String> {
    private int count;

    @Override
    public List<String> fetchData(ShardingContext shardingContext) {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < RandomUtils.nextInt(1, 10); i++) {
            datas.add(MessageFormat.format("jobName:{0},data:{1}", shardingContext.getJobName(), count++));
        }

        System.out.println("fetchData ok data-long:"+datas.size());
        return datas;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<String> data) {
        System.out.println("deal data:" + data);
    }
}
