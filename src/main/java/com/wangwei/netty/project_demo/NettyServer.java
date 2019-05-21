package com.wangwei.netty.project_demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * <这个其实Netty线程模型中的其中的多线程模型></>
 * 这个是Netty的服务端的代码模板方法，
 * @Author: wangwei
 * @Date: 2019/5/9 8:02
 * @Version 1.0
 */
public class NettyServer {

    /*
    * Netty中的几种线程模型：
    *   第一种：单线程模型，其实就是工作线程池组，没有专门接受请求的那个线程池组或者说交线程
    *   第二种：多线程模型，其实是有两个线程池组（就是下面代码的这种）
    *   第三种：主从多线程模型：这个是在第二种基础上把那个专门负责接受线程的线程变成一个线程池（bossGroup），也就是该一下里面的参数
    * */

    public static void main(String[] args) throws InterruptedException {

        //可以把这个看成是一个大Boss负责把这个公司的环境给搭建好，把人员给各就各位
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //这个相当于前台经理Boss（主要负责接待），这个组里包含多个事件循环,每个NioEventLoop包含1个selector和1个事件循环线程
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        //这个就是worker线程组，相当于技术经理主要负责产出
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        //这个其实就是绑定端口，初始化各种组件
       try {
       ChannelFuture channelFuture = serverBootstrap
               //前者是一个父线程池事件组，后者参数是子线程池事件组（其实就是专门用来处理具体的任务的）
               .group(boosGroup, workerGroup)
               //指定 Channel 的类型. 因为是服务器端, 因此使用了 NioServerSocketChannel.
               .channel(NioServerSocketChannel.class)
               //这个是给bossGroup所用的
               .handler(new LoggingHandler(LogLevel.DEBUG))
               //设置数据的处理器.这个是工workGroup使用的,这里的MyChannelInitializer是我自己定义的继承的ChannelInitializer
               .childHandler(new  MyChannelInitializer())
               .bind(9090).sync();
       }finally {
           //这里是关闭线程组
//           boosGroup.shutdownGracefully();
//           workerGroup.shutdownGracefully();
       }

    }
}
