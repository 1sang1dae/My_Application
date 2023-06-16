package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Region;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LmDetailActivity extends AppCompatActivity {
    private ViewPager2 pager;
    private TabLayout tabLayout;
    private TextView lm_name;
    private TextView lm_location;
    Boolean c_isItemChecked ;
    Boolean w_isItemChecked;
    private TokenManager tokenManager;
    private String class_number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lmdetail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.detail_result);
        lm_name = findViewById(R.id.lm_name);
        lm_location = findViewById(R.id.lm_location);
        tokenManager = new TokenManager(getApplicationContext());


        Intent intent = getIntent();
        String id = intent.getStringExtra("_id");
        System.out.println(id);
        class_number = id;
        String name = intent.getStringExtra("name");
        String location = intent.getStringExtra("location");
        String contact = intent.getStringExtra("contact");
        String homepage = intent.getStringExtra("homepage");
        String lm_fee = intent.getStringExtra("lm_fee");
        String op_time = intent.getStringExtra("op_time");
        String day_off = intent.getStringExtra("day_off");
        ArrayList<String> hashtag = intent.getStringArrayListExtra("hashtag");
        String description = intent.getStringExtra("description");
        String tip = intent.getStringExtra("tip");
        ArrayList<String> image = intent.getStringArrayListExtra("image");

        // Adapter 생성 및 초기화. 필요에 따라 Fragment와 탭 제목을 추가합니다.
        AdapterTabPager adapter = new AdapterTabPager(this);
        adapter.addFragment(IntroFragment.newInstance(description, tip), "소개");
        adapter.addFragment(OperationFragment.newInstance(contact, homepage, lm_fee, op_time, day_off), "운영정보");
        adapter.addFragment(MapFragment.newInstance(location), "지도");
        adapter.addFragment(new SNSFragment(), "SNS");

        // ViewPager2에 Adapter 설정
        pager.setAdapter(adapter);

        // 슬라이드를 통한 페이지 전환 비활성화
        pager.setUserInputEnabled(false);

        // TabLayout과 ViewPager2 연결
        new TabLayoutMediator(tabLayout, pager,
                (tab, position) -> tab.setText(adapter.getTabTitle(position))
        ).attach();

        //landmark 세부정보 textview에 넣기
        lm_name.setText(name);
        lm_location.setText("주소 : " + location);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        MenuItem complete_menu = menu.findItem(R.id.complete);
        MenuItem wishlist_menu = menu.findItem(R.id.wishlist);

        complete_menu.setIcon(R.drawable.complete_uncheck);
        wishlist_menu.setIcon(R.drawable.wishlist_uncheck);



        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        c_isItemChecked = false;
        w_isItemChecked = false;
        switch (item.getItemId()) {
//            case R.id.user_menu:
//                // 서브메뉴는 자동으로 처리되므로, 여기에 별도의 코드를 작성할 필요는 없습니다.
//                return true;
            case R.id.complete:
                if(c_isItemChecked){
                    item.setIcon(R.drawable.complete_uncheck);
                    c_isItemChecked = false;
                } else {
                    item.setIcon(R.drawable.complete_check);
                    c_isItemChecked = true;
                    item_toServer(item, class_number);

                }
                return true;
            case R.id.wishlist:
                if(w_isItemChecked){
                    item.setIcon(R.drawable.wishlist_uncheck);
                    w_isItemChecked = false;

                } else {
                    item.setIcon(R.drawable.wishlist_check);
                    w_isItemChecked = true;
                    item_toServer(item, class_number);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void item_toServer(MenuItem item, String classNumber){
        Retrofit retrofit = RetrofitInstance.getRetrofitInstance();
        MyApiService service = retrofit.create(MyApiService.class);

        JsonObject tokenJson = tokenManager.getToken(); // 저장된 JsonObject 토큰 가져오기
        System.out.println(tokenJson);
        String token = tokenJson.get("access_token").getAsString(); // JsonObject에서 실제 토큰 값 추출
        String authHeader = "Bearer " + token; // "Bearer "를 붙인 헤더 값 생성

        System.out.println(token);

        if (item.getItemId() == R.id.complete){
            Call<ResponseBody> call = service.add_complete(classNumber, authHeader);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        System.out.println("complete 데이터 전송");
                    } else {
                        System.out.println("Error: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
        else if (item.getItemId() == R.id.wishlist){
            Call<ResponseBody> call = service.add_wishlist(classNumber, authHeader);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        System.out.println("wishlist 전송");
                    } else {
                        System.out.println("Error: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }else{
            System.out.println("No id");
        }


    }
}