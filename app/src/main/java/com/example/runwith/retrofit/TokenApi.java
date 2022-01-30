package com.example.runwith.retrofit;

import com.example.runwith.domain.LoginResponse;
import com.example.runwith.domain.TokenResponse;
import com.example.runwith.domain.UserEntity;
import com.example.runwith.domain.TokenEntity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

//통신을 정의해주는 interface
public interface TokenApi {
    //Call<응답클래스이름> 그냥 이름(@Body 보낼 클래스데이터 이름);

    @POST("/message/sendToken")
    Call<TokenResponse> sendToken(@Body TokenEntity token);
}
