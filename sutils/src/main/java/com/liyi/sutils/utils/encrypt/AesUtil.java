package com.liyi.sutils.utils.encrypt;

import android.text.TextUtils;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES 加密工具类（对称加密，用于替代 DES）
 * <p>
 * 参考链接：http://www.cnblogs.com/whoislcj/p/5473030.html
 */
public final class AesUtil {
    private final static String HEX = "0123456789ABCDEF";
    /* AES 是加密方式 CBC 是工作模式 PKCS5Padding 是填充模式 */
    private static final String CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";
    /* AES 加密 */
    private static final String AES = "AES";
    /* SHA1PRNG 强随机种子算法, 要区别 4.2 以上版本的调用方法 */
    private static final String SHA1PRNG = "SHA1PRNG";

    private AesUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 生成随机数，可以当做动态的密钥 加密和解密的密钥必须一致，不然将不能解密
     *
     * @return 密钥
     */
    public static String generateKey() {
        try {
            SecureRandom localSecureRandom = SecureRandom.getInstance(SHA1PRNG);
            byte[] bytes_key = new byte[20];
            localSecureRandom.nextBytes(bytes_key);
            String str_key = toHex(bytes_key);
            return str_key;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对密钥进行处理
     *
     * @param seed 密钥的字节流数据
     * @return 处理后的字节流密钥
     */
    private static byte[] getRawKey(byte[] seed) {
        byte[] result = null;
        try {
            KeyGenerator kgen = null;
            kgen = KeyGenerator.getInstance(AES);
            // for android
            SecureRandom sr = null;
            // 在4.2 以上版本中，SecureRandom 获取方式发生了改变
            if (android.os.Build.VERSION.SDK_INT >= 17) {
                sr = SecureRandom.getInstance(SHA1PRNG, "Crypto");
            } else {
                sr = SecureRandom.getInstance(SHA1PRNG);
            }
            // for Java
            // secureRandom = SecureRandom.getInstance(SHA1PRNG);
            sr.setSeed(seed);
            // 256 bits or 128 bits,192bits
            kgen.init(128, sr);
            // AES 中 128 位密钥版本有 10 个加密循环，192 比特密钥版本有 12 个加密循环，256 比特密钥版本则有 14 个加密循环
            SecretKey skey = kgen.generateKey();
            result = skey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 加密
     *
     * @param key       密钥
     * @param plaintext 明文
     * @return 加密后的数据
     */
    public static String encrypt(String key, String plaintext) {
        if (TextUtils.isEmpty(plaintext)) {
            return plaintext;
        }
        try {
            byte[] result = encrypt(key, plaintext.getBytes());
            return Base64Encoder.encode(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密
     *
     * @param key   密钥
     * @param plain 明文
     * @return 加密后的数据
     */
    private static byte[] encrypt(String key, byte[] plain) {
        byte[] encrypted = null;
        try {
            byte[] raw = getRawKey(key.getBytes());
            SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
            Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            encrypted = cipher.doFinal(plain);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return encrypted;
    }

    /**
     * 解密
     *
     * @param key       密钥
     * @param encrypted 加密数据
     * @return 解密后返回的数据
     */
    public static String decrypt(String key, String encrypted) {
        if (TextUtils.isEmpty(encrypted)) {
            return encrypted;
        }
        try {
            byte[] enc = Base64Decoder.decodeToBytes(encrypted);
            byte[] result = decrypt(key, enc);
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param key       密钥
     * @param encrypted 加密数据
     * @return 解密后返回的数据
     */
    private static byte[] decrypt(String key, byte[] encrypted) {
        byte[] decrypted = null;
        try {
            byte[] raw = getRawKey(key.getBytes());
            SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
            Cipher cipher = null;
            cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            decrypted = cipher.doFinal(encrypted);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return decrypted;
    }

    /**
     * 二进制转字符
     *
     * @param buf
     * @return
     */
    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }
}
