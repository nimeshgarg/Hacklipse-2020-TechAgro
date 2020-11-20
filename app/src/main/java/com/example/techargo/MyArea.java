package com.example.techargo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyArea extends AppCompatActivity {
    private TextView txt_crop,txt_price;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myarea);
        firebase = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        txt_crop = findViewById(R.id.txt_area_crop);
        txt_price = findViewById(R.id.txt_area_price);
        Button btn_area_check = findViewById(R.id.btn_area_check);
        btn_area_check.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),AreaInfo.class)));
    }
}