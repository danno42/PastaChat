package com.example.pastachat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Preloader extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preloader);

        auth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = auth.getCurrentUser();

        Intent intent;
        if(currentUser != null) {
            intent = new Intent(Preloader.this, MainActivity.class);
        } else {
            intent = new Intent(Preloader.this, Login.class);
        }
        startActivity(intent);
        finish();
    }

}