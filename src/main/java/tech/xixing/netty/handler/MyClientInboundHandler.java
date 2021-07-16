package tech.xixing.netty.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

/**
 * 这个inboundHandler是为了激活数据
 * @author liuzhifei
 * @version 1.0
 * @date 2021/7/15 9:57 AM
 */
public class MyClientInboundHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {

        System.out.println("get msg from server:"+ctx.channel().remoteAddress()+"msg:"+msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        /**
         * 分析
         * 1."abcdabcdabcdabcd"是16个字节
         * 2。该处理器后一个的handler是MyLongToByteEncoder
         * 3。MyLongToByteEncoder父类 是MessageToByteEncoder
         *
         * public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
         *         ByteBuf buf = null;
         *         try {
         *             if (acceptOutboundMessage(msg)) {//判断是否是需要处理的类型，如果是，则处理，如果不是，则直接放行到下一个handler
         *                 @SuppressWarnings("unchecked")
         *                 I cast = (I) msg;
         *                 buf = allocateBuffer(ctx, cast, preferDirect);
         *                 try {
         *                     encode(ctx, cast, buf);
         *                 } finally {
         *                     ReferenceCountUtil.release(cast);
         *                 }
         *
         *                 if (buf.isReadable()) {
         *                     ctx.write(buf, promise);
         *                 } else {
         *                     buf.release();
         *                     ctx.write(Unpooled.EMPTY_BUFFER, promise);
         *                 }
         *                 buf = null;
         *             } else {
         *                 ctx.write(msg, promise);
         *             }
         *         } catch (EncoderException e) {
         *             throw e;
         *         } catch (Throwable e) {
         *             throw new EncoderException(e);
         *         } finally {
         *             if (buf != null) {
         *                 buf.release();
         *             }
         *         }
         *     }
         */

        ctx.writeAndFlush(1234L);

        //ctx.writeAndFlush(Unpooled.copiedBuffer("abcdabcdabcdabcd", Charset.forName("utf-8")));
        //super.channelActive(ctx);
    }
}
