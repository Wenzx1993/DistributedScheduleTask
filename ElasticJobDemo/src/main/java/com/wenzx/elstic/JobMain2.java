package com.wenzx.elstic;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

public class JobMain2 {

    public static void main(String[] args) {
        String zkHost = "192.168.119.141:2182";
        String nameSpace = "elstic-job-hello";
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(zkHost,nameSpace);
        CoordinatorRegistryCenter coordinatorRegistryCenter = new ZookeeperRegistryCenter(zookeeperConfiguration);
        coordinatorRegistryCenter.init();
        JobCoreConfiguration printHello = JobCoreConfiguration.newBuilder("PrintHello", "*/4 * * * * ?", 2).build();
        SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(printHello,PrintHelleJob.class.getName());
        new JobScheduler(coordinatorRegistryCenter, LiteJobConfiguration.newBuilder(simpleJobConfiguration).build()).init();
    }
}
