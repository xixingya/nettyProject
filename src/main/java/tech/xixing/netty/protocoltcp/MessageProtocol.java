package tech.xixing.netty.protocoltcp;

import com.oracle.webservices.internal.api.databinding.DatabindingMode;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/7/19 7:32 PM
 */

public class MessageProtocol {
    private int len;
    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
