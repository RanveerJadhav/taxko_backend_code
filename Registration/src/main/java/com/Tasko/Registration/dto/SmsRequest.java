package com.Tasko.Registration.dto;

public class SmsRequest {
    private String to;
    private String message;

    // Constructors, getters, and setters

    public SmsRequest() {
        // Default constructor
    }

    public SmsRequest(String to, String message) {
        this.to = to;
        this.message = message;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
