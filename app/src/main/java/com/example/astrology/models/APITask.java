package com.example.astrology.models;

/**
 *  @author Vedic Rishi Astro Pvt Ltd
 *  @description API Task Thread for a calling Vedic Rishi Astro API
 */

import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.net.Proxy;

public class APITask implements Runnable {

    private String apiKey;
    private String userId;
    public String url;
    RequestBody body;
    IAPITaskCallBack callBack;
    private final OkHttpClient client = new OkHttpClient();

    public APITask(String URL, String userId, String publicAPIKey, RequestBody body,IAPITaskCallBack callBack) {
        this.apiKey = publicAPIKey;
        this.userId = userId;
        this.url = URL;
        this.body = body;
        this.callBack = callBack;
    }

    private void callAPI() {

        try {
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            client.setAuthenticator(new Authenticator() {

                public Request authenticate(Proxy proxy, Response response) throws IOException {
                    String credential = Credentials.basic(userId, apiKey);
                    return response.request().newBuilder().header("Authorization", credential).build();
                }

                public Request authenticateProxy(Proxy proxy, Response response) throws IOException {
                    return null;
                }
            });

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()){
                callBack.onFailure("Request Failed with Error : "+response.code());
            }else{
                callBack.onSuccess(response.body().string());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        callAPI();
    }


}

