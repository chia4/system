package com.gzu.system.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * 需求: 传入字符串内容，返回byte类型存放的png格式的图片
 * byte[] generateQRCode(String content, int width, int height)
 */

@Service
public class UtilService {

    /**
     * 传入字符串内容和二维码颜色，返回byte类型存放的png格式的二维码图片
     * 二维码默认白底0xFFFFFF
     *
     * @param content
     * @param width
     * @param height
     * @param color
     * @return byte[]
     * @throws WriterException
     */
    public byte[] generateQRCode(String content, int width, int height, String color) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        HashMap<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, "1");

        BitMatrix bitMatrix = null;
        try {
            bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageConfig con = new MatrixToImageConfig(Color.decode(color).hashCode(), Color.decode("0XFFFFFF").hashCode());

        try {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream, con);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }

}
