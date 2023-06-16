package com.example.myapplication;

public class Festival{
    private String festival_category;
    private String festival_date_end;

    private String festival_date_start;

    private String festival_end_time;

    private String festival_fee;

    private String festival_homepage;
    private int festival_id;

    private String festival_image;

    private String festival_info;
    private String festival_like;

    private String festival_location;

    private String festival_name;

    private String festival_start_time;


    public Festival(String festival_name, int festival_id, String festival_date_start, String festival_date_end,
                    String festival_location, String festival_start_time, String festival_end_time,
                    String festival_homepage, String festival_fee, String festival_info,
                    String festival_image, String festival_like, String festival_category) {
        // Firebase에서 객체를 생성할 때 필요합니다.
        this.festival_name = festival_name;
        this.festival_id = festival_id;

        this.festival_date_start = festival_date_start;
        this.festival_date_end = festival_date_end;

        this.festival_location = festival_location;

        this.festival_start_time = festival_start_time;
        this.festival_end_time = festival_end_time;

        this.festival_homepage = festival_homepage;
        this.festival_fee = festival_fee;
        this.festival_info = festival_info;

        this.festival_image = festival_image;
        this.festival_like = festival_like;
        this.festival_category = festival_category;
    }



    public Festival() {
        // 기본 생성자
    }

    public int getFestival_id() {
        return festival_id;
    }

    public void setFestival_id(int festival_id) {
        this.festival_id = festival_id;
    }

    public String getFestival_date_start() {
        return festival_date_start;
    }

    public void setFestival_date_start(String festival_date_start) {
        this.festival_date_start = festival_date_start;
    }

    public String getFestival_date_end() {
        return festival_date_end;
    }

    public void setFestival_date_end(String festival_date_end) {
        this.festival_date_end = festival_date_end;
    }

    public String getFestival_name() {
        return festival_name;
    }

    public void setFestival_name(String festival_name) {
        this.festival_name = festival_name;
    }

    public String getFestival_location() {
        return festival_location;
    }

    public void setFestival_location(String festival_location) {
        this.festival_location = festival_location;
    }

    public String getFestival_start_time() {
        return festival_start_time;
    }

    public void setFestival_start_time(String festival_start_time) {
        this.festival_start_time = festival_start_time;
    }

    public String getFestival_end_time() {
        return festival_end_time;
    }

    public void setFestival_end_time(String festival_end_time) {
        this.festival_end_time = festival_end_time;
    }

    public String getFestival_homepage() {
        return festival_homepage;
    }

    public void setFestival_homepage(String festival_homepage) {
        this.festival_homepage = festival_homepage;
    }

    public String getFestival_fee() {
        return festival_fee;
    }

    public void setFestival_fee(String festival_fee) {
        this.festival_fee = festival_fee;
    }

    public String getFestival_info() {
        return festival_info;
    }

    public void setFestival_info(String festival_info) {
        this.festival_info = festival_info;
    }

    public String getFestival_image() {
        return festival_image;
    }

    public void setFestival_image(String festival_image) {
        this.festival_image = festival_image;
    }

    public String getFestival_like() {
        return festival_like;
    }

    public void setFestival_like(String festival_like) {
        this.festival_like = festival_like;
    }

    public String getFestival_category() {
        return festival_category;
    }

    public void setFestival_category(String festival_category) {
        this.festival_category = festival_category;
    }
}


