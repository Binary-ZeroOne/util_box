package org.zero.utilitybox.util;

import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @ProjectName platform
 * @Author: swang
 * @Date: 2018/7/5 14:52
 * @Description: 图片转base64
 */
public class BufferOfBase {

    /**
     * 图片转base64
     *
     * @param bufferedImage bufferedImage
     * @return String
     * @throws IOException IOException
     */
    public String getBase64Img(BufferedImage bufferedImage) throws IOException {
        StringBuilder base64Img = new StringBuilder();
        base64Img.append("data:image/jpeg;base64,");
        // bufferImage->base64
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", outputStream);
        Base64 base64 = new Base64();
        byte[] bytes = base64.encode(outputStream.toByteArray());
        base64Img.append(new String(bytes));

        return base64Img.toString();

    }

}
