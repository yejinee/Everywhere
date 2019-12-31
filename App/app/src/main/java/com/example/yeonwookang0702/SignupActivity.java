package com.example.yeonwookang0702;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    static  final String TAG="signup";
    TextView emailtxt;
    TextView pwtxt;
    TextView pwchecktxt;

    EditText id_signup;
    EditText pw_signup;
    EditText pwcheck;
    Button signupbtn;
    Button canclebtn;

    String email;
    String password;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();

        id_signup=findViewById(R.id.idEditTxt_signup);
        pw_signup=findViewById(R.id.pwEditTxt_signup);
        pwcheck=findViewById(R.id.pwCheckEditTxt_signup);
        signupbtn =findViewById(R.id.signupBtn_signup);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=id_signup.getText().toString();
                password=pw_signup.getText().toString();
                Log.d("signup", "btn click");
                if(password.equals(pwcheck.getText().toString())){
                    signup();
                    Log.d("signup", "signup");
                }
                else {
                    Log.d("signup", "reject");
                    Toast.makeText(getApplicationContext(), "비밀번호 불일치", Toast.LENGTH_SHORT).show();
                }
            }
        });
        canclebtn = findViewById(R.id.cancleBtn);
        canclebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void signup(){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            id_signup.setText("");
                            pw_signup.setText("");
                            pwcheck.setText("");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(),"signup fail", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

}
