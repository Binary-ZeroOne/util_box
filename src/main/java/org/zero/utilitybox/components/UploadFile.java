package org.zero.utilitybox.components;

import java.io.InputStream;

/**
 * @ProjectName appBoxBackSystem
 * @Author: zeroJun
 * @Date: 2018/8/23 12:03
 * @Description:
 */
public interface UploadFile {

    /**
     * 判断文件是否存在
     *
     * @param fileName fileName
     * @return boolean
     */
    boolean fileExist(String fileName);

    /**
     * 上传文件
     *
     * @param inputStream inputStream
     * @param fileName    fileName
     */
    void upload(InputStream inputStream, String fileName);
}
