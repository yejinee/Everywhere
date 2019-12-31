package com.example.yeonwookang0702;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SigninActivity extends AppCompatActivity {
    private static final String TAG="signin";
    // 로그인 테스트를 위한 샘플 계정
    public final String sampleId = "tester";
    public final String samplePw = "1234";

    private String userId;
    private String userPw;

    private EditText idEditText;
    private EditText pwEditText;
    private Button signinBtn;
    private Button signupBtn;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mAuth = FirebaseAuth.getInstance();//파베 인증

        idEditText = findViewById(R.id.idEditTxt);
        pwEditText = findViewById(R.id.pwEditTxt);

        signinBtn = findViewById(R.id.signinBtn);
        signupBtn = findViewById(R.id.signupBtn);

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userId = idEditText.getText().toString();
                userPw = pwEditText.getText().toString();

                signin(userId,userPw);

                if(mAuth.getCurrentUser()!=null){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }
//                if(!userId.equals("")) {
//
//                    if(!userPw.equals("")) {
//
//                        if(userId.equals(sampleId)) {
//
//                            if(userPw.equals(samplePw)) {
//
//                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                setResult(Activity.RESULT_OK,intent);
//                                finish();
//
//                            } else {
//                                Toast.makeText(getApplicationContext(), "패스워드가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
//                            }
//
//                        } else {
//                            Toast.makeText(getApplicationContext(), "가입된 정보가 없는 이메일입니다.", Toast.LENGTH_LONG).show();
//                            //Log.d("UserId: ", userId);
//                            //Log.d("SampleId: ",sampleId);
//                        }
//
//                    } else {
//                        Toast.makeText(getApplicationContext(), "패스워드를 입력해주세요.", Toast.LENGTH_LONG).show();
//                    }
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "이메일 입력해주세요.", Toast.LENGTH_LONG).show();
//                }
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

    }

    private void signin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            Toast.makeText(getApplicationContext(),"로그인 실패", Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }

                        // ...
                    }
                });
    }
}
