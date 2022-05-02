package com.example.astrology.models;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
public interface JSONPlaceholder {

    @POST("birth_details")
    Call<Post> createPost(@Body Post post, String Key);



}
