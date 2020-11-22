package com.example.techargo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddCrop extends AppCompatActivity {
    private FirebaseFirestore firebase;
    private FirebaseAuth mAuth;
    private Spinner spn_crop;
    private List<String> crop_list;
    private ArrayAdapter<String> adapter_crop;
    private ProgressBar bar;
    private String crop,state,city,area;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcrop);
        Button btn_submit = findViewById(R.id.btn_crop_submit);
        spn_crop = findViewById(R.id.spn_crop_crop);
        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseFirestore.getInstance();
        bar = findViewById(R.id.bar_crop);
        firebase.collection("user").document(Objects.requireNonNull(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail())).get()
                .addOnCompleteListener(task -> {
                    if(!task.isSuccessful()){
                        Toast.makeText(AddCrop.this, "Login Again!!", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        startActivity(new Intent(getApplicationContext(),Login.class));
                    }
                    crop = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).get("crop")).toString();
                    state = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).get("state")).toString();
                    city = Objects.requireNonNull(task.getResult().get("city")).toString();
                    area = Objects.requireNonNull(task.getResult().get("area")).toString();
                    firebase.collection("state").document(state)
                            .collection("city").document(city)
                            .collection("area").document(area)
                            .collection("crop").get().addOnCompleteListener(task1 -> {
                                if(task1.isSuccessful()){
                                    crop_list = new ArrayList<>();
                                    crop_list.add("Select Crop");
                                    for (QueryDocumentSnapshot documentSnapshot : Objects.requireNonNull(task1.getResult())){
                                        crop_list.add(Objects.requireNonNull(documentSnapshot.get("name")).toString());
                                    }
                                    crop_list.add("None");
                                    adapter_crop = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, crop_list);
                                    adapter_crop.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    spn_crop.setAdapter(adapter_crop);
                                }else{
                                    Toast.makeText(AddCrop.this, "Login Again!!", Toast.LENGTH_SHORT).show();
                                    mAuth.signOut();
                                    startActivity(new Intent(getApplicationContext(),Login.class));
                                }
                            });

                });
        btn_submit.setOnClickListener(v -> {
            bar.setVisibility(View.VISIBLE);
            btn_submit.setEnabled(false);
            btn_submit.setVisibility(View.INVISIBLE);
            if(!spn_crop.getSelectedItem().equals("Select Crop")){
                if(crop.equals("None")){
                    firebase.collection("user").document(Objects.requireNonNull(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail()))
                            .update("crop",spn_crop.getSelectedItem().toString()).addOnCompleteListener(task1 -> {
                        if(task1.isSuccessful()){
                            Toast.makeText(AddCrop.this, "Crop Updated", Toast.LENGTH_SHORT).show();
                            firebase.collection("state").document(state)
                                    .collection("city").document(city)
                                    .collection("area").document(area)
                                    .collection("crop").document(spn_crop.getSelectedItem().toString()).get(Source.SERVER).addOnCompleteListener(task123 -> {
                                if (task123.isSuccessful()) {
                                    Long number = (Long) Objects.requireNonNull(task123.getResult()).get("farmers");
                                    number++;
                                    firebase.collection("state").document(state)
                                            .collection("city").document(city)
                                            .collection("area").document(area)
                                            .collection("crop").document(spn_crop.getSelectedItem().toString()).update("farmers", number).addOnCompleteListener(task1234 -> {
                                        if (task1234.isSuccessful()) {
                                            startActivity(new Intent(getApplicationContext(), MainPage.class));
                                        } else {
                                            Toast.makeText(AddCrop.this, "Some Error Occurred!!", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    });
                                } else {
                                    Toast.makeText(AddCrop.this, "Some Error Occurred!!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        }else{
                            Toast.makeText(AddCrop.this, "Some Error Occurred!!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }else {
                    firebase.collection("state").document(state)
                            .collection("city").document(city)
                            .collection("area").document(area)
                            .collection("crop").document(crop).get(Source.SERVER).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Long num = (Long) Objects.requireNonNull(task.getResult()).get("farmers");
                            num--;
                            firebase.collection("state").document(state)
                                    .collection("city").document(city)
                                    .collection("area").document(area)
                                    .collection("crop").document(crop).update("farmers", num).addOnCompleteListener(task12 -> {
                                if (task12.isSuccessful()) {
                                    firebase.collection("user").document(Objects.requireNonNull(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail()))
                                            .update("crop", spn_crop.getSelectedItem().toString()).addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Toast.makeText(AddCrop.this, "Crop Updated", Toast.LENGTH_SHORT).show();
                                            firebase.collection("state").document(state)
                                                    .collection("city").document(city)
                                                    .collection("area").document(area)
                                                    .collection("crop").document(spn_crop.getSelectedItem().toString()).get(Source.SERVER).addOnCompleteListener(task123 -> {
                                                if (task123.isSuccessful()) {
                                                    Long number = (Long) Objects.requireNonNull(task123.getResult()).get("farmers");
                                                    number++;
                                                    firebase.collection("state").document(state)
                                                            .collection("city").document(city)
                                                            .collection("area").document(area)
                                                            .collection("crop").document(spn_crop.getSelectedItem().toString()).update("farmers", number).addOnCompleteListener(task1234 -> {
                                                        if (task1234.isSuccessful()) {
                                                            startActivity(new Intent(getApplicationContext(), MainPage.class));
                                                        } else {
                                                            Toast.makeText(AddCrop.this, "Some Error Occurred!!", Toast.LENGTH_SHORT).show();
                                                            finish();
                                                        }
                                                    });
                                                } else {
                                                    Toast.makeText(AddCrop.this, "Some Error Occurred!!", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }
                                            });
                                        } else {
                                            Toast.makeText(AddCrop.this, "Some Error Occurred!!", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    });
                                } else {
                                    Toast.makeText(AddCrop.this, "Try Later!!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        } else {
                            Toast.makeText(AddCrop.this, "Try Later!!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
            }else{
                Toast.makeText(this, "Select Crop", Toast.LENGTH_SHORT).show();
                bar.setVisibility(View.INVISIBLE);
                btn_submit.setEnabled(true);
                btn_submit.setVisibility(View.VISIBLE);
            }
        });
    }
}