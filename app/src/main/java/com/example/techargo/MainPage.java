package com.example.techargo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainPage extends AppCompatActivity {
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
        btn_profile.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),MyProfile.class)));
        btn_field.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),MyField.class)));
        btn_area.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MyArea.class)));
    }
}