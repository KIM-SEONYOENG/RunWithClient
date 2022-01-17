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

import com.example.runwith.FCM.MyFirebaseMessagingService;
import com.example.runwith.R;
import com.example.runwith.background.StepCallback;
import com.example.runwith.background.StepService;

public class HomeActivity extends AppCompatActivity{
    private final String TAG = "Home";

    public static HomeActivity homeActivity;

    private StepService stepService;
    boolean isService = false;
    private Intent intent;

    TextView tvStep;
    Button btnFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeActivity = this;

        checkPermission();
        setLayout();

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

        /*Intent fcm = new Intent(getApplicationContext(), MyFirebaseMessagingService.class);
        startService(fcm);*/


    }


    //레이아웃 세팅
    public void setLayout() {
        tvStep = (TextView) findViewById(R.id.tvStep);
        btnFriend = (Button) findViewById(R.id.btnFriend);
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
