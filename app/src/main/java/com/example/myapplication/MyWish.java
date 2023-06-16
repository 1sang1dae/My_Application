package com.example.myapplication;

public class MyWish {
    private int mywish_id;
    private String mywish_name;

    public MyWish(int mywish_id, String mywish_name) {
        this.mywish_id = mywish_id;
        this.mywish_name = mywish_name;
    }

    public MyWish() {}

    public int getMywish_id() {
        return this.mywish_id;
    }
    public void setMywish_id(int mywish_id) {
        this.mywish_id = mywish_id;
    }
    public String getMywish_name() {
        return this.mywish_name;
    }
    public void setMywish_name(String mywish_name) {
        this.mywish_name = mywish_name;
    }
}
