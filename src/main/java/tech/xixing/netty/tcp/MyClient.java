package tech.xixing.netty.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import tech.xixing.netty.handler.MyClientInitializer;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/7/19 7:23 PM
 */
public class MyClient {
    public static void main(String[] args) throws Exception{
        NioEventLoopGroup group = new NioEventLoopGroup();

        try{
            Bootstrap bootstrap=new Bootstrap();

            final ChannelFuture channelFuture = bootstrap.group(group).
                    channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new MyClientHandler());
                        }
                    }).connect("127.0.0.1", 1234).sync();
            channelFuture.channel().closeFuture().sync();

        }finally {
            group.shutdownGracefully();
        }
    }

}
