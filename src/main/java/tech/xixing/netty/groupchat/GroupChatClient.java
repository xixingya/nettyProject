package tech.xixing.netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/7/11 10:34 AM
 */
public class GroupChatClient {
    private final String host;

    private final int port;

    public GroupChatClient(String host,int port){
        this.host=host;
        this.port=port;
    }

    public void run() throws Exception{
        EventLoopGroup eventExecutors=new NioEventLoopGroup();

        Bootstrap bootstrap=new Bootstrap();

        try {
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            final ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("decode",new StringDecoder());
                            pipeline.addLast("encode",new StringEncoder());
                            pipeline.addLast(new GroupChatClientHandler());

                        }
                    });

            final ChannelFuture sync = bootstrap.connect(host, port).sync();
            final Channel channel = sync.channel();
            Scanner scanner=new Scanner(System.in);
            while (scanner.hasNext()){
                final String s = scanner.nextLine();
                channel.writeAndFlush(s);
            }

            channel.closeFuture().sync();
        }finally {
            eventExecutors.shutdownGracefully();
        }
    }


    public static void main(String[] args) throws Exception {
        final GroupChatClient groupChatClient = new GroupChatClient("127.0.0.1", 7000);
        groupChatClient.run();

    }

}
