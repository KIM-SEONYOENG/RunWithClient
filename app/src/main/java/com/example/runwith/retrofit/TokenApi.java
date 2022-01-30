package com.example.runwith.retrofit;

import com.example.runwith.domain.LoginResponse;
import com.example.runwith.domain.TokenResponse;
import com.example.runwith.domain.UserEntity;
import com.example.runwith.domain.UserToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

//통신을 정의해주는 interface
public interface TokenApi {

    @POST("/message/token")
    Call<LoginResponse> join(@Body UserEntity newUser);
    //Call<응답클래스이름> 그냥 이름(@Body 보낼 클래스데이터 이름);
    Call<TokenResponse> sendToken(@Body UserToken token);
}
