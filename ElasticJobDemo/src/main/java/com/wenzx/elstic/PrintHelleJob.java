package com.wenzx.elstic;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

public class PrintHelleJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("当前分片:" + shardingContext.getShardingItem() + "参数:" + shardingContext.getShardingParameter());
        System.out.println("Hello,This Is The World");
    }
}
