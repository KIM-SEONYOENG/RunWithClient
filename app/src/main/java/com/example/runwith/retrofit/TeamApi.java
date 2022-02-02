package com.example.runwith.retrofit;

import com.example.runwith.domain.TeamEntity;
import com.example.runwith.domain.TeamResponse;
import com.example.runwith.domain.TokenEntity;
import com.example.runwith.domain.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

//통신을 정의해주는 interface
public interface TeamApi {
    //Call<응답클래스이름> 그냥 이름(@Body 보낼 클래스데이터 이름);

    @POST("/team/invite")
    Call<TeamResponse> invite(@Body TeamEntity team);
}
