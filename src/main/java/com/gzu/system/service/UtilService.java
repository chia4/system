package com.gzu.system.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 *   需求: 传入字符串内容，返回byte类型存放的png格式的图片
 *   byte[] generateQRCode(String content, int width, int height)
 */

@Service
public class UtilService {


    public byte[] generateQRCode(String content, int width, int height,int color)throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageConfig con = new MatrixToImageConfig( color, 0xFFFFFF ) ;

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream,con);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }

    public void generateQRCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }

}
