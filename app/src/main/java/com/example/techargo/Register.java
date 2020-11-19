package com.example.techargo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Register extends AppCompatActivity {
     private Spinner spn_state,spn_city,spn_area,spn_crop;
     private FirebaseAuth mAuth;
     private FirebaseFirestore firebase;
     private List<String> state_list,city_list,area_list,crop_list;
     private ArrayAdapter<String> adapter_state,adapter_city,adapter_area,adapter_crop;
     private Button btn_submit;
     private EditText txt_email,txt_name,txt_pass;
     private ProgressBar bar;
     private FirebaseAnalytics analytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        spn_state = findViewById(R.id.spn_state_register);
        spn_city = findViewById(R.id.spn_city_register);
        spn_area = findViewById(R.id.spn_area_register);
        spn_crop = findViewById(R.id.spn_crop_register);
        btn_submit = findViewById(R.id.btn_register);
        txt_email = findViewById(R.id.txt_register_email);
        txt_name = findViewById(R.id.txt_register_name);
        txt_pass = findViewById(R.id.txt_register_password);
        analytics = FirebaseAnalytics.getInstance(this);
        bar = findViewById(R.id.bar_register);
        firebase = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firebase.collection("state").get().addOnCompleteListener(task -> {
            state_list = new ArrayList<>();
            state_list.add("Select State");
            adapter_state = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, state_list);
            if(!task.isSuccessful()){
                Toast.makeText(Register.this, "Try Again Later!!", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                finish();
            }
            for (QueryDocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())){
                state_list.add(Objects.requireNonNull(documentSnapshot.get("name")).toString());
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
        btn_submit.setOnClickListener(v -> {
            btn_submit.setEnabled(false);
            btn_submit.setVisibility(View.INVISIBLE);
            bar.setVisibility(View.VISIBLE);
            if(isEmailValid(txt_email.getText())){
                if(!txt_name.getText().toString().equals("")){
                    if(txt_pass.getText().toString().length()>=8){
                        if(!spn_state.getSelectedItem().toString().equals("Select State")){
                            if(!spn_city.getSelectedItem().toString().equals("Select City")){
                                if(!spn_area.getSelectedItem().toString().equals("Select Area")){
                                    if(!spn_crop.getSelectedItem().toString().equals("Select Crop")){
                                        mAuth.createUserWithEmailAndPassword(txt_email.getText().toString(),txt_pass.getText().toString())
                                                .addOnCompleteListener(task -> {
                                                    if(task.isSuccessful()){
                                                        analytics.setUserProperty("State",spn_state.getSelectedItem().toString());
                                                        analytics.setUserProperty("City",spn_city.getSelectedItem().toString());
                                                        analytics.setUserProperty("Area",spn_area.getSelectedItem().toString());
                                                        Map<String,String> data = new HashMap<>();
                                                        data.put("name",txt_name.getText().toString());
                                                        data.put("email",txt_email.getText().toString());
                                                        data.put("state",spn_state.getSelectedItem().toString());
                                                        data.put("city",spn_city.getSelectedItem().toString());
                                                        data.put("area",spn_area.getSelectedItem().toString());
                                                        data.put("crop",spn_crop.getSelectedItem().toString());
                                                        firebase.collection("user").document(txt_email.getText().toString()).set(data)
                                                                .addOnCompleteListener(task12 -> {
                                                                    if(task12.isSuccessful()){
                                                                        Toast.makeText(Register.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                                                                        startActivity(new Intent(getApplicationContext(),MainPage.class));
                                                                    }else{
                                                                        Toast.makeText(Register.this, "Some Error Occurred!!", Toast.LENGTH_SHORT).show();
                                                                        mAuth.signOut();
                                                                        finish();
                                                                    }
                                                                });
                                                    }else {
                                                        Toast.makeText(Register.this, "Try Later", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    }
                                                });
                                    }else{
                                        Toast.makeText(Register.this, "Select Crop!!", Toast.LENGTH_SHORT).show();
                                        btn_submit.setEnabled(true);
                                        bar.setVisibility(View.INVISIBLE);
                                        btn_submit.setVisibility(View.VISIBLE);
                                    }
                                }else{
                                    Toast.makeText(Register.this, "Select Area!!", Toast.LENGTH_SHORT).show();
                                    btn_submit.setEnabled(true);
                                    bar.setVisibility(View.INVISIBLE);
                                    btn_submit.setVisibility(View.VISIBLE);
                                }
                            }else{
                                Toast.makeText(Register.this, "Select City!!", Toast.LENGTH_SHORT).show();
                                btn_submit.setEnabled(true);
                                bar.setVisibility(View.INVISIBLE);
                                btn_submit.setVisibility(View.VISIBLE);
                            }
                        }else{
                            Toast.makeText(Register.this, "Select State!!", Toast.LENGTH_SHORT).show();
                            btn_submit.setEnabled(true);
                            bar.setVisibility(View.INVISIBLE);
                            btn_submit.setVisibility(View.VISIBLE);
                        }

                    }else{
                        Toast.makeText(Register.this, "Password is Too Short!!", Toast.LENGTH_SHORT).show();
                        btn_submit.setEnabled(true);
                        bar.setVisibility(View.INVISIBLE);
                        btn_submit.setVisibility(View.VISIBLE);
                    }
                }else{
                    Toast.makeText(Register.this, "Enter A Name !!", Toast.LENGTH_SHORT).show();
                    btn_submit.setEnabled(true);
                    bar.setVisibility(View.INVISIBLE);
                    btn_submit.setVisibility(View.VISIBLE);
                }
            }else{
                Toast.makeText(Register.this, "Enter A Valid Email!!", Toast.LENGTH_SHORT).show();
                btn_submit.setEnabled(true);
                bar.setVisibility(View.INVISIBLE);
                btn_submit.setVisibility(View.VISIBLE);
            }
        });
    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),Login.class));
    }
}

