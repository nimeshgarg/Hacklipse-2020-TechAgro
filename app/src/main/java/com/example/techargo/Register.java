package com.example.techargo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Register extends AppCompatActivity {
     private Spinner spn_state,spn_city,spn_area,spn_crop;
     private FirebaseAuth mAuth;
     private FirebaseFirestore firebase;
     private List<String> state_list,city_list,area_list,crop_list;
     private ArrayAdapter<String> adapter_state,adapter_city,adapter_area,adapter_crop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        spn_state = findViewById(R.id.spn_state_register);
        spn_city = findViewById(R.id.spn_city_register);
        spn_area = findViewById(R.id.spn_area_register);
        spn_crop = findViewById(R.id.spn_crop_register);
        firebase = FirebaseFirestore.getInstance();
        firebase.collection("state").get().addOnCompleteListener(task -> {
            state_list = new ArrayList<>();
            state_list.add("Select State");
            adapter_state = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, state_list);
            if(!task.isSuccessful()){
                Toast.makeText(Register.this, "Try Again Later!!", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                finish();
            }
            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                state_list.add(documentSnapshot.get("name").toString());
            }
            adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_item);
            spn_state.setAdapter(adapter_state);
        });
        spn_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                if(i!=0){
                    firebase.collection("state").document(spn_state.getSelectedItem().toString()).collection("city").get().addOnCompleteListener(task1 -> {
                        if(task1.isSuccessful()){
                            city_list = new ArrayList<>();
                            city_list.add("Select City");
                            for (QueryDocumentSnapshot documentSnapshot : Objects.requireNonNull(task1.getResult())){
                                city_list.add(Objects.requireNonNull(documentSnapshot.get("name")).toString());
                            }
                            adapter_city = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, city_list);
                            adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            spn_city.setAdapter(adapter_city);
                        }else{
                            Toast.makeText(Register.this, "Try Later!!", Toast.LENGTH_LONG).show();
                            finish();
                        }

                    });
                }else{
                    city_list = new ArrayList<>();
                    adapter_city = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, city_list);
                    adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spn_city.setAdapter(adapter_city);
                    area_list = new ArrayList<>();
                    adapter_area = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, area_list);
                    adapter_area.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spn_area.setAdapter(adapter_area);
                    crop_list = new ArrayList<>();
                    adapter_crop = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, crop_list);
                    adapter_crop.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spn_crop.setAdapter(adapter_crop);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spn_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                if(i!=0){
                    firebase.collection("state").document(spn_state.getSelectedItem().toString()).collection("city")
                            .document(spn_city.getSelectedItem().toString()).collection("area").get().addOnCompleteListener(task1 -> {
                        if(task1.isSuccessful()){
                            area_list = new ArrayList<>();
                            area_list.add("Select Area");
                            for (QueryDocumentSnapshot documentSnapshot : Objects.requireNonNull(task1.getResult())){
                                area_list.add(Objects.requireNonNull(documentSnapshot.get("name")).toString());
                            }
                            adapter_area = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, area_list);
                            adapter_area.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            spn_area.setAdapter(adapter_area);
                        }else{
                            Toast.makeText(Register.this, "Try Later!!", Toast.LENGTH_LONG).show();
                            finish();
                        }

                    });
                }else{
                    area_list = new ArrayList<>();
                    adapter_area = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, area_list);
                    adapter_area.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spn_area.setAdapter(adapter_area);
                    crop_list = new ArrayList<>();
                    adapter_crop = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, crop_list);
                    adapter_crop.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spn_crop.setAdapter(adapter_crop);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spn_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                if(i!=0){
                    firebase.collection("state").document(spn_state.getSelectedItem().toString()).collection("city")
                            .document(spn_city.getSelectedItem().toString()).collection("area")
                            .document(spn_area.getSelectedItem().toString()).collection("crop").get().addOnCompleteListener(task1 -> {
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
                            Toast.makeText(Register.this, "Try Later!!", Toast.LENGTH_LONG).show();
                            finish();
                        }

                    });
                }else{
                    crop_list = new ArrayList<>();
                    adapter_crop = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, crop_list);
                    adapter_crop.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spn_crop.setAdapter(adapter_crop);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}

