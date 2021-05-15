package com.example.techargo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ResetPassword extends AppCompatActivity {
    private EditText userEmail;
    private FirebaseAuth mAuth;
    private ProgressBar bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        userEmail = findViewById(R.id.email_field);
        Button sendmail = findViewById(R.id.send_email);
        bar = findViewById(R.id.bar_reset);
        mAuth = FirebaseAuth.getInstance();
        sendmail.setOnClickListener(v -> {
            bar.setVisibility(View.VISIBLE);
            sendmail.setEnabled(false);
            String email = userEmail.getText().toString();
            if(TextUtils.isEmpty(email)){
                Toast.makeText(ResetPassword.this,"Please enter valid email",Toast.LENGTH_SHORT).show();
                bar.setVisibility(View.INVISIBLE);
                sendmail.setEnabled(true);
            }
            else{
                if(isEmailValid(email)) {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetPassword.this, "Mail Successfully Sent!!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                        } else {
                            String message = Objects.requireNonNull(task.getException()).getMessage();
                            Toast.makeText(ResetPassword.this, "Error:" + message, Toast.LENGTH_SHORT).show();
                            bar.setVisibility(View.INVISIBLE);
                            sendmail.setEnabled(true);
                        }
                    });
                }else{
                    Toast.makeText(this, "Enter A Valid Email!!", Toast.LENGTH_SHORT).show();
                    bar.setVisibility(View.INVISIBLE);
                    sendmail.setEnabled(true);
                }
            }
        });
    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}