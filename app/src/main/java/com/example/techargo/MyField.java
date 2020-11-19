package com.example.techargo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MyField extends AppCompatActivity {
    private FirebaseFirestore firebase;
    private TextView txt_crop,txt_attack;
    private FirebaseAuth mAuth;
    private String state,city,area;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfield);
        txt_attack = findViewById(R.id.txt_field_attack);
        txt_crop = findViewById(R.id.txt_field_crop);
        firebase = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        Button btn_add_crop_my_field = findViewById(R.id.btn_field_crop);
        Button btn_add_pest_attack_my_field = findViewById(R.id.btn_field_attack);
        btn_add_crop_my_field.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),AddCrop.class)));
        btn_add_pest_attack_my_field.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),PestAttack.class)));
        firebase.collection("user").document(Objects.requireNonNull(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail())).get().addOnCompleteListener(task -> {
            if(!task.isSuccessful()){
                Toast.makeText(MyField.this, "Try Later!!", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                finish();
            }
            txt_crop.setText(Objects.requireNonNull(Objects.requireNonNull(task.getResult()).get("crop")).toString());
            state = Objects.requireNonNull(task.getResult().get("state")).toString();
            city = Objects.requireNonNull(task.getResult().get("city")).toString();
            area = Objects.requireNonNull(task.getResult().get("area")).toString();
            firebase.collection("state").document(state)
                    .collection("city").document(city)
                    .collection("area").document(area)
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()) {
                        txt_attack.setText(Objects.requireNonNull(Objects.requireNonNull(task.getResult()).get("attack")).toString());
                    }else{
                        Toast.makeText(MyField.this, "Try Later!!", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        finish();
                    }
                }
            });
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainPage.class));
    }
}