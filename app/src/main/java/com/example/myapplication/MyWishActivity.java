package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyWishActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyWishAdapter adapter;
    private MyWishManager myWishManager;
    private Button changeBtn;
    private boolean isLMItem = true; // 초기 값은 true로 설정
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywish);

        changeBtn = findViewById(R.id.changeBtn);
        changeBtn.setOnClickListener(v -> {
            isLMItem = !isLMItem; // 버튼을 클릭할 때마다 값을 반전시킴

            // Activity 재갱신 (새로고침)
            finish();//인텐트 종료
            overridePendingTransition(0, 0);//인텐트 효과 없애기
            Intent intent = getIntent(); //인텐트
            startActivity(intent); //액티비티 열기
            overridePendingTransition(0, 0);//인텐트 효과 없애기

        });

        recyclerView = findViewById(R.id.wishlist_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyWishAdapter(this);
        recyclerView.setAdapter(adapter);
        myWishManager = new MyWishManager(); // MyWishManager 객체 생성
        tokenManager = new TokenManager(getApplicationContext());


        // ApiService 인스턴스를 가져옵니다.
        MyApiService apiService = RetrofitInstance.getRetrofitInstance().create(MyApiService.class);


        //token 불러오기
        JsonObject tokenJson = tokenManager.getToken(); // 저장된 JsonObject 토큰 가져오기
        System.out.println(tokenJson);
        String token = tokenJson.get("access_token").getAsString(); // JsonObject에서 실제 토큰 값 추출
        String authHeader = "Bearer " + token; // "Bearer "를 붙인 헤더 값 생성

        // My Wish 정보 요청 API 호출
        Call<ResponseBody> call = apiService.getMyWishListFromServer(authHeader);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                List<MyWish> myWishList = new ArrayList<>();
                if (response.isSuccessful()) {

                    try {
                        // 응답을 문자열로 변환
                        String responseBodyString = response.body().string();
                        System.out.println(responseBodyString);

                        // 문자열을 JSON 객체로 변환
                        JSONObject jsonObject = new JSONObject(responseBodyString);

                        // JsonObject에서 가져올 키 값을 설정
                        String itemKey = isLMItem ? "landmark" : "festival";
//                        JSONArray itemArray = jsonObject.getJSONArray(itemKey);
//                        for (int i = 0; i < itemArray.length(); i++) {
//                            JSONObject item = itemArray.getJSONObject(i);
//                            Iterator<String> keysIterator = item.keys();
//
//                            while (keysIterator.hasNext()) {
//                                String key = keysIterator.next();
//                                int intValue = Integer.parseInt(key);
//                                String stringValue = item.getString(key);
//                                MyWish myWish = new MyWish(intValue, stringValue);
//                                myWishManager.addMyWish(myWish); // MyWishManager에 MyWish 추가
//                            }
//                        }
                        JSONObject item = jsonObject.getJSONObject(itemKey);

                        Iterator<String> keysIterator = item.keys();
                        while (keysIterator.hasNext()) {
                            String key = keysIterator.next();
                            int intValue = Integer.parseInt(key);
                            String stringValue = jsonObject.getString(key);
                            MyWish myWish = new MyWish(intValue, stringValue);
                            myWishManager.addMyWish(myWish); // MyWishManager에 MyWish 추가
                        }

                        // MyWishManager에서 My Wish 정보를 가져와 어댑터에 설정합니다.
                        adapter.setMyWishList(myWishManager.getWishlist());
                        recyclerView.setAdapter(adapter);
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    // 응답이 실패한 경우 처리하는 코드를 작성합니다.
                    Toast.makeText(MyWishActivity.this, "서버로부터 My Wish 정보를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // 요청이 실패했을 때 처리하는 코드를 작성합니다.
                Toast.makeText(MyWishActivity.this, "서버에 연결할 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
