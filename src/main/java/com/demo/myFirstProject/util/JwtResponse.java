package com.demo.myFirstProject.util;

public class JwtResponse {
    private String token;
    private String header;
    private String payload;
    private String signature;

    public JwtResponse(String token, String header, String payload, String signature) {
        this.token = token;
        this.header = header;
        this.payload = payload;
        this.signature = signature;
    }

    // Getters and setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getHeader() { return header; }
    public void setHeader(String header) { this.header = header; }
    public String getPayload() { return payload; }
    public void setPayload(String payload) { this.payload = payload; }
    public String getSignature() { return signature; }
    public void setSignature(String signature) { this.signature = signature; }
}
