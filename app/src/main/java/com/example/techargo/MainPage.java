package com.example.techargo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        //Handling the buttons
        Button btn_area = findViewById(R.id.btn_area);
        Button btn_field = findViewById(R.id.btn_field);
        Button btn_profile = findViewById(R.id.btn_profile);
        Button btn_sign_out = findViewById(R.id.btn_sign_out);
        mAuth = FirebaseAuth.getInstance();
        btn_profile.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),MyProfile.class)));
        btn_field.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),MyField.class)));
        btn_area.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MyArea.class)));
        btn_sign_out.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(getApplicationContext(),Login.class));
        });
    }
}