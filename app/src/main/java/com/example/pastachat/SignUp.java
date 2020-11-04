package com.example.pastachat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pastachat.Interfaces.SignUpInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity implements SignUpInterface {

    private FirebaseAuth auth;

    private EditText emailText, passwordText;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();

        emailText = findViewById(R.id.emailSignupText);
        passwordText = findViewById(R.id.passwordSignupText);

        signupButton = findViewById(R.id.signupButton);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();

                SignupEmailPassword(email, password);
            }
        });
    }

    private void SignupEmailPassword(String email, String password) {
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

    @Override
    public void SignupSuccess() {
        // Load next page of register (language preferences etc.)
    }

    @Override
    public void SignupFail() {

    }
}