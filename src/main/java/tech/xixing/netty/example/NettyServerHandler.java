package tech.xixing.netty.example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/7/4 10:06 AM
 *
 * 1.说明，自定义的handler，需要继承规定的某个HandlerAdapter
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     *
     * @param ctx 上下文对象，包含管道pipeline，通道 channel 管道做业务逻辑，通道做读写操作
     * @param msg 客户端发送的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        /**
         * 我们这里有一个非常耗时的业务->异步执行->提交到该channel对应的
         * NIOEventLoop的taskQueue中。
         * 相当于提交给一个只有一个线程的线程池中执行
         *
         */

        ctx.channel().eventLoop().execute(()->{
            try{
                Thread.sleep(10*1000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello client v2",CharsetUtil.UTF_8));
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        System.out.println("go on.......");

        /**
         * 用户提交
         */

        ctx.channel().eventLoop().schedule(()->{
            try{
                Thread.sleep(10*1000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello client v3",CharsetUtil.UTF_8));
            }catch (Exception e){
                e.printStackTrace();
            }
        },5, TimeUnit.SECONDS);


//        System.out.println("ChannelHandlerContext  = "+ctx);
//        //将msg转成ByteBuf,是netty提供的
//        ByteBuf byteBuf = (ByteBuf) msg;
//        System.out.println("client send msg:"+((ByteBuf) msg).toString(CharsetUtil.UTF_8));
//        System.out.println("client remote address = "+ctx.channel().remoteAddress());

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        /**
         * write and flush 是write +flush
         * 将数据写入缓存，并且刷新
         * 一般讲，我们需要对数据进行编码
         */
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello client",CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exception ="+cause.toString());
        ctx.channel().close();
        //super.exceptionCaught(ctx, cause);
    }
}
