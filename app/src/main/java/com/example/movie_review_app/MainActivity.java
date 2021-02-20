package com.example.movie_review_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText  emailEt,passwordEt;
    private Button LogIn;
    private TextView RegisterHere;
    private ProgressDialog ProgressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        emailEt = findViewById(R.id.email);
        passwordEt = findViewById(R.id.password);
        LogIn = findViewById(R.id.Login);
        ProgressDialog = new ProgressDialog(this);
        RegisterHere = findViewById(R.id.registerhere);
        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        RegisterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RegisterHereActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void Login(){
        String email = emailEt.getText().toString();
        String password = passwordEt.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailEt.setError("Email Address");
            return;
        }
        else if (TextUtils.isEmpty(password)) {
            passwordEt.setError("Enter your password");
            return;
        }
        ProgressDialog.setMessage("Please wait...");
        ProgressDialog.show();
        ProgressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Successfully Registered",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(MainActivity.this,DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(MainActivity.this,"LogIn failed",Toast.LENGTH_LONG).show();
                }
                ProgressDialog.dismiss();
            }
        });
    }

}