package com.example.doggie_diet;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AuthActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button actionButton;
    private TextView toggleTextView;
    private boolean isLoginMode = true;  // Initially, set to Login mode
    private DBHelper dbHelper;  // DBHelper instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // Initialize DBHelper
        dbHelper = new DBHelper(this);

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

                // Email format validation
                if (!isValidEmail(email)) {
                    Toast.makeText(AuthActivity.this, "Email is badly formatted", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Password length validation
                if (password.length() < 6) {
                    Toast.makeText(AuthActivity.this, "Password is too short, must be at least 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

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

    // Email format validation
    private boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void loginUser(String email, String password) {
        if (dbHelper.checkUser(email, password)) {
            Toast.makeText(AuthActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
            // Navigate to ContentActivity
            Intent intent = new Intent(AuthActivity.this, ContentActivity.class);
            startActivity(intent);
            finish(); // Optionally close AuthActivity
        } else {
            Toast.makeText(AuthActivity.this, "Login failed: Incorrect email or password", Toast.LENGTH_SHORT).show();
        }
    }

    private void signUpUser(String email, String password) {
        if (dbHelper.insertUser(email, password)) {
            Toast.makeText(AuthActivity.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
            // Navigate to ContentActivity
            Intent intent = new Intent(AuthActivity.this, ContentActivity.class);
            startActivity(intent);
            finish(); // Optionally close AuthActivity
        } else {
            Toast.makeText(AuthActivity.this, "Sign up failed: User already exists", Toast.LENGTH_SHORT).show();
        }
    }
}
