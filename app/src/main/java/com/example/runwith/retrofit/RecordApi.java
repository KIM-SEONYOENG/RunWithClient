package com.example.runwith.retrofit;

import com.example.runwith.domain.RecordEntity;
import com.example.runwith.domain.UserEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RecordApi {
    @POST
    Call postRecord(@Body RecordEntity newRecord);

    @GET("/record/showRecord")
    Call<List<RecordEntity>> showRecord(@Query("id") String id);
}
