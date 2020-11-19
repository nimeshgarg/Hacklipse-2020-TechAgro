package com.example.techargo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MyField extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfield);
        Button btn_add_crop_my_field = findViewById(R.id.btn_add_crop_my_field);
        Button btn_add_pest_attack_my_field = findViewById(R.id.btn_add_pest_attack_my_field);
        btn_add_crop_my_field.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),AddCrop.class)));
        btn_add_pest_attack_my_field.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),PestAttack.class)));
    }
}