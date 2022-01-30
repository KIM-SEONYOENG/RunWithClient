package com.example.runwith.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.runwith.R;
import com.example.runwith.domain.LoginResponse;
import com.example.runwith.domain.UserEntity;
import com.example.runwith.retrofit.RetrofitClient;
import com.example.runwith.retrofit.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class JoinActivity extends AppCompatActivity {
    private final static String TAG = "join";

    Button btnSubmit;
    EditText etId;
    EditText etPw;
    EditText etPwCheck;

    Retrofit retrofit;
    UserApi userApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        setLayout();

        retrofit = RetrofitClient.getClient();
        userApi = retrofit.create(UserApi.class);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"click!");

                if(!etPw.getText().toString().equals(etPwCheck.getText().toString())) {
                    Toast.makeText(JoinActivity.this, "비밀번호를 다시 확인해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserEntity userEntity = new UserEntity();
                userEntity.setId(etId.getText().toString());
                userEntity.setPw(etPw.getText().toString());
                Log.d(TAG, "id: " + userEntity.getId() + " pw: " + userEntity.getPw());

                userApi.join(userEntity).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        LoginResponse result = response.body();
                        if(result!=null && result.getResultCode()==200) {
                            Toast.makeText(JoinActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d(TAG, "통신 에러!");
                    }
                });
            }
        });
    }

    public void setLayout() {
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        etId = (EditText) findViewById(R.id.etId);
        etPw = (EditText) findViewById(R.id.etPw);
        etPwCheck = (EditText) findViewById(R.id.etPwCheck);
    }
}
