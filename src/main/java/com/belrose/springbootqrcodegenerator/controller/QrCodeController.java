package com.belrose.springbootqrcodegenerator.controller;

import com.belrose.springbootqrcodegenerator.config.AppConfig;
import com.belrose.springbootqrcodegenerator.service.QrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class QrCodeController {
    private final QrCodeService qrCodeService;
    private final AppConfig appConfig;

    public QrCodeController(QrCodeService qrCodeService, AppConfig appConfig) {
        this.qrCodeService = qrCodeService;
        this.appConfig = appConfig;
    }

    @GetMapping(value = "/qrCode",produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQrCode(@RequestHeader("content") String content){
        return ResponseEntity.ok().body(qrCodeService.generateQrCodeImage(content,appConfig.getWidth(),appConfig.getHeight()));
    }

}
