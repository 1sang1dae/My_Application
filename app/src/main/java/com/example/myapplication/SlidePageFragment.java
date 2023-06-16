package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SlidePageFragment extends Fragment {
    private static final String ARG_TEXT = "arg_text";
    private static final String ARG_IMAGE_RES_ID = "arg_image_res_id";

    public static SlidePageFragment newInstance(String text) {
        SlidePageFragment fragment = new SlidePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);
//        args.putInt(ARG_IMAGE_RES_ID, imageResId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slide_page, container, false);
        TextView textView = view.findViewById(R.id.result_name);
        ImageView imageView = view.findViewById(R.id.result_image);

        Bundle args = getArguments();
        if (args != null) {
            textView.setText(args.getString(ARG_TEXT));
            imageView.setImageResource(args.getInt(ARG_IMAGE_RES_ID));
        }

        return view;
    }
}
