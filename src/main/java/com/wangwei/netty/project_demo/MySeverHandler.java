package com.wangwei.netty.project_demo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author: wangwei
 * @Date: 2019/5/21 18:28
 * @Version 1.0
 */
public class MySeverHandler extends SimpleChannelInboundHandler {

    //这个其实是当用户有时间过来的话，就会被workGroup线程组进行调用
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

        System.out.println("我被调用用了------>"+channelHandlerContext);
    }
}
