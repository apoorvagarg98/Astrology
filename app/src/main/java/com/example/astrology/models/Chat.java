package com.example.astrology.models;

public class Chat {
    private String senderId, reciever,message;
public Chat(){}

    public Chat(String senderId, String reciever, String message) {
        this.senderId = senderId;
        this.reciever = reciever;

        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
