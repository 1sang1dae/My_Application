package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Item_Lm implements Parcelable {
    String lm_name;
    String img_src;
    String lm_id;

    public Item_Lm(String lm_name, String img_src, String lm_id ) {
        this.lm_name = lm_name;
        this.img_src = img_src;
        this.lm_id = lm_id;
    }

    protected Item_Lm(Parcel in) {
        lm_name = in.readString();
        img_src = in.readString();
    }

    public static final Creator<Item_Lm> CREATOR = new Creator<Item_Lm>() {
        @Override
        public Item_Lm createFromParcel(Parcel in) {
            return new Item_Lm(in);
        }

        @Override
        public Item_Lm[] newArray(int size) {
            return new Item_Lm[size];
        }
    };

    public String getTitle() {
        return lm_name;
    }

    public String getImageUrl() {
        return img_src;
    }
    public String getLmID() {
        return lm_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(lm_name);
        parcel.writeString(img_src);
        parcel.writeString(lm_id);
    }
}