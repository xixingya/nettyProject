package tech.xixing.netty.handler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/7/15 9:51 AM
 */
public class MyClient {
    public static void main(String[] args) throws Exception{
        NioEventLoopGroup group = new NioEventLoopGroup();

        try{
            Bootstrap bootstrap=new Bootstrap();

            final ChannelFuture channelFuture = bootstrap.group(group).
                    channel(NioSocketChannel.class)
                    .handler(new MyClientInitializer()).connect("127.0.0.1", 1234).sync();
            channelFuture.channel().closeFuture().sync();

        }finally {
            group.shutdownGracefully();
        }
    }
}
