package com.example.techargo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MyArea extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myarea);
        Button btn_area_check = findViewById(R.id.btn_area_check);
        btn_area_check.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),AreaInfo.class)));
    }
}