package io.sbed.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Description: XXXXX <br>
 * Copyright:DATANG SOFTWARE CO.LTD<br>
 *
 * @author fuxiangming
 * @date 2018/6/15 下午4:53
 */
public class HashEncryptor {

    /*
     * MD5转换成16进制字符串需要的基础数据。
     */
    private static final String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f"};

    public static final String HASH_ALGORITHM_MD5 = "MD5";

    public static final String HASH_ALGORITHM_SHA1 = "SHA-1";

    /**
     * 根据指定算法加密。
     *
     * @param str       待加密的字符串。
     * @param algorithm 加密算法。
     * @return 加密后的字符串
     */
    public static String encrypt(String str, String algorithm) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            result = byteArrayToHexString(md.digest(str.getBytes()));
        } catch (NoSuchAlgorithmException nsae) {
            throw new RuntimeException("[未支持的加密算法: " + algorithm + "]", nsae);
        }
        return result;
    }

    /*
     * 将字节数组转换成16进制字符串。
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuffer result = new StringBuffer();
        for (byte element : b) {
            result.append(byteToHexString(element));
        }
        return result.toString();
    }

    /*
     * 将一个字节转换成16进制字符串。
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static void main(String[] args) {
        String javaEncryptedStr = encrypt("webasp", HASH_ALGORITHM_MD5);
        // JS加密密文
        String jsEncryptedStr = "93cc7e6ffddcd7690120585ef6b81195";
        System.out.println(javaEncryptedStr);
        System.out.println(jsEncryptedStr.equals(javaEncryptedStr));
        System.out.println(encrypt("12ab!@", HASH_ALGORITHM_MD5));
    }

}
