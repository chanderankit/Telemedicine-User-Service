package com.telemedicine.user.retrofit.authentication;

import com.telemedicine.user.model.payload.Token;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthClient {
    @POST("/api/v1/auth/token")
    Single<Response<Token>> generateToken(@Body Token token);
}
