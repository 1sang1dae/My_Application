package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class OperationFragment extends Fragment {

    private static final String ARG_CONTACT = "contact";
    private static final String ARG_HOMAPAGE = "";
    private static final String ARG_FEE = "lm_fee";
    private static final String ARG_OPTIME = "op_time";
    private static final String ARG_DAYOFF = "day_off";
    public static OperationFragment newInstance(String contact, String homepage, String lm_fee, String op_time, String day_off) {
        OperationFragment fragment = new OperationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CONTACT, contact);
        args.putString(ARG_HOMAPAGE, homepage);
        args.putString(ARG_FEE, lm_fee);
        args.putString(ARG_OPTIME, op_time);
        args.putString(ARG_DAYOFF, day_off);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_op, container, false);

        TextView ContactTextView = view.findViewById(R.id.contact_content);
        TextView HomepageTextView = view.findViewById(R.id.homepage_content);
        TextView LMFeeTextView = view.findViewById(R.id.lm_fee_content);
        TextView TimeTextView = view.findViewById(R.id.op_time_content);
        TextView DayOffTextView = view.findViewById(R.id.day_off_content);

        if (getArguments() != null) {
            String contact = getArguments().getString(ARG_CONTACT);
            String homepage = getArguments().getString(ARG_HOMAPAGE);
            String fee = getArguments().getString(ARG_FEE);
            String optime = getArguments().getString(ARG_OPTIME);
            String dayoff = getArguments().getString(ARG_DAYOFF);

            ContactTextView.setText(homepage);
            HomepageTextView.setText(contact);
            LMFeeTextView.setText(fee);
            TimeTextView .setText(optime);
            DayOffTextView .setText(dayoff);
        }


        return view;
    }
}
