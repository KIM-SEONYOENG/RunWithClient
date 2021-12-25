package com.example.runwith.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.runwith.R;

public class HomeActivity extends AppCompatActivity implements SensorEventListener{
    SensorManager sensorManager;
    Sensor stepCountSensor;

    TextView tvStep;
    Button btnFriend;

    int currentSteps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        checkPermission();
        setLayout();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if(stepCountSensor == null) {
            Toast.makeText(HomeActivity.this, "No Sensor!", Toast.LENGTH_SHORT).show();
        }

        btnFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FriendActivity.class);
                startActivity(intent);
            }
        });
    }

    //센서 설정
    public void onStart() {
        super.onStart();
        if (stepCountSensor != null) {
            sensorManager.registerListener((SensorEventListener) HomeActivity.this, stepCountSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
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

    //센서 변경 감지
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            if(event.values[0] == 1.0f) {
                currentSteps = 0;
                tvStep.setText(String.valueOf(currentSteps));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
