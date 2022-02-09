package com.example.runwith.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.runwith.R;
import com.example.runwith.background.StepCallback;
import com.example.runwith.background.StepService;
import com.example.runwith.domain.DataResponse;
import com.example.runwith.domain.MessageEntity;
import com.example.runwith.domain.User;
import com.example.runwith.retrofit.RetrofitClient;
import com.example.runwith.retrofit.TokenApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeActivity extends AppCompatActivity{
    private final String TAG = "Home";

    public static HomeActivity homeActivity;

    private StepService stepService;
    boolean isService = false;
    private Intent intent;

    TextView tvStep;
    Button btnFriend;
    Button btnRecord;
    Button btnMessage;
    Button btnMember;

    Retrofit retrofit;
    TokenApi tokenApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeActivity = this;

        checkPermission();
        setLayout();

        retrofit = RetrofitClient.getClient();
        tokenApi = retrofit.create(TokenApi.class);

        Intent serviceIntent = new Intent(HomeActivity.this, StepService.class);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startForegroundService(serviceIntent);
        else
            startService(serviceIntent);

        btnFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FriendActivity.class);
                startActivity(intent);
            }
        });

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, RecordActivity.class);
                startActivity(intent);
            }
        });

        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = User.read("id", "");
                startSendMessage(new MessageEntity(id, "오늘 미션 성공 실화냐? 대박이다"));
            }
        });

        btnMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MemberActivity.class);
                startActivity(intent);
            }
        });
        /*Intent fcm = new Intent(getApplicationContext(), MyFirebaseMessagingService.class);
        startService(fcm);*/
    }


    private void startSendMessage(MessageEntity msg) {
        tokenApi.sendMessage(msg).enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                DataResponse result = response.body();
                Log.d("메세지 전송 통신 성공", result.getMessage());
                if (result.getResultCode() == 200) {
                    Log.d("resultCode:200", "성고오오오옹");
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                Log.e("메세지 전송 통신 실패", t.getMessage());
                t.printStackTrace();
            }
        });
    }

    //레이아웃 세팅
    public void setLayout() {
        tvStep = (TextView) findViewById(R.id.tvStep);
        btnFriend = (Button) findViewById(R.id.btnFriend);
        btnRecord = (Button) findViewById(R.id.btnRecord);
        btnMessage = (Button) findViewById(R.id.btnMessage);
        btnMember = (Button) findViewById(R.id.btnMember);
    }

    //권한 확인
    public void checkPermission() {
        if(ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[] {Manifest.permission.ACTIVITY_RECOGNITION} ,0);
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "바인딩");
            StepService.MyBinder myBinder = (StepService.MyBinder) service;
            stepService = myBinder.getService();
            stepService.setCallback(stepCallback);
            isService = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isService = false;
        }
    };


    private StepCallback stepCallback = new StepCallback() {

        @Override
        public void onUnbindService() {
            isService = false;
            Log.d(TAG, "바인드 해제");
        }
    };
}
