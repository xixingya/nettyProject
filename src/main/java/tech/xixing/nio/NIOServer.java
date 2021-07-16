package tech.xixing.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/6/17 9:07 AM
 */
public class NIOServer {

    public static void main(String[] args) throws Exception {
        //创建ServerSocketChannel -> SocketChannel

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //得到一个Selector
        Selector selector = Selector.open();
        //绑定端口6666，在服务器监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        //把serverSocketChannel注册到selector 关心的事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


        while (true) {

            //返回0则表示没有连接
            if (selector.select(1000) == 0) {
                System.out.println("wait one minutes no connection");
                continue;
            }

            //返回>0就返回SelectionKey集合
            //返回关注事件的集合
            final Set<SelectionKey> selectionKeys = selector.selectedKeys();
            final Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                final SelectionKey selectionKey = keyIterator.next();
                if (selectionKey.isAcceptable()) {//有客户端连接
                    //给这个客户端生成一个SocketChannel
                    final SocketChannel socketChannel = serverSocketChannel.accept();
                    //设置为非阻塞模式
                    socketChannel.configureBlocking(false);
                    System.out.println("client is connected ths socketchannel is"+socketChannel.hashCode());
                    //把socketChannel注册到selector，并且关注事件为读，同时给channel关联一个buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

 
                }
                else if (selectionKey.isReadable()) {
                    //拿到对应的channel
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    //获取到对应的buffer
                    ByteBuffer byteBuffer= (ByteBuffer)selectionKey.attachment();
                    socketChannel.read(byteBuffer);
                    System.out.println("from client get message: "+ new String(byteBuffer.array(),0, byteBuffer.position()));

                }
                keyIterator.remove();
            }


        }


    }
}
