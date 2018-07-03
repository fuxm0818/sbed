package io.sbed.common.utils;

import org.apache.shiro.util.ByteSource;

import java.io.Serializable;

/**
 * Description: 描述信息 <br>
 * Copyright:DATANG SOFTWARE CO.LTD <br>
 *
 * @author fuxiangming
 * @date created in 2018/7/3 下午5:28
 */
public class ByteSourceUtils {
    public static ByteSource bytes(byte[] bytes) {
        return new SimpleByteSource(bytes);
    }

    public static ByteSource bytes(String arg0) {
        return new SimpleByteSource(arg0.getBytes());
    }


}


class SimpleByteSource extends org.apache.shiro.util.SimpleByteSource
        implements Serializable {

    private static final long serialVersionUID = 5528101080905698238L;

    public SimpleByteSource(byte[] bytes) {
        super(bytes);
        // TODO 自动生成的构造函数存根
    }

}