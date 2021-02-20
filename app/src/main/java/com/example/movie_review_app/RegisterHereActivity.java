package com.example.movie_review_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;

import org.w3c.dom.Text;


public class RegisterHereActivity extends AppCompatActivity {
    private EditText usernameEt,phonenumberEt, emailEt,passwordEt1,passwordEt2;
   private Button RegisterButton;
   private TextView LogIn;
   private ProgressDialog ProgressDialog;
   private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerhere);
        firebaseAuth = FirebaseAuth.getInstance();
        usernameEt = findViewById(R.id.username);
        phonenumberEt = findViewById(R.id.Phonenumber);
        emailEt = findViewById(R.id.email);
        passwordEt1 = findViewById(R.id.password);
        passwordEt2 = findViewById(R.id.password2);
        RegisterButton = findViewById(R.id.register);
        ProgressDialog = new ProgressDialog(this);
        LogIn = findViewById(R.id.register);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterHereActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void Register() {
        String email = emailEt.getText().toString();
        String password = passwordEt1.getText().toString();
        String password2 = passwordEt2.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailEt.setError("Email Address");
            return;
        }
       else if (TextUtils.isEmpty(password)) {
            passwordEt1.setError("Enter your password");
            return;
        }
        else if (TextUtils.isEmpty(password2)) {
            passwordEt2.setError("Confirm your password");
            return;
        }
        else if (!password.equals(password2)) {
            passwordEt2.setError("Different password");
            return;
        }
        else if (password.length()<4) {
            passwordEt1.setError("Length should be >4");
            return;
        }
        else if (!isVallidEmail(email)) {
            emailEt.setError("Invalid email");
            return;
        }
        ProgressDialog.setMessage("Please wait...");
        ProgressDialog.show();
        ProgressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterHereActivity.this,"Successfully Registered",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(RegisterHereActivity.this,DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(RegisterHereActivity.this,"Registration failed",Toast.LENGTH_LONG).show();
                }
                ProgressDialog.dismiss();
            }
        });
    }
    private Boolean isVallidEmail(CharSequence target){
         return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
