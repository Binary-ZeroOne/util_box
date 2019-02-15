package org.zero.utilitybox.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * RSA加解密工具类
 * <p>
 * 说明：
 * 当明文长度小于等于密钥长度（Bytes）- 11时，使用普通的加解密方法即可
 * 当明文长度大于密钥长度（Bytes）- 11时，则需要使用分段加解密方法
 * 注意：当使用分段加解密时，双方需约定好分段的大小，以及分段加密后的拼装方式，最好是使用同一套分段代码
 *
 * @author 01
 * @date 2019-02-15
 */
public class RSAUtil {

    private final static String RSA_ALGORITHM = "RSA";

    private final static String CHARSET = "UTF-8";

    /**
     * 使用公钥加密
     *
     * @param originData   明文字符串
     * @param publicKeyStr 公钥字符串
     * @return 加密后的密文字符串
     */
    public static String encryptByPublicKey(String originData, String publicKeyStr) throws InvalidKeySpecException,
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        PublicKey publicKey = getPublicKey(publicKeyStr);
        checkPublicKey(publicKey);

        return encrypt(originData, publicKey);
    }

    /**
     * 使用私钥加密
     *
     * @param originData    明文字符串
     * @param privateKeyStr 私钥字符串
     * @return 加密后的密文字符串
     */
    public static String encryptByPrivateKey(String originData, String privateKeyStr) throws InvalidKeySpecException,
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        PrivateKey privateKey = getPrivateKey(privateKeyStr);
        checkPrivateKey(privateKey);

        return encrypt(originData, privateKey);
    }

    /**
     * 使用RSA秘钥加密
     *
     * @param originData 明文字符串
     * @param key        RSA秘钥
     * @return 加密后的密文字符串
     */
    public static String encrypt(String originData, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherText = cipher.doFinal(originData.getBytes());

        return Base64.getEncoder().encodeToString(cipherText);
    }

    /**
     * 使用私钥解密
     *
     * @param cipherText    密文字符串
     * @param privateKeyStr 私钥字符串
     * @return 解密后的明文字符串
     */
    public static String decryptByPrivateKey(String cipherText, String privateKeyStr) throws InvalidKeySpecException,
            NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, NoSuchPaddingException {
        PrivateKey privateKey = getPrivateKey(privateKeyStr);
        checkPrivateKey(privateKey);

        return decrypt(cipherText, privateKey);
    }

    /**
     * 使用公钥解密
     *
     * @param cipherText   密文字符串
     * @param publicKeyStr 公钥字符串
     * @return 解密后的明文字符串
     */
    public static String decryptByPublicKey(String cipherText, String publicKeyStr) throws InvalidKeySpecException,
            NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, NoSuchPaddingException {
        PublicKey publicKey = getPublicKey(publicKeyStr);
        checkPublicKey(publicKey);

        return decrypt(cipherText, publicKey);
    }

    /**
     * 使用RSA秘钥解密
     *
     * @param cipherText 密文字符串
     * @param key        RSA秘钥
     * @return 解密后的明文字符串
     */
    public static String decrypt(String cipherText, Key key) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] cipherTextBytes = Base64.getDecoder().decode(cipherText);
        byte[] decryptText = cipher.doFinal(cipherTextBytes);

        return new String(decryptText);
    }

    /**
     * 使用公钥分段加密
     *
     * @param originData   明文字符串
     * @param publicKeyStr 公钥字符串
     * @return 加密后的密文字符串
     */
    public static String splitEncryptByPublicKey(String originData, String publicKeyStr) throws InvalidKeySpecException,
            NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException {
        PublicKey publicKey = getPublicKey(publicKeyStr);
        checkPublicKey(publicKey);
        RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;

        return splitEncrypt(originData, rsaPublicKey, rsaPublicKey.getModulus());
    }

    /**
     * 使用私钥分段加密
     *
     * @param originData    明文字符串
     * @param privateKeyStr 私钥字符串
     * @return 加密后的密文字符串
     */
    public static String splitEncryptByPrivateKey(String originData, String privateKeyStr) throws InvalidKeySpecException,
            NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException {
        PrivateKey privateKey = getPrivateKey(privateKeyStr);
        checkPrivateKey(privateKey);
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;

        return splitEncrypt(originData, rsaPrivateKey, rsaPrivateKey.getModulus());
    }

    /**
     * 使用RSA分段加密
     *
     * @param originData 明文字符串
     * @param key        RSA秘钥
     * @param modulus    RSA秘钥模数
     * @return 加密后的密文字符串
     */
    public static String splitEncrypt(String originData, Key key, BigInteger modulus) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, originData.getBytes(CHARSET), modulus.bitLength());

        return Base64.getEncoder().encodeToString(result);
    }

    /**
     * 使用私钥分段解密
     *
     * @param cipherText    密文字符串
     * @param privateKeyStr 私钥字符串
     * @return 解密后的明文字符串
     */
    public static String splitDecryptByPrivateKey(String cipherText, String privateKeyStr) throws InvalidKeySpecException,
            NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException {
        PrivateKey privateKey = getPrivateKey(privateKeyStr);
        checkPrivateKey(privateKey);
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;

        return splitDecrypt(cipherText, rsaPrivateKey, rsaPrivateKey.getModulus());
    }

    /**
     * 使用公钥分段解密
     *
     * @param cipherText   密文字符串
     * @param publicKeyStr 公钥字符串
     * @return 解密后的明文字符串
     */
    public static String splitDecryptByPublicKey(String cipherText, String publicKeyStr) throws InvalidKeySpecException,
            NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException {
        PublicKey publicKey = getPublicKey(publicKeyStr);
        checkPublicKey(publicKey);
        RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;

        return splitDecrypt(cipherText, rsaPublicKey, rsaPublicKey.getModulus());
    }

    /**
     * 使用RSA分段解密
     *
     * @param cipherText 密文字符串
     * @param key        RSA秘钥
     * @param modulus    RSA秘钥模数
     * @return 解密后的明文字符串
     */
    public static String splitDecrypt(String cipherText, Key key, BigInteger modulus) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] result = rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, cipherText.getBytes(CHARSET), modulus.bitLength());

        return Base64.getEncoder().encodeToString(result);
    }

    /**
     * 检查公钥是否为RSAPublicKey的实例
     */
    private static void checkPublicKey(PublicKey publicKey) throws InvalidKeyException {
        if (!(publicKey instanceof RSAPublicKey)) {
            throw new InvalidKeyException("无效的公钥类型. [" + publicKey.getClass().getName() + "]");
        }
    }

    /**
     * 检查私钥是否为RSAPrivateKey的实例
     */
    private static void checkPrivateKey(PrivateKey privateKey) throws InvalidKeyException {
        if (!(privateKey instanceof RSAPrivateKey)) {
            throw new InvalidKeyException("无效的私钥类型. [" + privateKey.getClass().getName() + "]");
        }
    }

    /**
     * base64解码RSA秘钥字符串
     *
     * @param keyStr RSA秘钥字符串
     * @return 解码后的字节数组
     */
    private static byte[] getKeyBytes(String keyStr) {
        return Base64.getDecoder().decode(keyStr.replace("\r\n", ""));
    }

    /**
     * 将base64编码后的公钥字符串转成PublicKey实例
     *
     * @param publicKeyStr 公钥字符串
     * @return PublicKey实例
     */
    private static PublicKey getPublicKey(String publicKeyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = getKeyBytes(publicKeyStr);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);

        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 将base64编码后的私钥字符串转成PrivateKey实例
     *
     * @param privateKeyStr 私钥字符串
     * @return PrivateKey实例
     */
    private static PrivateKey getPrivateKey(String privateKeyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = getKeyBytes(privateKeyStr);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);

        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 分段加解密
     *
     * @param cipher  cipher
     * @param opMode  标识加密/解密模式等
     * @param data    data
     * @param keySize 秘钥长度
     * @return 加密/解密后的字节数组
     */
    private static byte[] rsaSplitCodec(Cipher cipher, int opMode, byte[] data, int keySize) {
        // 计算最大的分段长度
        int maxBlock = opMode == Cipher.DECRYPT_MODE ? keySize / 8 : keySize / 8 - 11;

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            int offSet = 0;
            byte[] buff;

            for (int i = 0; data.length > offSet; i++) {
                buff = data.length - offSet > maxBlock ? cipher.doFinal(data, offSet, maxBlock) :
                        cipher.doFinal(data, offSet, data.length - offSet);

                out.write(buff, 0, buff.length);
                offSet = i * maxBlock;
            }

            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        }
    }
}
