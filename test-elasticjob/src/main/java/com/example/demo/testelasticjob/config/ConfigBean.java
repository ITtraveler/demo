/**
 * beedo.com Inc.
 * Copyright (c) 2018- All Rights Reserved.
 */
package com.example.demo.testelasticjob.config;

import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.script.ScriptJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.example.demo.testelasticjob.job.DataflowJobTest;
import com.example.demo.testelasticjob.job.SimpleJobTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author guosheng.huang
 * @version ConfigBean.java, v 0.1 2019年05月11日 18:59 guosheng.huang Exp $
 */
@Configuration
public class ConfigBean {

/*    @Bean
    public SimpleJob simpleJob(){
        SimpleJob simpleJob = new SimpleJobTest();
        HashMap<Integer, String> param = new HashMap<>();
        param.put(0, ""+RandomUtils.nextInt());
        ShardingContexts jobParameter0 = new ShardingContexts("simpleJobTask-0", "simpleJobTask-0", 1, "jobParameter0", param);
        ShardingContext shardingContext = new ShardingContext(jobParameter0, 1);

        simpleJob.execute(shardingContext);
        return simpleJob;
    }*/

    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter zookeeperRegistryCenter() {
        ZookeeperConfiguration zkConfig = new ZookeeperConfiguration("127.0.0.1:2181", "elasticJob-simpleTask");
        ZookeeperRegistryCenter zookeeperRegistryCenter = new ZookeeperRegistryCenter(zkConfig);
        return zookeeperRegistryCenter;
    }


    /****** simpleJob ******/
    @Bean(initMethod = "init")
    public JobScheduler jobScheduler(SimpleJob simpleJob, ZookeeperRegistryCenter regCenter) {
        SpringJobScheduler springJobScheduler = new SpringJobScheduler(simpleJob, regCenter,
                simpleJobConfiguration("jobParameterA", "0/10 * * * * ?", 1, "param0"));
        return springJobScheduler;
    }


    @Bean(initMethod = "init")
    public JobScheduler jobScheduler2(SimpleJob simpleJob, ZookeeperRegistryCenter regCenter) {
        SpringJobScheduler springJobScheduler = new SpringJobScheduler(simpleJob, regCenter,
                simpleJobConfiguration("jobParameterB", "0/10 * * * * ?", 1, "param1"));
        return springJobScheduler;
    }


    public LiteJobConfiguration simpleJobConfiguration(String jobName, String cron, int shardingTotalCount, String param) {
        JobCoreConfiguration.Builder jobConfig0 = JobCoreConfiguration.newBuilder(jobName, cron, shardingTotalCount)
                .jobParameter(param)
                .description("just a simpleJobTest");
        SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(jobConfig0.build(),
                SimpleJobTest.class.getCanonicalName());
        LiteJobConfiguration.Builder builder =
                LiteJobConfiguration.newBuilder(simpleJobConfiguration).overwrite(true);
        return builder.build();
    }


    /******* dataflowJob ******/

    @Bean(initMethod = "init")
    public JobScheduler dataflowJobScheduler(DataflowJob dataflowJob, ZookeeperRegistryCenter regCenter) {
        SpringJobScheduler springJobScheduler = new SpringJobScheduler(dataflowJob, regCenter,
                dataflowJobConfiguration("dataflowJobTest0", "0/10 * * * * ?", 1, "param"));
        return springJobScheduler;
    }

    public LiteJobConfiguration dataflowJobConfiguration(String jobName, String cron, int shardingTotalCount, String param) {
        JobCoreConfiguration.Builder jobConfig = JobCoreConfiguration.newBuilder(jobName, cron, shardingTotalCount)
                .jobParameter(param)
                .description("just a dataflowJobTest");
        DataflowJobConfiguration dataflowJobConfiguration = new DataflowJobConfiguration(jobConfig.build(),
                DataflowJobTest.class.getCanonicalName(),false);
        LiteJobConfiguration.Builder builder =
                LiteJobConfiguration.newBuilder(dataflowJobConfiguration).overwrite(true);
        return builder.build();
    }


    /******* scriptJob ******/
    @Bean(initMethod = "init")
    public JobScheduler scriptJobScheduler(ZookeeperRegistryCenter regCenter) {
        return new JobScheduler(regCenter, scriptJobConfiguration());
    }

    public LiteJobConfiguration scriptJobConfiguration() {
        JobCoreConfiguration.Builder jobConfig0 = JobCoreConfiguration.newBuilder("scriptJobTest", "0/10 * * * * ?", 1)
                .jobParameter("param1")
                .description("just a scriptJobTest");
        ScriptJobConfiguration jobConfiguration = new ScriptJobConfiguration(jobConfig0.build(), "/Users/hgspiece" +
                "/Documents/job.sh");//"echo \"hello job\""
        LiteJobConfiguration.Builder builder =
                LiteJobConfiguration.newBuilder(jobConfiguration).overwrite(true);
        return builder.build();
    }

}
