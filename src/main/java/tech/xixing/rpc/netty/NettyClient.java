package tech.xixing.rpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.*;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/7/22 7:25 PM
 */
public class NettyClient {

    private static ExecutorService executorService=new ThreadPoolExecutor(10,20,10, TimeUnit.SECONDS,new ArrayBlockingQueue<>(10));

    private static NettyClientHandler clientHandler;

    public Object getBean(final Class<?> serviceClass,final String head){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),new Class<?>[]{serviceClass},(proxy,method,args)->{
            //这部分的代码，客户端每调用一次hello，就会进入到该代码
            if(clientHandler==null){
                initClient();
            }
            //设置参数,head是协议头，args[0就是调用客户端api hello(??)参数
            clientHandler.setParam(head+args[0]);
            return executorService.submit(clientHandler).get();
        });
    }

    private static void initClient() throws Exception{
        clientHandler=new NettyClientHandler();
        final NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        final Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        final ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(clientHandler);
                    }
                });
        bootstrap.connect("127.0.0.1",1234).sync();
    }
}
