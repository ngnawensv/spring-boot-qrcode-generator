package com.belrose.springbootqrcodegenerator.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class QrCodeService {
    public byte[] generateQrCodeImage(String content, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType,Object> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.CHARACTER_SET,"UTF-8");
        BitMatrix bitMatrix;
        try {
            bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE,width,height);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Failed to generate QR code image. Cause: %s", e));
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedImage qrImage = toBufferedImage(bitMatrix);
        log.info("qrImage {}",qrImage);
        try{
            ImageIO.write(qrImage,"png",outputStream);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Failed to write QR code image to outputStream. cause: ",e));
        }
        return outputStream.toByteArray();
    }

    private BufferedImage toBufferedImage(BitMatrix bitMatrix) {
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0,0,width,height);
        graphics.setColor(Color.BLACK);
        for (int x=0; x<width;x++){
            for (int y=0;y<height;y++){
                if(bitMatrix.get(x,y)){
                    graphics.fillRect(x,y,1,1);
                }
            }
        }
        return image;
    }
}
