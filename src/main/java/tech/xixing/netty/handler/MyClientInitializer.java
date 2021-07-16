package tech.xixing.netty.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/7/15 9:53 AM
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        final ChannelPipeline pipeline = ch.pipeline();

        //加一个出栈的handler，即编码的handler
        pipeline.addLast(new MyLongToByteEncoder());

        //加一个入站的decoder
        pipeline.addLast(new MyByteToLongDecoder());
        //加一个入栈的handler，处理业务
        pipeline.addLast(new MyClientInboundHandler());

    }
}
