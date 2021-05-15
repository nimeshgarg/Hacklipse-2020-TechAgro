package com.example.techargo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class AddYield extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebase;
    private Button btn_submit;
    private ProgressBar bar;
    private TextView txt_crop;
    private EditText txt_num;
    private String email,state,city,area,crop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addyield);
        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseFirestore.getInstance();
        btn_submit=findViewById(R.id.btn_yield_submit);
        bar = findViewById(R.id.bar_yield);
        txt_crop = findViewById(R.id.txt_yield_crop);
        txt_num = findViewById(R.id.txt_yield_number);
        email = Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();
        firebase.collection("user").document(email).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                crop = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).get("crop")).toString();
                state = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).get("state")).toString();
                city = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).get("city")).toString();
                area = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).get("area")).toString();
                txt_crop.setText(crop);

            }else{
                Toast.makeText(AddYield.this, "Try Later!!", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                finish();
            }
        });
        btn_submit.setOnClickListener(v -> {
            bar.setVisibility(View.VISIBLE);
            btn_submit.setEnabled(false);
            if(crop.equals("None")){
                Toast.makeText(this, "No Crop Chosen!!", Toast.LENGTH_SHORT).show();
                bar.setVisibility(View.INVISIBLE);
                btn_submit.setEnabled(true);
            }else {
                firebase.collection("state").document(state)
                        .collection("city").document(city)
                        .collection("area").document(area)
                        .collection("crop").document(crop).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String value = txt_num.getText().toString();
                        int finalValue = Integer.parseInt(value);
                        Long num = (Long) Objects.requireNonNull(task.getResult()).get("yield");
                        Long number = (Long) Objects.requireNonNull(task.getResult()).get("farmers");
                        number--;
                        num = num + finalValue;
                        firebase.collection("state").document(state)
                                .collection("city").document(city)
                                .collection("area").document(area)
                                .collection("crop").document(crop).update("yield", num, "farmers", number).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                firebase.collection("user").document(email).update("crop", "None").addOnCompleteListener(task2 -> {
                                    if (task2.isSuccessful()) {
                                        Toast.makeText(AddYield.this, "Yield Added", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), MainPage.class));
                                    } else {
                                        Toast.makeText(AddYield.this, "Try Later!!", Toast.LENGTH_SHORT).show();
                                        mAuth.signOut();
                                        finish();
                                    }
                                });
                            } else {
                                Toast.makeText(AddYield.this, "Try Later!!", Toast.LENGTH_SHORT).show();
                                mAuth.signOut();
                                finish();
                            }
                        });
                    } else {
                        Toast.makeText(AddYield.this, "Try Later!!", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        finish();
                    }
                });
            }
        });
    }
}