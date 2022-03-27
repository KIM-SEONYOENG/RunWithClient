package com.example.runwith.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.runwith.R;
import com.example.runwith.domain.DataResponse;
import com.example.runwith.domain.User;
import com.example.runwith.retrofit.RetrofitClient;
import com.example.runwith.retrofit.UserApi;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private EditText etId;
    private EditText etPw;
    private Button btnLogin;
    private Button btnJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setLayout();
        User.init(getApplicationContext());

        if(check())
            toNextPage();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //사용자가 입력한 id 와 pw 가져오기
                String id = etId.getText().toString();
                //String pw = etPw.getText().toString();

                loginStart(id);
            }
        });

        /*btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
                Log.d("hhhhhhhhhhhhhhhhh", "111111111");
            }
        });*/

    }

    public boolean check() {
        if(User.read("id", null) == null)
            return false;
        return true;
    }

    public void toNextPage() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }


    public void loginStart(String id) {
        Retrofit retrofit = RetrofitClient.getClient();
        UserApi userApi = retrofit.create(UserApi.class);
        userApi.idcheck(id).enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                DataResponse dataResponse = response.body();
                if(dataResponse.getResultCode() == 200) {
                    User.write("id", id);
                    String token = FirebaseMessaging.getInstance().getToken().getResult();
                    userApi.login(id, token).enqueue(new Callback<DataResponse>() {
                        @Override
                        public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                            DataResponse dataResponse0 = response.body();
                            if(dataResponse0.getResultCode() == 200) {
                                User.write("id", id);
                                //User.write("pw", pw);
                                //toNextPage();
                            }
                            else if(dataResponse0.getResultCode()>=300)
                                Toast.makeText(LoginActivity.this, dataResponse0.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<DataResponse> call, Throwable t) {
                            Log.d("error msg", t.getMessage());
                            Log.d(TAG, t.getMessage());
                            Log.d(TAG, "통신 실패!");
                        }
                    });
                    toNextPage();
                }
                else if(dataResponse.getResultCode()>=300)
                    Toast.makeText(LoginActivity.this, dataResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                Log.d("error msg", t.getMessage());
                Log.d(TAG, t.getMessage());
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
