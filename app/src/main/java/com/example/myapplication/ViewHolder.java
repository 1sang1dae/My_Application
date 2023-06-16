package com.example.myapplication;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView mNameTextView;
    TextView mTimeTextView;
    //ImageView mImageView;

    ViewHolder(View itemView) {
        super(itemView);
        mNameTextView = itemView.findViewById(R.id.festival_name);

        mTimeTextView =  itemView.findViewById(R.id.festival_time);
        //mImageView = itemView.findViewById(R.id.image_festival);
    }
}


