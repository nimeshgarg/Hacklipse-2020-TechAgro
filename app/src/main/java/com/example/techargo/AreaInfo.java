package com.example.techargo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AreaInfo extends AppCompatActivity {
    private FirebaseFirestore firebase;
    private FirebaseAuth mAuth;
    private String state,city,area;
    private List<String> crop_list;
    private ArrayAdapter<String> adapter_crop;
    private Spinner spn_crop;
    private TextView txt_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_info);
        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseFirestore.getInstance();
        txt_num = findViewById(R.id.txt_info_num);
        Button btn_check = findViewById(R.id.btn_info_check);
        spn_crop = findViewById(R.id.spn_info_crop);
        firebase.collection("user").document(Objects.requireNonNull(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail())).get()
                .addOnCompleteListener(task -> {
                    if(!task.isSuccessful()){
                        Toast.makeText(AreaInfo.this, "Login Again!!", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        startActivity(new Intent(getApplicationContext(),Login.class));
                    }
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
                            adapter_crop = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, crop_list);
                            adapter_crop.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            spn_crop.setAdapter(adapter_crop);
                        }else{
                            Toast.makeText(AreaInfo.this, "Try Later!!", Toast.LENGTH_LONG).show();
                            finish();
                        }

                    });
                });
        btn_check.setOnClickListener(v -> {
            if(!spn_crop.getSelectedItem().equals("Select Crop")){
                firebase.collection("state").document(state)
                        .collection("city").document(city)
                        .collection("area").document(area)
                        .collection("crop").document(spn_crop.getSelectedItem().toString()).get(Source.SERVER).addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        Long num = (Long) Objects.requireNonNull(task.getResult()).get("farmers");
                        txt_num.setText(num.toString());
                    }else{
                        Toast.makeText(AreaInfo.this, "Try Later!!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
        spn_crop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txt_num.setText("Press Check");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}