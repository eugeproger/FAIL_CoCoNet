package com.proger.coconet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseUser currentUser;
    private Button logInButton, phoneLogInButton;
    private EditText userLoginEmailField, userLoginPasswordField;
    private TextView signUpLink, createNewPasswordLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeElements();

        signUpLink.setOnClickListener(view -> sendUserToSignupActivity());
    }

    private void initializeElements() {
        logInButton = findViewById(R.id.log_in_button);
        phoneLogInButton = findViewById(R.id.phone_log_in_button);
        userLoginEmailField = findViewById(R.id.user_login_email_field);
        userLoginPasswordField = findViewById(R.id.user_login_password_field);
        signUpLink = findViewById(R.id.sign_up_link);
        createNewPasswordLink = findViewById(R.id.create_new_password_link);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (currentUser != null) {
            sendUserToAppActivity();
        }
    }

    private void sendUserToAppActivity() {
        Intent appIntent = new Intent(LoginActivity.this, AppActivity.class);
        startActivity(appIntent);
    }

    private void sendUserToSignupActivity() {
        Intent signupIntent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(signupIntent);
    }
}