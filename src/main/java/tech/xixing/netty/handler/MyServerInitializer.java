package tech.xixing.netty.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/7/15 9:41 AM
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        final ChannelPipeline pipeline = ch.pipeline();

        //入栈的handler，decode都是入栈的handler，即inbound
        pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MyLongToByteEncoder());
        //outboundhandler一定要放在最后一个inbound之前，
        // 保证inbound在write的时候，可以往前找到outbound
        pipeline.addLast(new MyServerInboundHandler());



    }
}
