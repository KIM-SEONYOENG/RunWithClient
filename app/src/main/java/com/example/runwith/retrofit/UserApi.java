package com.example.runwith.retrofit;

import com.example.runwith.domain.LoginResponse;
import com.example.runwith.domain.UserEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

//통신을 정의해주는 interface
public interface UserApi {
    @GET("/user/login")
    Call<LoginResponse> login(@Query("id") String id, @Query("pw") String pw);

    @GET("/user/searchUser")
    Call<List<UserEntity>> findUser(@Query("keyword") String keyword);

    @POST("/user/join")
    Call<LoginResponse> join(@Body UserEntity newUser);
}
