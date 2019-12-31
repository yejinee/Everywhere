package com.example.yeonwookang0702;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.yeonwookang0702.Classes.Activity;
import com.example.yeonwookang0702.Classes.ImageTask;
import com.example.yeonwookang0702.Parser.Parser2Task;
import com.example.yeonwookang0702.RecyclerViewAdapters.RecyclerViewListAdapter2;
import com.example.yeonwookang0702.RecyclerViewAdapters.RecyclerViewListAdapter3;
import com.example.yeonwookang0702.RecyclerViewItems.ActivityItem;
import com.example.yeonwookang0702.RecyclerViewItems.ReviewItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DetailActivity extends AppCompatActivity {
    // 네비게이션 바 메뉴 설정
    private ImageButton mytripBtn;
    private ImageButton scrapBtn;
    private ImageButton mainBtn;
    private ImageButton reviewBtn;
    private ImageButton mypageBtn;

    // 화면 요소들
    private ImageView thumbnail;
    private TextView nameTxt;
    private TextView introTxt;
    private TextView addressTxt;
    private TextView addressTxt2;
    private TextView managerTxt;

    private Button naverMapBtn;
    private Button bookingBtn;
    private Button emailBtn;
    private Button homepageBtn;

    // 스크랩 토글 버튼
    private ToggleButton scrapToggleBtn;

    // 리뷰 쓰기 버튼
    private ImageButton reviewWriteBtn;

    // 리사이클러뷰
    private List<ActivityItem> activityList = new ArrayList<>(); // 취향 추천 마을 리스트
    private RecyclerView activityView;
    private RecyclerViewListAdapter2 activityListAdapter;

    private List<ReviewItem> reviewList = new ArrayList<>(); // 리뷰 리스트
    private RecyclerView reviewView;
    private RecyclerViewListAdapter3 reviewListAdapter;

    private String villageId;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mAuth = FirebaseAuth.getInstance();
        // 이전 인텐트로부터 정보 가져오기
        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String intro = intent.getStringExtra("intro");
        String address = intent.getStringExtra("address");
        String manager = intent.getStringExtra("manager");
        String email = intent.getStringExtra("email");
        String homepage = intent.getStringExtra("homepage");
        villageId = intent.getStringExtra("id");
        String image = intent.getStringExtra("image");

        intro = removeHtml(intro);


        String[] array = new String[3];
        array[0] = villageId; // 마을 아이디
        array[1] = "village"; // 모드????
        array[2] = ""; // 오퍼레이션 (밑에서 각각 호출)

        // 썸네일 설정
        thumbnail = findViewById(R.id.tonwImage);
        ImageTask imageTask = new ImageTask();
        Bitmap bitmap = null;
        try {
            bitmap = (Bitmap) imageTask.execute(image).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        thumbnail.setImageBitmap(bitmap);

        // 마을 이름
        nameTxt = findViewById(R.id.townName);
        nameTxt.setText(name);

        // 소개글
        introTxt = findViewById(R.id.detailIntro);
        introTxt.setText(intro);

        // 주소
        addressTxt = findViewById(R.id.detailAddressMap); // 지도 위쪽 주소
        addressTxt2 = findViewById(R.id.detailAddress);
        addressTxt.setText(address);
        addressTxt2.setText(address);

        // 담당자
        managerTxt = findViewById(R.id.detailManager);
        managerTxt.setText(manager);

        // 이메일

        // 홈페이지

        // 스크랩 버튼
        scrapToggleBtn = findViewById(R.id.scrap_toggle_btn);
        scrapToggleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(scrapToggleBtn.isChecked()){
                    scrapToggleBtn.setBackgroundDrawable(
                            getResources().
                                    getDrawable(R.drawable.scrap_toggle_btn2)
                    );
                    writedb();
                }else{
                    scrapToggleBtn.setBackgroundDrawable(
                            getResources().
                                    getDrawable(R.drawable.scrap_toggle_btn1)
                    );
                } // end if

            }
        });


        // 리뷰 쓰기 버튼
        reviewWriteBtn = findViewById(R.id.review_write_btn);
        reviewWriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ReviewDialog inputDialog = new ReviewDialog(DetailActivity.this);
                inputDialog.show();
            }
        });

        // 활동 리사이클러뷰 생성
        activityView = findViewById(R.id.detail_activity_list);
        //activityView.addItemDecoration(new DividerItemDecoration(DetailActivity.this, LinearLayoutManager.HORIZONTAL));
        activityListAdapter = new RecyclerViewListAdapter2(activityList, getApplicationContext());
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(DetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
        activityView.setLayoutManager(horizontalLayoutManager);
        activityView.setAdapter(activityListAdapter);

        // 샘플 데이터 가져오기
        //getSampleListItem();

        array[2]="getFrcClvtExprn";
        getActivities(array);
        array[2]="getIdsrsExprn";
        getActivities(array);
        array[2]="getFdExprn";
        getActivities(array);
        array[2]="getTrditClturExprn";
        getActivities(array);
        array[2]="getNatureEclgyExprn";
        getActivities(array);
        array[2]="getHealthLeports";
        getActivities(array);
        array[2]="getFrhlLvlhExprn";
        getActivities(array);
        array[2]="getEtcExprn";
        getActivities(array);

        // 리뷰 리사이클러뷰 생성
        reviewView = findViewById(R.id.detail_review_list);
        reviewListAdapter = new RecyclerViewListAdapter3(reviewList, getApplicationContext());
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(DetailActivity.this, LinearLayoutManager.VERTICAL, false);
        reviewView.setLayoutManager(verticalLayoutManager);
        reviewView.setAdapter(reviewListAdapter);

        getSampleListItem2();
    }

    private void writedb() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        if(mAuth.getCurrentUser()!=null)
            myRef.child("users").child(mAuth.getUid()).child("scrap").push().setValue(villageId);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // 내비게이션바 세팅
        setNavBtnListener();
    }

    // 샘플 데이터 호출 메소드
    /*private void getSampleListItem() {
        ActivityItem item1 = new ActivityItem(R.drawable.activitysampleimage,"샘플활동1","운영시기: 1월~2월", "참가가능인원: 20명", "체험연령: 전연령층");
        ActivityItem item2 = new ActivityItem(R.drawable.activitysampleimage,"샘플활동2","운영시기: 4월~7월", "참가가능인원: 5명", "체험연령: 9세 이상");
        ActivityItem item3 = new ActivityItem(R.drawable.activitysampleimage,"샘플활동3","운영시기: 1월~12월", "참가가능인원: 10명", "체험연령: 7세 이상");

        activityList.add(item1);
        activityList.add(item2);
        activityList.add(item3);

        activityListAdapter.notifyDataSetChanged();
    }*/

    private void getActivities(String[] array) {
        try {
            ArrayList<Activity> parserarray= (ArrayList<Activity>) new Parser2Task().execute(array).get();

            for(int i = 0; i < parserarray.size(); i++) {
                Activity activity = parserarray.get(i);
                ImageTask imageTask = new ImageTask(); // 이미지 가져오기
                Log.d("마을ID1", villageId);
                Log.d("마을ID2", activity.getVillageId());
                activityList.add(new ActivityItem(activity.getName(), activity.getTag(), activity.getIntro(), activity.getMin(), activity.getMax(), activity.getPrice(), activity.getImage(), activity.getId(), activity.getVillageId(), activity.getCode()));
            }
            activityListAdapter.notifyDataSetChanged();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    // 샘플 데이터 호출 메소드2
    private void getSampleListItem2() {
        ReviewItem item1 = new ReviewItem("잘 쉬다와서 좋았습니다.", 4.5);
        ReviewItem item2 = new ReviewItem("주민분들이 친절해서 편하게 놀았습니다.", 3.5);
        ReviewItem item3 = new ReviewItem("아이들이 재밌어했어요.", 4.0);
        ReviewItem item4 = new ReviewItem("별로...", 1.5);
        ReviewItem item5 = new ReviewItem("가까워서 다녀왔는데 그냥 그랬어요.", 3.0);

        reviewList.add(item1);
        reviewList.add(item2);
        reviewList.add(item3);
        reviewList.add(item4);
        reviewList.add(item5);

        reviewListAdapter.notifyDataSetChanged();
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
                // 마이 트립 액티비티 시작 시키기
            }
        });

        // 스크랩 버튼
        scrapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 스트랩 액티비티 시작 시키기
            }
        });

        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 메인 액티비티 시작
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 현재 화면은 종료시킴 (스택 관리)
                startActivity(intent);
            }
        });

        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 리뷰 액티비티 시작 시키기
            }
        });

        mypageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 마이페이지 액티비티 시작 시키기
            }
        });
    }

    private String removeHtml(String html) {
        html = html.replaceAll("<(.*?)\\>","");
        html = html.replaceAll("<(.*?)\\\n","");
        html = html.replaceFirst("(.*?)\\>", " ");
        html = html.replaceAll("&nbsp;"," ");
        html = html.replaceAll("&amp;","&");
        return html;
    }
}