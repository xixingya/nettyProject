package tech.xixing.nio.groupchat;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/6/25 7:54 PM
 */
public class GroupChatClient {

    private SocketChannel channel;
    private final String HOST = "127.0.0.1";
    private final int PORT =6667;
    private Selector selector;

    private SocketChannel socketChannel;
    private String username;

    public GroupChatClient() throws Exception{
        selector = Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST,PORT));
        //设置非阻塞
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);

        username = socketChannel.getLocalAddress().toString();

        System.out.println(username+"is ok.....");

    }

    public void sendMsg(String msg){
        msg = username+"say:"+msg;
        trySendMsg(msg);
    }

    public void readMsg(){
        tryReadMsg();
    }

    private void trySendMsg(String msg){

        try{
            socketChannel.write(ByteBuffer.wrap(msg.getBytes()));

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    private void tryReadMsg(){
        try{
            int readChannels = selector.select();
            if(readChannels>0){
                final Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    final SelectionKey key = iterator.next();
                    if(key.isReadable()){
                        final SocketChannel channel =(SocketChannel)key.channel();
                        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
                        channel.read(byteBuffer);
                        String msg = new String(byteBuffer.array(),0,byteBuffer.position());
                        System.out.println(msg);
                    }
                    iterator.remove();
                }
            }
        }catch (Exception e){

        }
    }

    public static void main(String[] args) throws Exception{
        //启动客户端
        final GroupChatClient groupChatClient = new GroupChatClient();
        new Thread(()->{
            while (true){
                try{
                    groupChatClient.readMsg();
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();

                }
            }
        }).start();
        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNext()){
            String s = scanner.nextLine();
            groupChatClient.sendMsg(s);
        }
    }




}
