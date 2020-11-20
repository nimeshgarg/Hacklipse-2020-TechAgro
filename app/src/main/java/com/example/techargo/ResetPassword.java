package com.example.techargo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {
    private EditText userEmail;
    private Button sendemail;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        userEmail = findViewById(R.id.email_field);
        sendemail = findViewById(R.id.send_email);
        mAuth = FirebaseAuth.getInstance();
        sendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(ResetPassword.this,"Please enter valid email",Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ResetPassword.this,"Please visit your inbox to reset the password",Toast.LENGTH_SHORT).show();
                                /*public void login (View v){
                                    startActivity(new Intent(getApplicationContext(),Login.class));
                                }*/
                            }
                            else{
                                String message = task.getException().getMessage();
                                Toast.makeText(ResetPassword.this,"Error:"+message,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}