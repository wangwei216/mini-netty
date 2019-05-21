package com.wangwei.netty.project_demo;

import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Author: wangwei
 * @Date: 2019/5/9 23:10
 * @Version 1.0
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    //我们可以在这个流水线中定义很多的流水线的一些处理器
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new StringEncoder());
        socketChannel.pipeline().addLast(new StringDecoder());
        socketChannel.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
            @Override
            protected void channelRead0(ChannelHandlerContext ctx, String msg) {
                System.out.println("服务端接收并打印管道中的信息----->" + msg);
            }
        });
    }
}
