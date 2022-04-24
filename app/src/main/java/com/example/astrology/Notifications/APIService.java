package com.example.astrology.Notifications;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAjWM-Gr8:APA91bFbP_v-hG42wCMswrWb5GqP4SXoo07ErDJljuWtwBCDz5k_nHFiX1GGxRVTrz3i8MDRLUh-aeJ-7sLdNawnIEeWiDQdRcZix5yI_cdlGy0mye4fP8RtnakORh5Zd_l6Hq1wH9d8"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
