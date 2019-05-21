package com.wangwei.netty.project_demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Date;

/**
 * @Author: wangwei
 * @Date: 2019/5/9 9:04
 * @Version 1.0
 */
public class NettyClient {

    public static void main(String[] args) throws InterruptedException {

        Bootstrap bootstrap = new Bootstrap();
        //这里是定义一个事件组，其实本质就是他的父类MultithreadEventExecutorGroup维护了一个线程池 EventExecutor children
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
        //指定 Channel 的类型. 因为是客户端, 因此使用了NioSocketChannel. Channel 是通过工厂方法 ChannelFactory.newChannel()
                .channel(NioSocketChannel.class)
        //设置数据的处理器. ChannelInitializer的目的是在pipeline的处理器中先占个位置类似占位符
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        //这里是配置一个解码器
                        ch.pipeline().addLast(new StringEncoder());
                    }
                });
        //连接服务器地址和端口并且绑定通道
        Channel channel = bootstrap.connect("127.0.0.1", 9090).channel();

        while (true) {
            channel.writeAndFlush(new Date() + "《来自客户端写入的消息》->hello world!");
            Thread.sleep(2000);
        }
    }
}
