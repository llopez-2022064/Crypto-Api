package com.lesterlopez.encryptor.dto;

public class CryptoResponse {
    private String result;

    public CryptoResponse(String result){
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
