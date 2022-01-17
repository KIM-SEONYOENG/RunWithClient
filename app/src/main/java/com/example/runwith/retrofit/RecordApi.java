package com.example.runwith.retrofit;

import com.example.runwith.domain.RecordEntity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RecordApi {
    @POST
    Call postRecord(@Body RecordEntity newRecord);
}
