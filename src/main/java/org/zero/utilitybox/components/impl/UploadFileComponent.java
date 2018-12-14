package org.zero.utilitybox.components.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Callback;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zero.utilitybox.components.UploadFile;
import org.zero.utilitybox.config.OssConfig;

import java.io.InputStream;

/**
 * @ProjectName applicationBox
 * @Author: zeroJun
 * @Date: 2018/8/1 10:42
 * @Description: 上传文件组件
 */
@Component
public class UploadFileComponent implements UploadFile {

    private final OssConfig ossConfig;
    private final String callbackBody;

    @Autowired
    public UploadFileComponent(OssConfig ossConfig) {
        this.ossConfig = ossConfig;
        this.callbackBody = "{\\\"bucket\\\":${bucket},\\\"object\\\":${object},"
                + "\\\"mimeType\\\":${mimeType},\\\"size\\\":${size},\\\"etag\\\":${etag}," +
                "\\\"imageInfo.height\\\":${imageInfo.height},\\\"imageInfo.width\\\":${imageInfo.width}," +
                "\\\"imageInfo.format\\\":${imageInfo.format}}";
    }


    @Override
    public boolean fileExist(String fileName) {
        OSS ossClient = null;
        try {
            // 创建OSSClient实例
            ossClient = new OSSClient(ossConfig.getEndpoint(),
                    ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());

            return ossClient.doesObjectExist(ossConfig.getBucketName(), fileName);
        } finally {
            if (ossClient != null) {
                // 关闭OSSClient
                ossClient.shutdown();
            }
        }
    }

    @Override
    public void upload(InputStream inputStream, String fileName) {
        // 创建OSSClient实例
        OSS ossClient = null;

        try {
            ossClient = new OSSClient(ossConfig.getEndpoint(),
                    ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
            // 上传文件流
            PutObjectRequest putObjectRequest = new PutObjectRequest(ossConfig.getBucketName(), fileName, inputStream);
            // 设置回调
            putObjectRequest.setCallback(getCallbackObj());

            PutObjectResult result = ossClient.putObject(putObjectRequest);
            result.getResponse();
        } finally {
            if (ossClient != null) {
                // 关闭OSSClient
                ossClient.shutdown();
            }
        }
    }

    /**
     * 获取回调实例对象
     *
     * @return Callback
     */
    private Callback getCallbackObj() {
        // 实例化回调对象
        Callback callback = new Callback();
        // 设置回调url
        callback.setCallbackUrl(ossConfig.getCallbackUrl());
        // 设置回调参数
        callback.setCallbackBody(callbackBody);
        callback.setCalbackBodyType(Callback.CalbackBodyType.JSON);

        return callback;
    }
}
