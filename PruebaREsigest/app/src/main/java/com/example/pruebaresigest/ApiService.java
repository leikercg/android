package com.example.pruebaresigest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/login")  // Usa la ruta de tu backend
    @Headers("Accept: application/json")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
