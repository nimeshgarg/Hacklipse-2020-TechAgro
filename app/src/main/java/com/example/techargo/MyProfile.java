package com.example.techargo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MyProfile extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView txt_name,txt_email,txt_area,txt_crop,txt_state,txt_city;
    private ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        Button btn_logout = findViewById(R.id.btn_out);
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebase = FirebaseFirestore.getInstance();
        txt_area = findViewById(R.id.txt_profile_area);
        txt_crop = findViewById(R.id.txt_profile_crop);
        txt_email = findViewById(R.id.txt_profile_email);
        txt_name = findViewById(R.id.txt_profile_name);
        txt_state = findViewById(R.id.txt_profile_state);
        txt_city = findViewById(R.id.txt_profile_city);
        bar = findViewById(R.id.bar_profile);
        btn_logout.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(getApplicationContext(),Login.class));
        });
        bar.setVisibility(View.VISIBLE);
        firebase.collection("user").document(Objects.requireNonNull(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail())).get()
                .addOnCompleteListener(task -> {
                    if(!task.isSuccessful()){
                        Toast.makeText(MyProfile.this, "Try Again Later!!", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        finish();
                    }
                    txt_state.setText(Objects.requireNonNull(Objects.requireNonNull(task.getResult()).get("state")).toString());
                    txt_city.setText(Objects.requireNonNull(task.getResult().get("city")).toString());
                    txt_area.setText(Objects.requireNonNull(task.getResult().get("area")).toString());
                    txt_crop.setText(Objects.requireNonNull(task.getResult().get("crop")).toString());
                    txt_name.setText(Objects.requireNonNull(task.getResult().get("name")).toString());
                    txt_email.setText(mAuth.getCurrentUser().getEmail());
                    bar.setVisibility(View.INVISIBLE);
                });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainPage.class));
    }
}