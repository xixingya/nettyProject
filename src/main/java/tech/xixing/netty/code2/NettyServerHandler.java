package tech.xixing.netty.code2;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import tech.xixing.netty.codec.StudentPOJO;

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
         * 读取从客户端发送的studentPOJO.Student
         */

        Data.MyMessage myMessage = (Data.MyMessage ) msg;
        if(myMessage.getDataType()== Data.MyMessage.DataType.StudentType){
            System.out.println("student id = "+myMessage.getStudent().getId());
            System.out.println("student name = "+myMessage.getStudent().getName());
        }else if(myMessage.getDataType()== Data.MyMessage.DataType.WorkerType){
            System.out.println("worker age = "+myMessage.getWorker().getAge());
            System.out.println("Worker name = "+myMessage.getWorker().getName());
        }else {
            System.out.println("wrong message type");
        }
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
