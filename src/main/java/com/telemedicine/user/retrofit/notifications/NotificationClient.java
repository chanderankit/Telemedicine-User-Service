package com.telemedicine.user.retrofit.notifications;


import com.telemedicine.user.model.payload.request.NotificationRequest;
import com.telemedicine.user.model.payload.response.NotificationResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NotificationClient {
    @POST("/api/v1/notification")
    Call<NotificationResponse> sendNotification(@Body NotificationRequest notificationRequest, @Query("type") String Mail);
}
