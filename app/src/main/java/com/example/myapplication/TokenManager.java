package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TokenManager {
    private static final String PREFERENCES_NAME = "myPreferences";
    private static final String TOKEN_KEY = "token";
    private SharedPreferences sharedPreferences;

    public TokenManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void saveToken(JsonObject token) {
        String tokenString = token.toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, tokenString);
        editor.apply();
    }


    public JsonObject getToken() {
        String tokenString = sharedPreferences.getString(TOKEN_KEY, null);
        if (tokenString != null) {
            return JsonParser.parseString(tokenString).getAsJsonObject();
        } else {
            return null;
        }
    }
}
