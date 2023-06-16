package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //      DetectionActivity로 이동하는 코드 작성 (Main 뿐만 아니라 하단바 있는 모든 Activity에 작성)
        //01. 축제 화면으로 넘어가기
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationview);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home) {
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                }
                else if (id == R.id.festival) {
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                }
                else if (id == R.id.camera) {
                    Intent intent = new Intent(MainActivity.this, DetectionActivity.class);
                    startActivity(intent);
                    return true;
                }
                else if (id == R.id.search) {
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(intent);
                    return true;
                }
                else if (id == R.id.mypage) {
                    Intent intent = new Intent(MainActivity.this, MyPageActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }

        });
    }
}
