package com.example.runwith.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.runwith.R;
import com.example.runwith.domain.TeamEntity;
import com.example.runwith.domain.TeamResponse;
import com.example.runwith.domain.User;
import com.example.runwith.domain.UserEntity;
import com.example.runwith.retrofit.RetrofitClient;
import com.example.runwith.retrofit.TeamApi;
import com.example.runwith.retrofit.UserApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FriendActivity extends AppCompatActivity {
    private static final String TAG = "FriendActivity";

    EditText etKeyword;
    Button btnFindFriend;
    ListView candidateList;

    Retrofit retrofit;
    UserApi userApi;
    TeamApi teamApi;

    List<UserEntity> friendCandidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        setLayout();

        retrofit  = RetrofitClient.getClient();
        userApi = retrofit.create(UserApi.class);
        teamApi = retrofit.create(TeamApi.class);

        btnFindFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = etKeyword.getText().toString();
                if(keyword.equals(""))
                    Toast.makeText(FriendActivity.this, "검색어를 입력하세요", Toast.LENGTH_SHORT).show();
                else
                    getFriend(keyword);
            }
        });

        candidateList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(FriendActivity.this, friendCandidate.get(position).getId(), Toast.LENGTH_SHORT).show();
                String my_id = User.read("id", "");
                teamApi.invite(new TeamEntity(my_id, friendCandidate.get(position).getId())).enqueue(new Callback<TeamResponse>() {
                    @Override
                    public void onResponse(Call<TeamResponse> call, Response<TeamResponse> response) {
                        TeamResponse result = response.body();
                        if (result.getResultCode() == 200)
                            finish();
                    }

                    @Override
                    public void onFailure(Call<TeamResponse> call, Throwable t) {
                        Log.e("친구 초대 에러", t.getMessage());
                    }
                });


            }
        });
    }

    public void getFriend(String keyword) {
        userApi.findUser(keyword).enqueue(new Callback<List<UserEntity>>() {
            @Override
            public void onResponse(Call<List<UserEntity>> call, Response<List<UserEntity>> response) {
                friendCandidate = response.body();

                if(friendCandidate.size()==0)
                    Toast.makeText(FriendActivity.this, "해당하는 아이디가 없습니다.", Toast.LENGTH_SHORT).show();
                List<String> idList = new ArrayList<>();
                for(UserEntity candidate : friendCandidate)
                    idList.add(candidate.getId());
                ArrayAdapter<String> adapter = new ArrayAdapter<>(FriendActivity.this, android.R.layout.simple_list_item_1, idList);
                candidateList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<UserEntity>> call, Throwable t) {
                Log.d(TAG, "친구 리스트 통신 에러!");
                Log.d(TAG, t.getMessage());
            }
        });
    }

    public void setLayout() {
        etKeyword = (EditText) findViewById(R.id.etKeyword);
        btnFindFriend = (Button) findViewById(R.id.btnFindFriend);
        candidateList = (ListView) findViewById(R.id.candidateList);
    }
}
