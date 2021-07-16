package tech.xixing.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/6/25 7:09 PM
 */
public class GroupChatServer {

    //属性
    private Selector selector;

    private ServerSocketChannel listenChannel;

    private static final int PORT = 6667;

    public GroupChatServer() {
        try {
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            //绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置非阻塞模式
            listenChannel.configureBlocking(false);
            //注册  关心连接事件
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {

        }
    }

    public void listen() {
        tryListen();
    }

    /**
     * 读信息
     * @param key
     * @throws IOException
     */
    public void read(SelectionKey key) throws IOException {
        final SocketChannel channel = (SocketChannel) key.channel();
        try {
            doRead(key);
        } catch (IOException e) {
            System.out.println(channel.getRemoteAddress()+" is disconnect");
            key.cancel();
            channel.close();
            e.printStackTrace();
        }
    }

    /**
     * 给其他在线的客户端发送消息
     * @param msg
     * @param self
     */
    private void sendMsgToOthers(String msg, SocketChannel self) {
        final Set<SelectionKey> keys = selector.keys();
        keys.forEach(key -> {
            final Channel channel =  key.channel();
            if (channel instanceof SocketChannel && channel != self) {
                SocketChannel dest = (SocketChannel) channel;
                final ByteBuffer wrap = ByteBuffer.wrap(msg.getBytes());
                try {
                    dest.write(wrap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    private void doRead(SelectionKey key) throws IOException{
        final SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int count = socketChannel.read(byteBuffer);
        if (count > 0) {
            final String msg = new String(byteBuffer.array(), 0, byteBuffer.position());
            System.out.println("from client:" + socketChannel.getRemoteAddress() + "get msg:" + msg);
            //向其他客户端转发消息
            sendMsgToOthers(msg, socketChannel);
        }else {
            System.out.println(socketChannel.getRemoteAddress()+" is disconnect");
            key.cancel();
            socketChannel.close();
        }

    }

    private void tryListen() {
        try {
            while (true) {
                int count = selector.select(2000);
                if (count > 0) {
                    final Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        final SelectionKey key = iterator.next();
                        if (key.isAcceptable()) {
                            final SocketChannel socketChannel = listenChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);

                            System.out.println(socketChannel.getRemoteAddress() + " is online");
                        }
                        //读数据
                        if (key.isReadable()) {
                            System.out.println("read msg to everybody");
                            read(key);

                        }


                        iterator.remove();

                    }
                } else {
                    System.out.println("waiting........");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    public static void main(String[] args) {
        final GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();

    }
}
