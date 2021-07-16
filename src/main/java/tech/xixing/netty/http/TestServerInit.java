package tech.xixing.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/7/6 9:50 AM
 */
public class TestServerInit extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //向管道加入处理器

        //得到管道
        final ChannelPipeline pipeline = ch.pipeline();

        //加入netty提供的httpServerCodeC 编码解码 codec->[code -  decoder]
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());
        pipeline.addLast("MyHttpServerHandler",new TestHttpServerHandler());

        System.out.println("ok......");


    }
}
