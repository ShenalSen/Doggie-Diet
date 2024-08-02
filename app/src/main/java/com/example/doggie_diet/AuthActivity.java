package com.example.doggie_diet;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button actionButton;
    private TextView toggleTextView;
    private FirebaseAuth mAuth;

    private boolean isLoginMode = true;  // Initially, set to Login mode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Get references to UI elements
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        actionButton = findViewById(R.id.actionButton);
        toggleTextView = findViewById(R.id.toggleTextView);

        // Set click listener on the action button (Login or Sign-Up)
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(AuthActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isLoginMode) {
                    loginUser(email, password);
                } else {
                    signUpUser(email, password);
                }
            }
        });

        // Toggle between Login and Sign-Up mode
        toggleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoginMode) {
                    isLoginMode = false;
                    actionButton.setText("Sign Up");
                    toggleTextView.setText("Already have an account? Login");
                } else {
                    isLoginMode = true;
                    actionButton.setText("Login");
                    toggleTextView.setText("Don't have an account? Sign Up");
                }
            }
        });
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(AuthActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                            // Navigate to the next activity
                        } else {
                            Toast.makeText(AuthActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signUpUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(AuthActivity.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
                            // Optionally, navigate to the next activity
                        } else {
                            Toast.makeText(AuthActivity.this, "Sign up failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
