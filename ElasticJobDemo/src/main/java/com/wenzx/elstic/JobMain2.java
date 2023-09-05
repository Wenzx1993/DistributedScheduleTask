package com.wenzx.elstic;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.api.strategy.impl.RotateServerByNameJobShardingStrategy;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

public class JobMain2 {

    public static void main(String[] args) {
        String zkHost = "192.168.119.141:2182";
        String nameSpace = "elstic-job-hello";
        //配置分布式注册服务zk
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(zkHost, nameSpace);
        CoordinatorRegistryCenter coordinatorRegistryCenter = new ZookeeperRegistryCenter(zookeeperConfiguration);
        coordinatorRegistryCenter.init();
        //配置任务
        //任务分片：分片总数、分片参数、overwrite=true
        JobCoreConfiguration printHello = JobCoreConfiguration.newBuilder("PrintHello", "*/4 * * * * ?", 5)
                //分片参数
                .shardingItemParameters("0=test1,1=test2,2=test3,3=test4,4=test5")
                .build();
        SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(printHello, PrintHelleJob.class.getName());
        new JobScheduler(
                coordinatorRegistryCenter,
                LiteJobConfiguration.newBuilder(simpleJobConfiguration)
                        //分片策略，可自定义,默认为平均分算法，其他还有hash值奇数和偶数升降序算法，轮转算法
//                        .jobShardingStrategyClass(RotateServerByNameJobShardingStrategy.class.getName())
                        .overwrite(true)
                        .build())
                .init();
    }
}
