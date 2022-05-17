package com.example.astrology.models;


public interface IAPITaskCallBack {

    void onSuccess(String response);
    void onFailure(String error);
}
