package com.example.techargo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MyArea extends AppCompatActivity {
    private TextView txt_crop,txt_price;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebase;
    private String state,city,area,crop;
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
        firebase.collection("user").document(Objects.requireNonNull(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail())).get()
                .addOnCompleteListener(task -> {
                    if(!task.isSuccessful()){
                        Toast.makeText(MyArea.this, "Try Again Later!!", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        finish();
                    }
                    state = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).get("state")).toString();
                    city = Objects.requireNonNull(task.getResult().get("city")).toString();
                    area = Objects.requireNonNull(task.getResult().get("area")).toString();
                    crop = Objects.requireNonNull(task.getResult().get("crop")).toString();
                    txt_crop.setText(crop);
                    firebase.collection("state").document(state)
                            .collection("city").document(city)
                            .collection("area").document(area)
                            .collection("crop").document(crop)
                            .get().addOnCompleteListener(task1 -> {
                        if(task1.isSuccessful()) {
                            txt_price.setText(Objects.requireNonNull(Objects.requireNonNull(task1.getResult()).get("price")).toString()+" /quintal");
                        }else{
                            Toast.makeText(MyArea.this, "Try Later!!", Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                            finish();
                        }
                    });
                });
    }
}