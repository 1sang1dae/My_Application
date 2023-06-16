package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
public class LoginActivity extends AppCompatActivity {

    private EditText userid;
    private EditText userpwd;
    private Button createAccount;
    private TokenManager tokenManager;
    private TextView appName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appName= findViewById(R.id.appName);
        appName.setAlpha(0f); // 처음에는 완전 불투명하게 설정
        appName.animate()
                .alpha(1f) // 최종적으로 완전 투명하게 설정
                .setDuration(2000) // 이 애니메이션을 2초 동안 실행
                .setListener(null);
        userid = findViewById(R.id.userid);
        userpwd = findViewById(R.id.userpwd);

        tokenManager = new TokenManager(getApplicationContext());

        //회원가입 창으로 이동
        createAccount = findViewById(R.id.create_account);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });

        Button loginBtn = findViewById(R.id.SignInBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(LoginActivity.this);
                String input = userid.getText().toString();

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
                    userid.setError("유효한 이메일 주소를 입력해주세요");
                } else {
                    token_fromServer();
                }

            }
        });


    }
    private void token_fromServer(){
        Retrofit retrofit = RetrofitInstance.getRetrofitInstance();
        MyApiService service = retrofit.create(MyApiService.class);

        Call<JsonObject> call = service.loginForAccessToken(
                userid.getText().toString(),
                userpwd.getText().toString());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    // 서버로부터의 응답이 성공적인 경우

                    System.out.println("연결성공");
                    System.out.println(response);

                    // 토큰 받아오기
                    JsonObject token = response.body();
                    if (token != null) {
                        // 토큰을 TokenManager에 저장
                        tokenManager.saveToken(token);
                        System.out.println(token);

                        Intent mainScreen = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(mainScreen);

                    }
                } else {
                    // 서버로부터의 응답이 실패한 경우
                    System.out.println("연결실패");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // 네트워크 통신 자체가 실패한 경우
                t.printStackTrace();
            }
        });
    }
    public void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        // Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        // If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}

