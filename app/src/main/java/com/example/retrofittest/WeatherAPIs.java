package com.example.retrofittest;

import com.example.retrofittest.pojos.WResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPIs {

    @GET("/data/2.5/weather?")
    Call<WResponse> getWeatherByCity(@Query("q") String city, @Query("appid") String apiKey);
}