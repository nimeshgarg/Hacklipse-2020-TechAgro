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

import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {
    private Spinner spn_state;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebase;
    private List<String> state_list;
    private ArrayAdapter<String> adapter_state;
    private String sel_state = "null", sel_city = "null", sel_area = "null";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        spn_state = findViewById(R.id.spn_state_register);
        firebase = FirebaseFirestore.getInstance();
        firebase.collection("state").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
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
            }
        });
        spn_state.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                if(i!=0){
                    // code for city does here
                    // collection("state").document(spn_state.getSelectedItem().toString()).collection("city")
                }
            }
        });
    }
}