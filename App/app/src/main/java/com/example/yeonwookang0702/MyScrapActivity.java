package com.example.yeonwookang0702;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.yeonwookang0702.RecyclerViewAdapters.RecyclerViewListAdapter4;
import com.example.yeonwookang0702.RecyclerViewItems.SearchItem;

import java.util.ArrayList;
import java.util.List;

public class MyScrapActivity extends AppCompatActivity {
    // 리사이클러 뷰
    private List<SearchItem> scrapList = new ArrayList<>(); // 스크랩 리스트
    private RecyclerView scrapView;
    private RecyclerViewListAdapter4 scrapListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_scrap);

        // 스크랩 리사이클러뷰 생성
        scrapView = findViewById(R.id.scrap_item_list);
        //activityView.addItemDecoration(new DividerItemDecoration(DetailActivity.this, LinearLayoutManager.HORIZONTAL));
        scrapListAdapter = new RecyclerViewListAdapter4(scrapList, getApplicationContext());
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(MyScrapActivity.this, LinearLayoutManager.VERTICAL, false);
        scrapView.setLayoutManager(verticalLayoutManager);
        scrapView.setAdapter(scrapListAdapter);

        // 샘플 데이터 가져오기
        //getSampleListItem();
    }
    // 샘플 데이터 호출 메소드
    private void getSampleListItem() {
        //SearchItem item1 = new SearchItem(R.drawable.townsampleimage,"샘플마을1","강원도 샘플리", "#체험 #워크샵", "체험마을");
        //SearchItem item2 = new SearchItem(R.drawable.townsampleimage,"샘플마을2","강원도 샘플리", "#먹거리 #전통문화", "전통문화마을");

        //scrapList.add(item1);
        //scrapList.add(item2);

        //scrapListAdapter.notifyDataSetChanged();
    }
}
