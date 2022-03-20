package com.example.runwith.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.runwith.R;
import com.example.runwith.domain.TeamEntity;
import com.example.runwith.retrofit.MessageApi;
import com.example.runwith.retrofit.RetrofitClient;
import com.example.runwith.retrofit.TeamApi;

import retrofit2.Retrofit;


public class InviteActivity extends AppCompatActivity {

    Button btnAccept;
    Button btnRefuse;
    Retrofit retrofit;
    TeamApi teamApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        setLayout();

        retrofit = RetrofitClient.getClient();
        teamApi = retrofit.create(TeamApi.class);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //teamApi.accept(new TeamEntity())
            }
        });

    }
    public void setLayout() {
        btnAccept = (Button) findViewById(R.id.btnAccept);
        btnRefuse = (Button) findViewById(R.id.btnRefuse);
    }
}
