package com.optimusDev.security;

public final class TpbRestCredentials {

    private String requestData;
    private String signature;

    public TpbRestCredentials(String requestData, String signature) {
        this.requestData = requestData;
        this.signature = signature;
    }

    public String getRequestData() {
        return requestData;
    }

    public String getSignature() {
        return signature;
    }

}