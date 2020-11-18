package com.example.techargo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MyProfile extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        Button btn_logout = findViewById(R.id.btn_out);
        mAuth = FirebaseAuth.getInstance();
        btn_logout.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(getApplicationContext(),Login.class));
        });
    }
}