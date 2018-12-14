package com.example.demo.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

/**
 * 生成RSA公/私钥对
 *
 * @author 01
 * @createTime 2018-12-13
 */
public class GeneratorRSAKey {

    public static void main(String[] args) {
        jdkRSA();
    }

    public static void jdkRSA() {
        GeneratorRSAKey generatorKey = new GeneratorRSAKey();

        try {
            // 初始化密钥，产生公钥私钥对
            Object[] keyPairArr = generatorKey.initSecretkey();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPairArr[0];
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPairArr[1];

            System.out.println("\n------------------PublicKey------------------");
            System.out.println(Base64.getEncoder().encodeToString(rsaPublicKey.getEncoded()));

            System.out.println("\n------------------PrivateKey------------------");
            System.out.println(Base64.getEncoder().encodeToString(rsaPrivateKey.getEncoded()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化密钥，生成公钥私钥对
     *
     * @return Object[]
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     */
    private Object[] initSecretkey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

        Object[] keyPairArr = new Object[2];
        keyPairArr[0] = rsaPublicKey;
        keyPairArr[1] = rsaPrivateKey;

        return keyPairArr;
    }
}
