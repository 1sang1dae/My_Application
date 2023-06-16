package com.example.myapplication;
import android.media.session.MediaSession;

import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface MyApiService {

    @FormUrlEncoded
    @POST("/signup")
    Call<Void> sendUserInfo(@Field("name") String name,
                            @Field("email") String email,
                            @Field("password") String password);

    @FormUrlEncoded
    @POST("/token")
    Call<JsonObject> loginForAccessToken(@Field("username") String username, @Field("password") String password);


    @Multipart
    @POST("/upload_image/")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part image);

    @GET("/upload_lmdetail/{class_number}")
    Call<LMDetail> sendClassNumber(@Path("class_number") String classNumber);

    @GET("/upload_item/{keyword}")
    Call<ResponseBody> sendQuery(@Path("keyword") String query);

    @GET("/upload_festival/{f_date}")
    Call<ResponseBody> uploadData( @Path("f_date") String festivals);

    @GET("/add_wishlist/{class_number}")
    Call<ResponseBody> add_wishlist(@Path("class_number") String classNumber, @Header("Authorization") String token);

    @GET("/add_complete/{class_number}")
    Call<ResponseBody> add_complete(@Path("class_number") String classNumber, @Header("Authorization") String token);


    @GET("/myWishList")
    Call<ResponseBody> getMyWishListFromServer( @Header("Authorization") String token);

    @POST("/removeMyWish/{id}")
    Call<ResponseBody> removeMyWishFromServer(@Path("id") int myWishId);


}

