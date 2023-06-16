package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ImageClassificationResult extends AppCompatActivity {
    final ArrayList<String> class_numbers = new ArrayList<>();
    final ArrayList<String> class_names = new ArrayList<>();
    private TokenManager tokenManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_classification_result);

        ViewPager2 viewPager2 = findViewById(R.id.viewpager2);
        Button show_detailBtn = (Button) findViewById(R.id.result_confirm);
        tokenManager = new TokenManager(getApplicationContext());

        // 서버에서 결과 값 받기 - Top5 class
        String prediction = getIntent().getStringExtra("prediction");
        System.out.println(prediction);

        try {
            // prediction을 JsonArray형태로 저장
            JSONArray jsonArray = new JSONArray(prediction);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Iterator<String> keys = jsonObject.keys();
                while(keys.hasNext()){
                    String key = keys.next();
                    Object valueObject = jsonObject.get(key);
                    if (valueObject instanceof JSONObject) {
                        JSONObject valueJSONObject = (JSONObject) valueObject;
                        if(valueJSONObject.has("class_name")) {
                            String class_name = valueJSONObject.getString("class_name");
                            System.out.println(class_name);
                            class_names.add(class_name);
                        }
                    }
                    class_numbers.add(key);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(class_names);
        SlidePagerAdapter adapter = new SlidePagerAdapter(this, class_names );
        viewPager2.setAdapter(adapter);


        show_detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LmDetailActivity.class);
                int currentItem = viewPager2.getCurrentItem();
                String currentClassNumber = class_numbers.get(currentItem);

                System.out.println(currentClassNumber);

                startDetailActivity(getApplicationContext(),currentClassNumber);

            }
        });


    }
    public void startDetailActivity(Context context, String classNumber) {
        Retrofit retrofit = RetrofitInstance.getRetrofitInstance();
        MyApiService service = retrofit.create(MyApiService.class);
        Call<LMDetail> call = service.sendClassNumber(classNumber);
        call.enqueue(new Callback<LMDetail>() {
            @Override
            public void onResponse(Call<LMDetail> call, Response<LMDetail> response) {
                if (response.isSuccessful()) {
                    LMDetail detail = response.body();
                    System.out.println(detail.getId());
                    // 응답을 LmDetailActivity로 전달
                    Intent intent = new Intent(ImageClassificationResult.this, LmDetailActivity.class);

                    intent.putExtra("_id", detail.getId());

                    intent.putExtra("name", detail.getName());
                    intent.putExtra("location", detail.getLocation());
                    intent.putExtra("homepage", detail.getHomepage());
                    intent.putExtra("lm_fee", detail.getLm_fee());
                    intent.putExtra("op_time", detail.getOp_time());
                    intent.putExtra("day_off", detail.getDay_off());
                    intent.putExtra("hashtag", detail.getHashtag());
                    intent.putExtra("description", detail.getDescription());
                    intent.putExtra("tip", detail.getTip());
                    intent.putExtra("image", detail.getImage());
                    startActivity(intent);
                } else {
                    System.out.println("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<LMDetail> call, Throwable t) {
                // 네트워크 오류 등, 여기에서 오류를 처리합니다.
            }
        });
    }
}