package com.example.pastachat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Preloader extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    private TextView textLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preloader);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        textLog = findViewById(R.id.textLog);

        CheckUserLoggedIn();
    }

    private void CheckUserLoggedIn() {
        textLog.setText("Checking user logged in already.");
        Log.d("PRELOADER", "Checking user logged in already.");

        FirebaseUser currentUser = auth.getCurrentUser();

        if(currentUser != null) {
            textLog.setText("User logged in. Checking database document.");
            Log.d("PRELOADER", "User logged in. Checking database document.");


            Db.DocumentExists(db, "Users", "email", currentUser.getEmail(), new Db.DocumentExistsCallback() {
                @Override
                public void DocumentExistsCompleted(boolean exists) {
                    if(exists) {
                        textLog.setText("User document exists. Opening main.");
                        Log.d("PRELOADER", "User document exists. Opening main.");

                        Intent intent = new Intent(Preloader.this, MainActivity.class);

                        startActivity(intent);
                        finish();
                    } else {
                        textLog.setText("User document does not exist. Opening signup second screen.");
                        Log.d("PRELOADER", "User document does not exist. Opening signup second screen.");

                        Intent intent = new Intent(Preloader.this, SignUp.class);

                        intent.putExtra("default", 1);

                        startActivity(intent);
                        finish();
                    }
                }
            });


        } else {
            textLog.setText("User not logged in. Opening login.");
            Log.d("PRELOADER", "User not logged in. Opening login.");

            Intent intent = new Intent(Preloader.this, SignIn.class);

            startActivity(intent);
            finish();
        }

    }


}