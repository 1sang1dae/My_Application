package com.example.myapplication;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item_Lm> items;
    private Context context;

    public ItemAdapter(Context context,List<Item_Lm> items) {
        this.items = items;
        this.context = context;
    }

    public void startDetailActivity(String classNumber) {
        Retrofit retrofit = RetrofitInstance.getRetrofitInstance();
        MyApiService service = retrofit.create(MyApiService.class);
        Call<LMDetail> call = service.sendClassNumber(classNumber);
        call.enqueue(new Callback<LMDetail>() {
            @Override
            public void onResponse(Call<LMDetail> call, Response<LMDetail> response) {
                if (response.isSuccessful()) {
                    LMDetail detail = response.body();
                    System.out.println(detail);

                    // 응답을 LmDetailActivity로 전달
                    Intent intent = new Intent(context, LmDetailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    intent.putExtra("_id", detail.getId());
                    intent.putExtra("name", detail.getName());
                    intent.putExtra("location", detail.getLocation());
                    intent.putExtra("homepage", detail.getHomepage());
                    intent.putExtra("lm_fee", detail.getLm_fee());
                    intent.putExtra("op_time", detail.getOp_time());
                    intent.putExtra("day_off", detail.getDay_off());
                    intent.putExtra("hashtag", detail.getHashtag());
                    intent.putExtra("description", detail.getDescription());
                    intent.putExtra("tip", detail.getTip());
                    intent.putExtra("image", detail.getImage());
                    context.startActivity(intent);
                } else {
                    System.out.println("Error: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<LMDetail> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_landmark_btnview, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        final Item_Lm item = items.get(position);
        holder.title.setText(item.getTitle());

//        Glide.with(holder.image.getContext())
//                .load(item.getImageUrl()) // 이미지 URL 로드
//                .into(holder.image); // ImageView에 적용

        holder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String classNumber = item.getLmID();
                startDetailActivity(classNumber);
            }
        });
    }

    // 아이템 리스트를 업데이트하는 메소드 추가
    public void updateItems(List<Item_Lm> newItems) {
        this.items = newItems;
        notifyDataSetChanged(); // 변경된 데이터에 맞춰 UI를 갱신
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        FrameLayout frameLayout;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.lmbtn_title);
            image = itemView.findViewById(R.id.lmbtn_image);
            frameLayout = itemView.findViewById(R.id.itemBtn);
        }
    }
}
