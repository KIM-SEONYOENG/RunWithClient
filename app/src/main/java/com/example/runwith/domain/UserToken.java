package com.example.runwith.domain;

import com.google.gson.annotations.SerializedName;

public class UserToken {
    @SerializedName("id")
    private String id;

    @SerializedName("token")
    private String token;

    public UserToken(String id, String token) {
        this.id = id;
        this.token = token;
    }
}
