package tech.xixing.nio;

import java.nio.IntBuffer;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/6/6 9:45 AM
 */
public class  BasicBuffer {

    public static void main(String[] args) {
        //举例说明Buffer的使用（简单说明）
        //创建一个Buffer，大小为5，即可存放5个int
        IntBuffer intBuffer=IntBuffer.allocate(5);
        //向buffer中存放数据
        for(int i=0;i<intBuffer.capacity()-1;i++){
            intBuffer.put(i*2);
        }

        //从buffer中取数据
        //将buffer转换，读写切换
        intBuffer.flip();
        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }

    }
}
