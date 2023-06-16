package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchActivity extends AppCompatActivity {

    private List<Item_Lm>  lmItems= new ArrayList<Item_Lm>();
    private List<Item_Lm>  fItems= new ArrayList<Item_Lm>();
    private ItemAdapter itemAdapter;
    private TabLayout tabLayout;
    private RecyclerView searchResult;
    private boolean isSearchPerformed = false; // 검색 수행 여부
    private TokenManager tokenManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        tabLayout = findViewById(R.id.searchResult_layout);

        searchResult = findViewById(R.id.search_result);
        tokenManager = new TokenManager(getApplicationContext());

        // 초기에 10개의 아이템을 보여줌 -> 나중에 좋아요가 많은 list대로 보여지도록
        lmItems.add(new Item_Lm("경복궁","src","1"));
        fItems.add(new Item_Lm("창덕궁","src","1"));

        // LMItemAdapter 인스턴스 생성
        itemAdapter = new ItemAdapter(getApplicationContext(),lmItems);

        // RecyclerView에 LayoutManager 설정
        searchResult.setLayoutManager(new LinearLayoutManager(this));

        // 어댑터를 RecyclerView에 연결
        searchResult.setAdapter(itemAdapter);
//        searchResult.setAdapter(fAdapter);

        TabLayout.Tab firstTab = tabLayout.newTab(); // 새 탭 생성
        firstTab.setText("관광지"); // 탭에 표시될 텍스트 설정
        tabLayout.addTab(firstTab); // 탭 추가

        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("축제");
        tabLayout.addTab(secondTab);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        // 첫 번째 탭에 해당하는 데이터로 RecyclerView를 업데이트
                        if (isSearchPerformed) { // 사용자가 검색을 수행한 경우에만 목록을 clear()
                            itemAdapter.updateItems(lmItems); // 관광지 아이템 리스트를 어댑터에 전달
                            break;
                            // ...
                        }
                        else {
                            itemAdapter.updateItems(lmItems);
                        }
//                        items.addAll(items); // dataForFirstTab는 첫 번째 탭에 해당하는 데이터

                        break;
                    case 1:
                        // 두 번째 탭에 해당하는 데이터로 RecyclerView를 업데이트
                        if (isSearchPerformed) { // 사용자가 검색을 수행한 경우에만 목록을 clear()

                            itemAdapter.updateItems(fItems);
                            break;
                        }
                        else {
                            itemAdapter.updateItems(fItems);
                        }
//                        items.addAll(items); // dataForFirstTab는 첫 번째 탭에 해당하는 데이터

                    // 필요한 만큼 case를 추가하면 됩니다.
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        // TODO: 검색 창의 리스너 설정. 사용자가 검색을 실행하면 아래 메소드를 호출
        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // 사용자가 검색어를 입력하고 검색을 실행하면 호출됨
                isSearchPerformed = true; // 검색이 수행되었음을 나타냄
                searchItems(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 검색어가 변경되었을 때 호출됨. 여기서는 처리하지 않음
                return false;
            }
        });
    }

    // 검색어를 받아서 서버에 검색 요청을 하고, 결과로 받은 list를 RecyclerView에 보여주는 메소드
    private void searchItems(String query) {
        // TODO: query를 이용해서 서버에 검색 요청을 하고, 결과를 받아옴
        Retrofit retrofit = RetrofitInstance.getRetrofitInstance();
        MyApiService service = retrofit.create(MyApiService.class);

        Call<ResponseBody> call = service.sendQuery(query); // 이 부분이 수정되었습니다.
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println("연경성공");
                try {
                    // 응답을 문자열로 변환
                    String responseBodyString = response.body().string();

                    // 문자열을 JSON 객체로 변환
                    JSONObject jsonObject = new JSONObject(responseBodyString);
                    JSONObject lm_item = jsonObject.getJSONObject("lm_item");
                    JSONObject f_item = jsonObject.getJSONObject("f_item");

                    lmItems.clear();
                    fItems.clear();

                    // JSONObject의 key들을 Iterator로 받아옵니다.
                    Iterator<String> keysLMIterator = lm_item.keys();
                    Iterator<String> keysFIterator = f_item.keys();

                    // keysIterator를 List로 변환합니다.
                    List<String> lm_keys = new ArrayList<>();
                    List<String> f_keys = new ArrayList<>();
                    while (keysLMIterator.hasNext()) {
                        lm_keys.add(keysLMIterator.next());
                    }
                    while (keysFIterator.hasNext()) {
                        f_keys.add(keysFIterator.next());
                    }

                    for(String key : lm_keys) {
                        String value = lm_item.getString(key);
                        lmItems.add(new Item_Lm(value,"/dd",key));
                    }
                    for(String key : f_keys) {
                        String value = lm_item.getString(key);
                        fItems.add(new Item_Lm(value,"/dd",key));
                    }

                    for(Item_Lm item : lmItems) {
                        System.out.println(item.getTitle());

                    }

                    itemAdapter.notifyDataSetChanged();

                }catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("연경실팹");
                t.printStackTrace();
            }


        });

    }
}