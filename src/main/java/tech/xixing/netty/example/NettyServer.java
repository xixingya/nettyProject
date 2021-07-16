package tech.xixing.netty.example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/7/4 9:45 AM
 */
public class NettyServer {
    public static void main(String[] args) throws Exception{
        //创建BossGroup   WorkerGroup
        //说明
        //1.创建两个线程组
        //2.bossGroup做连接请求，客户端处理给workerGroup
        final NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        /**
         * 配置参数，服务端启动对象
         */
        final ServerBootstrap bootstrap = new ServerBootstrap();

        try {
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128) //设置线程队列等待连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) //设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {//给workerGroup的eventLoop设置对应的管道处理器
                        //给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("客户socketChannel hashcode = "+ch.hashCode());
                            //可以使用一个集合管理SocketChannel，再推送消息时，
                            // 可以将业务加入到各个channel对应的NioEventLoop的
                            // taskQueue或者scheduleTaskQueue
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            //绑定端口并且同步处理，生成ChannelFuture对象
            final ChannelFuture channelFuture = bootstrap.bind(6666).sync();
            channelFuture.addListener((ChannelFutureListener) future -> {
                if(channelFuture.isSuccess()){
                    System.out.println("listening 6666 port is success");
                }else {
                    System.out.println("listening 6666 port fail");
                }
            });

            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();


        }



    }
}
