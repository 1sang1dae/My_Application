package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LMDetail {
    @SerializedName("_id")
    private String id;
    private String name;
    private String location;
    private String homepage;
    private String lm_fee;
    private String op_time;
    private String day_off;
    private ArrayList<String> hashtag;
    private String description;
    private String tip;
    private ArrayList<String> image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getLm_fee() {
        return lm_fee;
    }

    public void setLm_fee(String lm_fee) {
        this.lm_fee = lm_fee;
    }

    public String getOp_time() {
        return op_time;
    }

    public void setOp_time(String op_time) {
        this.op_time = op_time;
    }

    public String getDay_off() {
        return day_off;
    }

    public void setDay_off(String day_off) {
        this.day_off = day_off;
    }

    public ArrayList<String> getHashtag() {
        return hashtag;
    }

    public void setHashtag(ArrayList<String> hashtag) {
        this.hashtag = hashtag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public ArrayList<String> getImage() {
        return image;
    }

    public void setImage(ArrayList<String> image) {
        this.image = image;
    }

}
