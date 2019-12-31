package com.example.yeonwookang0702;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.yeonwookang0702.Classes.ImageTask;
import com.example.yeonwookang0702.Classes.Village;
import com.example.yeonwookang0702.Parser.ParserTask;
import com.example.yeonwookang0702.RecyclerViewAdapters.RecyclerViewListAdapter4;
import com.example.yeonwookang0702.RecyclerViewItems.SearchItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchActivity extends AppCompatActivity {

    private TextView keyWord;

    // 리사이클러 뷰
    private List<SearchItem> searchList = new ArrayList<>(); // 검색 결과 리스트
    private RecyclerView searchView;
    private RecyclerViewListAdapter4 searchListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // 이전 액티비티에서 값을 전달 받음
        Intent intent = getIntent();
        String keyword = intent.getStringExtra("keyword"); // 검색 키워드
        String tag = intent.getStringExtra("tag"); // 지역 or 마을
        String oper = intent.getStringExtra("operation"); // 체험, 자연, 전통문화, ...
        String[] array = new String[3];
        array[0] = keyword;
        array[1] = tag;
        array[2] = oper;

        keyWord = findViewById(R.id.search_word);

        keyWord.setText(keyword); // 검색 키워드

        // 검색 리사이클러뷰 생성
        searchView = findViewById(R.id.search_item_list);
        //activityView.addItemDecoration(new DividerItemDecoration(DetailActivity.this, LinearLayoutManager.HORIZONTAL));
        searchListAdapter = new RecyclerViewListAdapter4(searchList, getApplicationContext());
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false);
        searchView.setLayoutManager(verticalLayoutManager);
        searchView.setAdapter(searchListAdapter);

        // 샘플 데이터 가져오기
        //getSampleListItem();

        // 검색리스트
        if(oper.equals("all")){//전체
            array[2]="getExprnTour";
            getVillages(array);
            array[2]="getNatureTour";
            getVillages(array);
            array[2]="getTrditClturTour";
            getVillages(array);
            array[2]="getWellBeingTour";
            getVillages(array);
        }else if(oper.equals("exp")){//체험여행
            array[2]="getExprnTour";
            getVillages(array);
        }else if(oper.equals("nat")){//자연여행
            array[2]="getNatureTour";
            getVillages(array);
        }else if(oper.equals("tra")){//전통여행
            array[2]="getTrditClturTour";
            getVillages(array);
        }else if(oper.equals("wel")){//웰빙여행
            array[2]="getWellBeingTour";
            getVillages(array);
        }

    }

    private void getVillages(String[] array) {
        try {
            ArrayList<Village> parserarray= (ArrayList<Village>) new ParserTask().execute(array).get();

            for(int i = 0; i < parserarray.size(); i++) {
                Village village = parserarray.get(i);
                ImageTask imageTask = new ImageTask(); // 이미지 가져오기
                searchList.add(new SearchItem(village.getName(), village.getAddress1(),village.getFunction(), village.getTag(),village.getImage(), village.getIntro(), village.getId(), village.getManagerName(), village.getManagerEmail(), village.getHomepage()));
            }
            searchListAdapter.notifyDataSetChanged();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
