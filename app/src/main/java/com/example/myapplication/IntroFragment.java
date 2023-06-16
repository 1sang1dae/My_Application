package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class IntroFragment extends Fragment {
    private static final String ARG_DESCRIPTION = "description";
    private static final String ARG_TIP = "tip";

    public static IntroFragment newInstance(String description, String tip) {
        IntroFragment fragment = new IntroFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DESCRIPTION, description);
        args.putString(ARG_TIP, tip);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro, container, false);

        TextView descriptionTextView = view.findViewById(R.id.description_content); // 여기에 실제 TextView의 id를 적어주세요.
        TextView tipTextView = view.findViewById(R.id.tip_content); // 여기에 실제 TextView의 id를 적어주세요.

        if (getArguments() != null) {
            String description = getArguments().getString(ARG_DESCRIPTION);
            String tip = getArguments().getString(ARG_TIP);
            descriptionTextView.setText(description);
            tipTextView.setText(tip);
        }



        return view;
    }
}
