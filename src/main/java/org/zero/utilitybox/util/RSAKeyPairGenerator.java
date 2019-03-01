package org.zero.utilitybox.util;

import java.io.*;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

/**
 * RSA密钥对生成器
 *
 * @author 01
 * @date 2019-02-17
 **/
public class RSAKeyPairGenerator {

    private final static String ALGORITHM = "RSA";

    private final static int KEY_SIZE = 1024;

    private final static String PRIVATE_KEY_WINDOWS = "/privateKey.txt";
    private final static String PUBLIC_KEY_WINDOWS = "/publicKey.txt";

    private final static String PRIVATE_KEY_LINUX = "/privateKey";
    private final static String PUBLIC_KEY_LINUX = "/publicKey";

    private RSAPublicKey rsaPublicKey;

    private RSAPrivateKey rsaPrivateKey;

    public RSAPublicKey getRsaPublicKey() {
        return rsaPublicKey;
    }

    public void setRsaPublicKey(RSAPublicKey rsaPublicKey) {
        this.rsaPublicKey = rsaPublicKey;
    }

    public RSAPrivateKey getRsaPrivateKey() {
        return rsaPrivateKey;
    }

    public void setRsaPrivateKey(RSAPrivateKey rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
    }

    private RSAKeyPairGenerator() {
        this(KEY_SIZE, ALGORITHM);
    }

    private RSAKeyPairGenerator(int keySize) {
        this(keySize, ALGORITHM);
    }

    private RSAKeyPairGenerator(int keySize, String algorithm) {
        initSecretKeyPair(keySize, algorithm);
    }

    /**
     * 初始化RSA密钥对，生成公钥私钥
     */
    private void initSecretKeyPair(int keySize, String algorithm) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
            keyPairGenerator.initialize(keySize);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

            setRsaPrivateKey(rsaPrivateKey);
            setRsaPublicKey(rsaPublicKey);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("初始化密钥对发生异常, 未找到指定的密钥生成算法: " + ALGORITHM, e);
        }
    }

    public String getRsaPrivateKeyToString() {
        return Base64.getEncoder()
                .encodeToString(getRsaPrivateKey().getEncoded());
    }

    public String getRsaPublicKeyToString() {
        return Base64.getEncoder()
                .encodeToString(getRsaPublicKey().getEncoded());
    }

    public String getRsaPrivateKeyToMimeString() {
        String encodeStr = Base64.getMimeEncoder(64, "\r\n".getBytes())
                .encodeToString(getRsaPrivateKey().getEncoded());

        return "-----BEGIN RSA PRIVATE KEY-----\r\n" + encodeStr + "\r\n-----END RSA PRIVATE KEY-----";
    }

    public String getRsaPublicKeyToMimeString() {
        String encodeStr = Base64.getMimeEncoder(64, "\r\n".getBytes())
                .encodeToString(getRsaPublicKey().getEncoded());

        return "-----BEGIN PUBLIC KEY-----\r\n" + encodeStr + "\r\n-----END PUBLIC KEY-----";
    }

    public String getRsaPrivateKeyToUrlSafeString() {
        return Base64.getUrlEncoder()
                .encodeToString(getRsaPrivateKey().getEncoded());
    }

    public String getRsaPublicKeyToUrlSafeString() {
        return Base64.getUrlEncoder()
                .encodeToString(getRsaPublicKey().getEncoded());
    }

    public static RSAKeyPairGenerator initGenerator() {
        return new RSAKeyPairGenerator();
    }

    public static RSAKeyPairGenerator initGenerator(int keySize) {
        return new RSAKeyPairGenerator(keySize);
    }

    public void generatorRsaFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new RuntimeException("创建文件夹失败. path = " + path);
            }
        }

        String privateKeyFileName = isWindows() ? file.getPath() + PRIVATE_KEY_WINDOWS : file.getPath() + PRIVATE_KEY_LINUX;
        String publicKeyFileName = isWindows() ? file.getPath() + PUBLIC_KEY_WINDOWS : file.getPath() + PUBLIC_KEY_LINUX;

        File privateKeyFile = new File(privateKeyFileName);
        File publicKeyFile = new File(publicKeyFileName);

        generatorKeyFile(privateKeyFile, getRsaPrivateKeyToMimeString());
        generatorKeyFile(publicKeyFile, getRsaPublicKeyToMimeString());
        System.out.println("生成RSA密钥文件完成.");
    }

    private void generatorKeyFile(File file, String keyStr) {
        try (OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(keyStr.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException("生成密钥文件发生异常.", e);
        }
    }

    private boolean isWindows() {
        return System.getProperties().getProperty("os.name").toUpperCase().contains("WINDOWS");
    }

    private boolean isLinux() {
        return System.getProperties().getProperty("os.name").toUpperCase().contains("LINUX");
    }
}
