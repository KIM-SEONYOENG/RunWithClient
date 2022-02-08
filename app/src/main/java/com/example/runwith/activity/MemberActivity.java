package com.example.runwith.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.runwith.R;
import com.example.runwith.domain.RecordEntity;
import com.example.runwith.domain.User;
import com.example.runwith.retrofit.RecordApi;
import com.example.runwith.retrofit.RetrofitClient;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MemberActivity extends AppCompatActivity {

    static final String TAG = "MemberActivity";

    TextView tvMemberStep;
    Button btnNew;

    Retrofit retrofit;
    RecordApi recordApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        setLayout();
        setMemberStep();

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMemberStep();
            }
        });

    }

    private void setLayout() {
        tvMemberStep = (TextView) findViewById(R.id.tvMemberStep);
        btnNew = (Button) findViewById(R.id.btnNew);
    }

    private void setMemberStep() {
        retrofit = RetrofitClient.getClient();
        recordApi = retrofit.create(RecordApi.class);

        recordApi.findMemberRecord(User.read("id", "")).enqueue(new Callback<RecordEntity>() {
            @Override
            public void onResponse(Call<RecordEntity> call, Response<RecordEntity> response) {
                RecordEntity record = response.body();
                tvMemberStep.setText(Integer.toString(record.getCount()));
            }

            @Override
            public void onFailure(Call<RecordEntity> call, Throwable t) {
            }
        });
    }
}
