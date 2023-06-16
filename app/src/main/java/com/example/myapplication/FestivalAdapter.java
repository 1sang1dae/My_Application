package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FestivalAdapter extends RecyclerView.Adapter<FestivalAdapter.ViewHolder> {
    private List<Festival> mFestivals;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public FestivalAdapter(List<Festival> festivals, OnItemClickListener listener) {
        mFestivals = festivals;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == R.layout.item_no_festivals) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_no_festivals, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_festival_view, parent, false);
        }

        return new ViewHolder(view, viewType);
    }




    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mFestivals.size() == 0) {
            // 데이터가 없을 경우에는 "No festivals found" 메시지를 표시하는 작업을 수행합니다.
            holder.mNoFestivalsTextView.setText("No festivals found");
        } else {
            // 기존의 축제 아이템을 표시하는 작업을 수행합니다.
            Festival festival = mFestivals.get(position);
            holder.mNameTextView.setText(festival.getFestival_name());
            holder.mTimeTextView.setText(festival.getFestival_start_time() + "~" + festival.getFestival_end_time());
        }
    }


    @Override
    public int getItemCount() {
        return mFestivals.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mFestivals.size() == 0) {
            // 데이터가 없을 경우에는 "No festivals found" 메시지를 표시하는 뷰 타입을 정의합니다.
            return R.layout.item_no_festivals;
        } else {
            return R.layout.item_festival_view;
        }
    }



    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mNameTextView;
        TextView mTimeTextView;
        TextView mNoFestivalsTextView;

        ViewHolder(View itemView, int viewType) {
            super(itemView);

            if (viewType == R.layout.item_no_festivals) {
                mNoFestivalsTextView = itemView.findViewById(R.id.no_festivals_text);
            } else {
                mNameTextView = itemView.findViewById(R.id.festival_name);
                mTimeTextView = itemView.findViewById(R.id.festival_time);
            }

            itemView.setOnClickListener(this);
        }




        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mListener.onItemClick(position);
            }
        }
    }

}
