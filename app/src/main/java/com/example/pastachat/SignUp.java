package com.example.pastachat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pastachat.Interfaces.SignUpInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity implements SignUpInterface {

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    private EditText emailText, passwordText;
    private Button signupButton;
    private TextView signInText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        emailText = findViewById(R.id.emailSignupText);
        passwordText = findViewById(R.id.passwordSignupText);
        signupButton = findViewById(R.id.signUpButton);
        signInText = findViewById(R.id.sign_in);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();

                SignupEmailPassword(email, password);
            }
        });

        signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, SignIn.class);

                startActivity(intent);
            }
        });

        int defaultState = getIntent().getIntExtra("default", 0);
        ChangeState(defaultState);
    }

    private void SignupEmailPassword(final String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("SIGNUP", "Signup operation completed.");

                if(task.isSuccessful()) {
                    SignupSuccess();
                } else {
                    SignupFail();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("SIGNUP", "Signup error: " + e.getMessage());
            }
        });
    }

    private void CreateDbRecord(String email, String username) {
        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("username", username);
        user.put("signupDate", new Timestamp(new Date()));

        db.collection("Users").add(user).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Log.d("SIGNUPDB", "Document creation operation completed.");

                if(task.isSuccessful()) {
                    DbSuccess();
                } else {
                    DbFail();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("SIGNUPDB", "Document creation error: " + e.getMessage());
            }
        });
    }

    private void ChangeState(int state) {
        switch (state) {
            case 0:
                // Default screen, do nothing
                break;
            case 1:
                // Second screen
                break;
        }
    }

    @Override
    public void SignupSuccess() {
        // Load next page of register (language preferences etc.)
        ChangeState(1);
    }

    @Override
    public void SignupFail() {

    }

    @Override
    public void DbSuccess() {
        Intent intent = new Intent(SignUp.this, SignIn.class);

        startActivity(intent);
        finish();
    }

    @Override
    public void DbFail() {

    }
}