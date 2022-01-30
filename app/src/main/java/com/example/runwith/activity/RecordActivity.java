package com.example.runwith.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runwith.R;
import com.example.runwith.etc.RecordRecyclerAdapter;
import com.example.runwith.domain.RecordEntity;
import com.example.runwith.domain.User;
import com.example.runwith.retrofit.RecordApi;
import com.example.runwith.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecordActivity extends AppCompatActivity {
    private final static String TAG = "Record";

    RecyclerView recordView;
    Button btnCalender;

    RecordApi recordApi;
    Retrofit retrofit;

    List<RecordEntity> list = new ArrayList<>();
    RecordRecyclerAdapter recordRecyclerAdapter;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        Log.d(TAG, "prepare");

        id = User.read("id", "");
        Log.d(TAG, id);

        setLayout();

        retrofit = RetrofitClient.getClient();
        recordApi = retrofit.create(RecordApi.class);

        recordApi.showRecord(id).enqueue(new Callback<List<RecordEntity>>() {
            @Override
            public void onResponse(Call<List<RecordEntity>> call, Response<List<RecordEntity>> response) {
                Log.d(TAG, "in response");
                list = response.body();
                Toast.makeText(RecordActivity.this, "ok", Toast.LENGTH_SHORT).show();
                ArrayList<RecordEntity> arr = new ArrayList<>();
                arr.addAll(list);

                recordRecyclerAdapter = new RecordRecyclerAdapter();
                recordRecyclerAdapter.setRecordList(arr);
                recordView.setAdapter(recordRecyclerAdapter);
                recordView.setLayoutManager(new LinearLayoutManager(RecordActivity.this));
            }

            @Override
            public void onFailure(Call<List<RecordEntity>> call, Throwable t) {
                Log.d(TAG, "통신 실패!");
            }
        });
    }

    public void setLayout() {
        recordView = (RecyclerView) findViewById(R.id.viewRecord);
    }
}
