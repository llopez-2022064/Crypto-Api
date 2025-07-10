package com.lesterlopez.encryptor.controller;

import com.lesterlopez.encryptor.dto.CryptoResponse;
import com.lesterlopez.encryptor.dto.TextRequest;
import com.lesterlopez.encryptor.service.CryptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "https://cryptopweb.netlify.app")
@RequestMapping("/api/v1/crypto")
public class CryptoController {

    private final CryptoService cryptoService;

    @PostMapping("/encrypt")
    public CryptoResponse encrypt(@RequestBody TextRequest request) throws Exception {
        return new CryptoResponse(cryptoService.encrypt(request.getText()));
    }

    @PostMapping("/decrypt")
    public CryptoResponse decrypt(@RequestBody TextRequest request) throws Exception {
        return new CryptoResponse(cryptoService.decrypt(request.getText()));
    }
}
