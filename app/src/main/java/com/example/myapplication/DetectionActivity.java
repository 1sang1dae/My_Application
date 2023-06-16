package com.example.myapplication;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;

import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class DetectionActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA_PERMISSION_CODE = 1;
    private ImageView imageView;
    private Button captureButton;
    private Button sendButton;
    private Button galleryButton;
    private static final String TAG = "DetectionActivity";
    private TokenManager tokenManager;


    private ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imageView.setImageBitmap(imageBitmap);
                }
            }
    );
    private ActivityResultLauncher<String> mGetGalleryContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    if (uri != null) {
                        imageView.setImageURI(uri);
                    }
                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detection);

        captureButton = (Button) findViewById(R.id.cameraBtn);
        imageView = (ImageView) findViewById(R.id.landmark_image);
        galleryButton = (Button) findViewById(R.id.galleryBtn);
        sendButton = (Button) findViewById(R.id.resultBtn);
        tokenManager = new TokenManager(getApplicationContext());
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getImageFromGallery();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (imageView.getDrawable() == null) {
                        throw new NullPointerException();
                    } else {
                        uploadImageToServer();
                    }
                } catch (NullPointerException e) {
                    Toast.makeText(getApplicationContext(), "이미지가 없습니다. 이미지를 추가해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void dispatchTakePictureIntent() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION_CODE);
            return;
        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            mGetContent.launch(takePictureIntent);
        }
    }

    //   갤러리에서 이미지 가져 오는 함수
    private void getImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
        GalleryLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> GalleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            System.out.println("gallery start");
            if(result.getResultCode() != RESULT_CANCELED){
                Intent intent = result.getData();
                Uri uri = intent.getData();
                Glide.with(DetectionActivity.this).load(uri).into(imageView);
                Log.d(TAG ,"available");
            }else{
                Log.d(TAG ,"no image");
            }
        }
    });

//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        Log.d("PERMISSION", "onRequestPermissionsResult called.");
//        if (requestCode == REQUEST_GALLERY_PERMISSION_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // 권한이 허용되면, 갤러리를 열고 이미지를 선택하도록 합니다.
//                mGetGalleryContent.launch("image/*");
//            } else {
//                // 권한이 거부되면, 사용자에게 이에 대한 메시지를 표시합니다.
//                Toast.makeText(this, "갤러리 접근 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    private void galleryTakePictureIntent(){
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
//                    REQUEST_GALLERY_PERMISSION_CODE);
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                    != PackageManager.PERMISSION_GRANTED) {
//                Log.d("PERMISSION", "Permission request not granted.");
//            } else {
//                Log.d("PERMISSION", "Permission request granted.");
//            }
//        }
//        else {
//            mGetGalleryContent.launch("image/*");
//        }
//    }

    private void uploadImageToServer() {
        // Retrofit 인스턴스 생성
        Retrofit retrofit = RetrofitInstance.getRetrofitInstance();
        MyApiService service = retrofit.create(MyApiService.class);

        // imageView에서 Bitmap을 가져오기
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        System.out.println(bitmap);

// 2. 이 이미지를 파일로 저장합니다.
        Context context = this;
        File filesDir = context.getFilesDir();
        File imageFile = new File(filesDir, "image.jpg");

        try (FileOutputStream out = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (IOException e) {
            e.printStackTrace();
        }

// 3. 저장된 파일을 multipart/form-data 형식으로 서버에 전송합니다.
        RequestBody reqFile = RequestBody.Companion.create(imageFile, MediaType.parse("image/*"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", imageFile.getName(), reqFile);

        Call<ResponseBody> call = service.uploadImage(body); // 이 부분이 수정되었습니다.
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println("연경성공");

                try {

                    // 응답을 문자열로 변환
                    String responseBodyString = response.body().string();

                    // 문자열을 JSON 객체로 변환
                    JSONObject jsonObject = new JSONObject(responseBodyString);

                    Object prediction = jsonObject.get("prediction");

                    Intent intent = new Intent(DetectionActivity.this, ImageClassificationResult.class);
                    intent.putExtra("prediction", prediction.toString());
                    startActivity(intent);



                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("연경실팹");
                t.printStackTrace();
            }
        });


    }
}