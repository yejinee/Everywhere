package com.example.yeonwookang0702;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MyPageActivity extends AppCompatActivity {
    TextView tvemail;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        mAuth = FirebaseAuth.getInstance();
        tvemail.findViewById(R.id.mypage_email);
        tvemail.setText("이메일: "+ mAuth.getCurrentUser().getEmail());
    }

    public void btnlogoutclick(View view) {//로그아웃 버튼
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getApplicationContext(),"로그아웃",Toast.LENGTH_SHORT).show();
        //홈으로 보내야되나?
    }
}
