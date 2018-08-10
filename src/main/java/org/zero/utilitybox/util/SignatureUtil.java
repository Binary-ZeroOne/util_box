package org.zero.utilitybox.util;

import com.aliyun.oss.common.utils.BinaryUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URI;
import java.net.URLDecoder;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * @ProjectName applicationBox
 * @Author: zeroJun
 * @Date: 2018/8/2 15:02
 * @Description: 阿里云oss上传文件回调验证签名工具类
 */
@Slf4j
public class SignatureUtil {

    private static final String AUTHORIZATION = "authorization";
    private static final String XOSSPUBKEYURL = "x-oss-pub-key-url";
    private static final String START_ADDR = "http://gosspublic.alicdn.com/";
    private static final String START_ADDR2 = "https://gosspublic.alicdn.com/";
    private static final String KEY_START = "-----BEGIN PUBLIC KEY-----";
    private static final String KEY_END = "-----END PUBLIC KEY-----";

    /**
     * 获取验签结果，post数据由内部读取
     *
     * @param request request
     * @return boolean
     */
    public static boolean getCheckResult(HttpServletRequest request) {
        int len = Integer.parseInt(request.getHeader("content-length"));
        String ossCallbackBody;
        try {
            ossCallbackBody = getPostBody(request.getInputStream(), len);
        } catch (IOException e) {
            log.error("", e);
            throw new RuntimeException(e);
        }
        return getCheckResult(request, ossCallbackBody);
    }

    /**
     * 获取验签结果，post数据由外部传入
     *
     * @param request  request
     * @param jsonData jsonData
     * @return boolean
     */
    public static boolean getCheckResult(HttpServletRequest request, String jsonData) {
        String authorizationInput = request.getHeader(AUTHORIZATION);
        byte[] signature = BinaryUtil.fromBase64String(authorizationInput);
        String xOssPubKeyUrl = request.getHeader(XOSSPUBKEYURL);
        byte[] pubKey = BinaryUtil.fromBase64String(xOssPubKeyUrl);
        String pubKeyAddr = new String(pubKey);

        if (!pubKeyAddr.startsWith(START_ADDR) && !pubKeyAddr.startsWith(START_ADDR2)) {
            System.out.println("pub key addr must be oss addrss");
            return false;
        }

        String retString = executeGet(pubKeyAddr);
        retString = retString.replace(KEY_START, "");
        retString = retString.replace(KEY_END, "");

        String uri = request.getRequestURI();
        String decodeUri;
        try {
            decodeUri = URLDecoder.decode(uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
            throw new RuntimeException(e);
        }
        String queryString = request.getQueryString();
        String authStr = decodeUri;
        if (queryString != null && !"".equals(queryString)) {
            authStr += "?" + queryString;
        }
        authStr += "\n" + jsonData;

        return doCheck(authStr, signature, retString);
    }

    /**
     * 验签
     *
     * @param content   content
     * @param sign      sign
     * @param publicKey publicKey
     * @return boolean
     */
    private static boolean doCheck(String content, byte[] sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = BinaryUtil.fromBase64String(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            java.security.Signature signature = java.security.Signature.getInstance("MD5withRSA");
            signature.initVerify(pubKey);
            signature.update(content.getBytes());

            return signature.verify(sign);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 请求公钥文件地址，获取公钥内容
     *
     * @param url url
     * @return String
     */
    @SuppressWarnings({"finally"})
    private static String executeGet(String url) {
        BufferedReader in = null;

        String content;
        try {
            // 定义HttpClient
            @SuppressWarnings("resource")
            HttpClient client = HttpClientBuilder.create().build();

            // 实例化HTTP方法
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);

            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder sb = new StringBuilder();
            String line;
            String nl = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line).append(nl);
            }
            in.close();
            content = sb.toString();
        } catch (Exception e) {
            log.error("", e);
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    // 最后要关闭BufferedReader
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }

    /**
     * 获取post的body内容
     *
     * @param is         is
     * @param contentLen contentLen
     * @return String
     */
    private static String getPostBody(InputStream is, int contentLen) {
        if (contentLen > 0) {
            int readLen = 0;
            int readLengthThisTime;
            byte[] message = new byte[contentLen];
            try {
                while (readLen != contentLen) {
                    readLengthThisTime = is.read(message, readLen, contentLen - readLen);
                    if (readLengthThisTime == -1) {
                        // Should not happen.
                        break;
                    }
                    readLen += readLengthThisTime;
                }
                return new String(message);
            } catch (IOException e) {
                log.error("", e);
                throw new RuntimeException(e);
            }
        }
        return "";
    }
}
