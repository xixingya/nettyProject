package tech.xixing.netty.protocoltcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

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
                            final ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new MessageProtoColToByteEncoder());
                            pipeline.addLast(new ByteToMessageProtocolDecoder());
                            pipeline.addLast(new MyClientHandler());

                        }
                    }).connect("127.0.0.1", 1234).sync();
            channelFuture.channel().closeFuture().sync();

        }finally {
            group.shutdownGracefully();
        }
    }

}
