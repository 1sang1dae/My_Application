package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWishAdapter extends RecyclerView.Adapter<MyWishAdapter.MyWishViewHolder> {
    private List<MyWish> wishlist;
    private MyWishActivity activity;

    public MyWishAdapter(MyWishActivity activity) {
        this.activity = activity;
    }


    @NonNull
    @Override
    public MyWishAdapter.MyWishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mywish, parent, false);
        return new MyWishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyWishAdapter.MyWishViewHolder holder, int position) {
        MyWish mywish = wishlist.get(position);
        holder.bind(mywish);
    }

    @Override
    public int getItemCount() {
        return (wishlist != null) ? wishlist.size() : 0;
    }

    public class MyWishViewHolder extends RecyclerView.ViewHolder {
        private TextView mywish_Name;
        private Button removeBtn;

        public MyWishViewHolder(@NonNull View itemView) {
            super(itemView);
            mywish_Name = itemView.findViewById(R.id.mywish_name);
            removeBtn = itemView.findViewById(R.id.removeBtn);

            removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        removeMyWish(position);
                    }
                }
            });
        }

        public void bind(MyWish mywish) {
            mywish_Name.setText(mywish.getMywish_name());
        }
    }

    private void removeMyWish(int position) {
        MyWish myWish = wishlist.get(position);

        // 서버에 삭제 요청을 보내는 코드를 추가합니다.
        // MyApiService 인스턴스를 가져옵니다.
        MyApiService apiService = RetrofitInstance.getRetrofitInstance().create(MyApiService.class);

        // 서버에 삭제 요청을 보내는 API 호출
        Call<ResponseBody> call = apiService.removeMyWishFromServer(myWish.getMywish_id());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // 서버에서 삭제 요청을 정상적으로 처리한 경우에만 삭제 작업을 수행합니다.
                    wishlist.remove(position);
                    notifyItemRemoved(position);
                } else {
                    // 서버에서 삭제 요청을 실패한 경우에 대한 처리를 여기에 작성합니다.
                    Toast.makeText(activity, "서버로의 삭제 요청을 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // 서버와의 통신이 실패한 경우에 대한 처리를 여기에 작성합니다.
                Toast.makeText(activity, "서버와의 통신을 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void setMyWishList(List<MyWish> wishlist) {
        this.wishlist = wishlist;
        notifyDataSetChanged();
    }
}
