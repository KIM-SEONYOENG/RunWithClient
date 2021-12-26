package com.example.runwith.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.runwith.R;
import com.example.runwith.domain.LoginResponse;
import com.example.runwith.retrofit.RetrofitClient;
import com.example.runwith.retrofit.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private final String SHARE_NAME = "userData";

    private EditText etId;
    private EditText etPw;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setLayout();

        sharedPreferences = getSharedPreferences(SHARE_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if(check())
            toNextPage();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //사용자가 입력한 id 와 pw 가져오기
                String id = etId.getText().toString();
                String pw = etPw.getText().toString();

                loginStart(id, pw);
            }
        });

    }

    public boolean check() {
        String id = sharedPreferences.getString("id", null);
        String pw = sharedPreferences.getString("pw", null);
        if(id==null || pw == null)
            return false;

        return true;
    }

    public void save(String id, String pw) {
        editor.putString("id", id);
        editor.putString("pw", pw);
        editor.apply();
    }

    public void toNextPage() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }


    public void loginStart(String id, String pw) {
        Retrofit retrofit = RetrofitClient.getClient();
        UserApi userApi = retrofit.create(UserApi.class);
        userApi.login(id, pw).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if(loginResponse.getResultCode() == 200) {
                    save(id, pw);
                    toNextPage();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d(TAG, "통신 실패!");
            }
        });
    }

    //화면의 객체 연결
    private void setLayout() {
        etId = (EditText) findViewById(R.id.etId);
        etPw = (EditText) findViewById(R.id.etPw);
        btnLogin = (Button) findViewById(R.id.btnLogin);
    }

}
