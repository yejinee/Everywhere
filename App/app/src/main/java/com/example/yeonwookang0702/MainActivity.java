package com.example.yeonwookang0702;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.yeonwookang0702.RecyclerViewAdapters.RecyclerViewListAdapter;
import com.example.yeonwookang0702.RecyclerViewItems.TownItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // 로그인 된 사용자 정보
    public String userId = "";
    // FLAG
    public final int SIGN_IN = 101; // 로그인 액티비티

    public static boolean isLogined = false; // 로그인 되었는지 체크 여부

    // 네비게이션 바 메뉴 설정
    private ImageButton mytripBtn;
    private ImageButton scrapBtn;
    private ImageButton mainBtn;
    private ImageButton reviewBtn;
    private ImageButton mypageBtn;

    // 검색 옵션 설정 스피너
    private List<String> spinnerList = new ArrayList<>(); // 스피너 목록 항목
    private Spinner optionSpinner;
    private ArrayAdapter<String> spinnerAdapter;

    // 라디오 버튼
    private RadioButton regionRadio;
    private RadioButton villageRadio;
    private String tagWord = "region";

    // 검색 창
    private EditText searchTxt;
    // 검색 버튼
    private Button searchBtn;

    // 리사이클러뷰
    private List<TownItem> recommendedList = new ArrayList<>(); // 취향 추천 마을 리스트
    private RecyclerView recommendedView;
    private RecyclerViewListAdapter recommendedListAdapter;

    private List<TownItem> bestreviewList = new ArrayList<>(); // 사용자 평점 베스트 마을 리스트
    private RecyclerView bestreviewView;
    private RecyclerViewListAdapter bestreviewListAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        // 네비게이션 바 버튼 생성 (함수 호출)
        setNavBtnListener();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 네비게이션 바 버튼 생성 (함수 호출)
        setNavBtnListener();

        // 스피너 생성
        optionSpinner = findViewById(R.id.option_spinner);
        // 스피너에 항목 추가
        spinnerList.add("전체"); spinnerList.add("체험여행"); spinnerList.add("자연여행"); spinnerList.add("전통여행"); spinnerList.add("웰빙여행");
        optionSpinner.setPrompt("검색 옵션을 선택해주세요.");
        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, spinnerList);
        optionSpinner.setAdapter(spinnerAdapter);

        // 라디오 버튼 생성
        regionRadio = findViewById(R.id.region_radio);
        villageRadio = findViewById(R.id.town_radio);

        regionRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagWord = "region";
            }
        });

        villageRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagWord = "village";
            }
        });

        // 검색 창
        searchTxt = findViewById(R.id.search_text);

        // 검색 버튼
        searchBtn = findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchWord = searchTxt.getText().toString(); //검색어

                Log.d("tagword",tagWord);
                String oper = optionSpinner.getSelectedItem().toString(); // 오퍼레이션 종류
                String operWord = "";

                if(oper.equals("전체")) {
                    operWord = "all";

                } else if(oper.equals("체험여행")) {
                    operWord = "exp";

                } else if(oper.equals("자연여행")) {
                    operWord = "nat";

                } else if(oper.equals("전통여행")) {
                    operWord = "tra";

                } else if(oper.equals("웰빙여행")) {
                    operWord = "wel";

                }

                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra("keyword",searchWord); // ex> 강원도, 거북이마을 (배열 0번)
                intent.putExtra("tag", tagWord); // ex> 지역명, 마을명 (배열 1번)
                intent.putExtra("operation", operWord); // ex> 상세 오퍼레이션, 체험, 전통문화 등 (배열 2번)
                startActivity(intent);
            }
        });

        // 추천 리사이클러뷰 생성
        recommendedView = findViewById(R.id.recommended_list);
        //recommendedView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.HORIZONTAL));
        recommendedListAdapter = new RecyclerViewListAdapter(recommendedList, getApplicationContext());
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recommendedView.setLayoutManager(horizontalLayoutManager);
        recommendedView.setAdapter(recommendedListAdapter);

        // 베스트 리사이클러뷰 생성
        bestreviewView = findViewById(R.id.best_list);
        //bestreviewView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayout.HORIZONTAL));
        bestreviewListAdapter = new RecyclerViewListAdapter(bestreviewList, getApplicationContext());
        LinearLayoutManager horizontalLayoutManager2 = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        bestreviewView.setLayoutManager(horizontalLayoutManager2); // 위에서 생성한 것 재할용
        bestreviewView.setAdapter(bestreviewListAdapter);

        // 샘플 데이터 가져오기
        getSampleListItem();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 로그인 액티비티에서 돌아온 결과
        if(requestCode == SIGN_IN) {
            if(resultCode == Activity.RESULT_OK) {// 로그인 성공이면
                isLogined = true;
                userId=""; // 사용자 계정 정보 가져오기
            }
        }
    }

    // 샘플 데이터 호출 메소드
    private void getSampleListItem() {
        TownItem item1 = new TownItem(R.drawable.townsampleimage,"샘플마을","경남 마산시");
        TownItem item2 = new TownItem(R.drawable.townsampleimage,"샘플마을2","경남 마산시");
        TownItem item3 = new TownItem(R.drawable.townsampleimage,"샘플마을3","경남 마산시");
        TownItem item4 = new TownItem(R.drawable.townsampleimage,"샘플마을4","경남 마산시");
        TownItem item5 = new TownItem(R.drawable.townsampleimage,"샘플마을5","경남 마산시");

        recommendedList.add(item1);
        recommendedList.add(item2);
        recommendedList.add(item3);
        recommendedList.add(item4);
        recommendedList.add(item5);

        bestreviewList.add(item1);
        bestreviewList.add(item2);
        bestreviewList.add(item3);
        bestreviewList.add(item4);
        bestreviewList.add(item5);

        recommendedListAdapter.notifyDataSetChanged();
        bestreviewListAdapter.notifyDataSetChanged();
    }

    // 네비게이션 바 생성 후 버튼에 리스너 추가하는 메소드
    private void setNavBtnListener() {
        mytripBtn = findViewById(R.id.mytrip_btn);
        scrapBtn = findViewById(R.id.scrap_btn);
        mainBtn = findViewById(R.id.main_btn);
        reviewBtn = findViewById(R.id.review_btn);
        mypageBtn = findViewById(R.id.mypage_btn);

        // 각 버튼 클릭 리스너 추가
        // 마이 트립 버튼
        mytripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 마이 트립 액티비티 시작 시키기 (로그인 되어있을 때만 가능)
                if(isLogined) {

                }
            }
        });

        // 스크랩 버튼
        scrapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 스크랩 액티비티 시작 시키기 (로그인 되어있을 때만 가능)
                if(isLogined) {
                    Intent intent = new Intent(getApplicationContext(), MyScrapActivity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);

                }
            }
        });

        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 메인 액티비티 맨 위로 가져오기

            }
        });

        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 리뷰 액티비티 시작 시키기 (로그인 되어있을 때만 가능)
                if(isLogined) {

                }
            }
        });

        mypageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 마이페이지 액티비티 시작 시키기 (로그인 되어있을 때만 가능)
                if(isLogined) {
                    Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);

                } else {
                    // 로그인 되어있지 않으면 로그인 페이지로 이동
                    Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
                    startActivityForResult(intent, SIGN_IN);
                }

            }
        });
    }
}
