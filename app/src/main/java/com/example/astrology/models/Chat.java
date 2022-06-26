package com.example.astrology.models;

public class Chat {
    private String client,expert,message;
public Chat(){}

    public Chat(String client, String expert, String message) {
        this.client = client;
        this.expert = expert;
        this.message = message;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getExpert() {
        return expert;
    }

    public void setExpert(String expert) {
        this.expert = expert;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
