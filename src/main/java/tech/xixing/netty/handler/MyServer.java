package tech.xixing.netty.handler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/7/15 9:37 AM
 */
public class MyServer {

    public static void main(String[] args) throws Exception{
        NioEventLoopGroup bossGroup =new NioEventLoopGroup();
        NioEventLoopGroup workerGroup =new NioEventLoopGroup();

        try{
            ServerBootstrap bootstrap=new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup).
                    channel(NioServerSocketChannel.class)
                    .childHandler(new MyServerInitializer());

            final ChannelFuture channelFuture = bootstrap.bind(1234).sync();
            channelFuture.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
