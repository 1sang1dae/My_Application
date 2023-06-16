package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity {
    private EditText userName;
    private EditText userId;
    private EditText userPwd;
    private Button joinInBtn;
    private TextView welcome;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        constraintLayout = findViewById(R.id.content_layout);
        constraintLayout.setAlpha(0f); // 처음에는 완전 불투명하게 설정
        constraintLayout.animate()
                .alpha(1f) // 최종적으로 완전 투명하게 설정
                .setDuration(2000) // 이 애니메이션을 2초 동안 실행
                .setListener(null);
        userName = findViewById(R.id.signIn_username);
        userId = findViewById(R.id.signIn_userid);
        userPwd = findViewById(R.id.signIn_userpwd);
        joinInBtn = findViewById(R.id.enterBtn);
        welcome = findViewById(R.id.welcome);
        welcome.setAlpha(0f); // 처음에는 완전 불투명하게 설정
        welcome.animate()
                .alpha(1f) // 최종적으로 완전 투명하게 설정
                .setDuration(4000) // 이 애니메이션을 2초 동안 실행
                .setListener(null);

        Retrofit retrofit = RetrofitInstance.getRetrofitInstance();
        MyApiService service = retrofit.create(MyApiService.class);

        joinInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> call = service.sendUserInfo(
                        userName.getText().toString(),
                        userId.getText().toString(),
                        userPwd.getText().toString());

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if (response.isSuccessful()) {
                            System.out.println("연경성공");
                        } else {
                            System.out.println("연결 실패: ");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });



    }
}