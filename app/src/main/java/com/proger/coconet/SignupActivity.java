package com.proger.coconet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignupActivity extends AppCompatActivity {

    private Button signUpButton;
    private EditText userSignupEmailField, userSignupPasswordField;
    private TextView logInLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initializeElements();

        logInLink.setOnClickListener(view -> sendUserToLoginActivity());
    }

    private void initializeElements() {
        signUpButton = findViewById(R.id.sign_up_button);
        userSignupEmailField = findViewById(R.id.user_signup_email_field);
        userSignupPasswordField = findViewById(R.id.user_signup_password_field);
        logInLink = findViewById(R.id.log_in_link);
    }

    private void sendUserToLoginActivity() {
        Intent loginIntent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }
}