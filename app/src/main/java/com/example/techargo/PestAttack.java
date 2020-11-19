package com.example.techargo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PestAttack extends AppCompatActivity {
        private FirebaseFirestore firebase;
        private FirebaseAuth mAuth;
        private Spinner spn_pest;
        private List<String> pest_list;
        private ArrayAdapter<String> adapter_pest;
        private ProgressBar bar;
        private String state,city,area;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pestattack);
            Button btn_submit = findViewById(R.id.btn_pest_add);
            spn_pest = findViewById(R.id.spn_pest_attack);
            mAuth = FirebaseAuth.getInstance();
            firebase = FirebaseFirestore.getInstance();
            bar = findViewById(R.id.bar_pest);
            firebase.collection("user").document(Objects.requireNonNull(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail())).get()
                    .addOnCompleteListener(task -> {
                        if(!task.isSuccessful()){
                            Toast.makeText(PestAttack.this, "Login Again!!", Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                            startActivity(new Intent(getApplicationContext(),Login.class));
                        }
                        state = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).get("state")).toString();
                        city = Objects.requireNonNull(task.getResult().get("city")).toString();
                        area = Objects.requireNonNull(task.getResult().get("area")).toString();
                        firebase.collection("state").document(state)
                                .collection("city").document(city)
                                .collection("area").document(area)
                                .collection("attack").get().addOnCompleteListener(task1 -> {
                            if(task1.isSuccessful()){
                                pest_list = new ArrayList<>();
                                pest_list.add("Select Pest Attack");
                                for (QueryDocumentSnapshot documentSnapshot : Objects.requireNonNull(task1.getResult())){
                                    pest_list.add(Objects.requireNonNull(documentSnapshot.get("name")).toString());
                                }
                                pest_list.add("None");
                                adapter_pest = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, pest_list);
                                adapter_pest.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                spn_pest.setAdapter(adapter_pest);
                            }else{
                                Toast.makeText(PestAttack.this, "Login Again!!", Toast.LENGTH_SHORT).show();
                                mAuth.signOut();
                                startActivity(new Intent(getApplicationContext(),Login.class));
                            }
                        });

                    });
            btn_submit.setOnClickListener(v -> {
                bar.setVisibility(View.VISIBLE);
                btn_submit.setEnabled(false);
                btn_submit.setVisibility(View.INVISIBLE);
                if(!spn_pest.getSelectedItem().equals("Select Pest Attack")){
                    firebase.collection("state").document(state)
                            .collection("city").document(city)
                            .collection("area").document(area)
                            .update("attack",spn_pest.getSelectedItem().toString()).addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            Toast.makeText(PestAttack.this, "Pest Attack Added", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainPage.class));
                        }else{
                            Toast.makeText(PestAttack.this, "Some Error Occurred!!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }else{
                    Toast.makeText(this, "Select Pest Attack", Toast.LENGTH_SHORT).show();
                    bar.setVisibility(View.INVISIBLE);
                    btn_submit.setEnabled(true);
                    btn_submit.setVisibility(View.VISIBLE);
                }
            });
        }
}