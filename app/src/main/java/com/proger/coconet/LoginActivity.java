package com.proger.coconet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseUser currentUser;
    private Button logInButton, phoneLogInButton;
    private EditText userLoginEmailField, userLoginPasswordField;
    private TextView signUpLink, createNewPasswordLink;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private void initializeElements() {
        logInButton = findViewById(R.id.log_in_button);
        phoneLogInButton = findViewById(R.id.phone_log_in_button);
        userLoginEmailField = findViewById(R.id.user_login_email_field);
        userLoginPasswordField = findViewById(R.id.user_login_password_field);
        signUpLink = findViewById(R.id.sign_up_link);
        createNewPasswordLink = findViewById(R.id.create_new_password_link);
        progressDialog = new ProgressDialog(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeElements();

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        signUpLink.setOnClickListener(view -> sendUserToSignupActivity());
        logInButton.setOnClickListener(view -> allowUserToLogIn());
    }

    private void allowUserToLogIn() {
        String email = userLoginEmailField.getText().toString();
        String password = userLoginPasswordField.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Utility.showShortToast(this,"Please, enter your email.");
        } else if (TextUtils.isEmpty(password)) {
            Utility.showShortToast(this,"Please, enter your password.");
        } else {
            progressDialog.setTitle("Sign in");
            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        sendUserToAppActivity();
                        Utility.showShortToast(LoginActivity.this, "Logged in successful");
                        progressDialog.dismiss();
                    } else {
                        String message = task.getException().toString();
                        Utility.showLongToast(LoginActivity.this, "Error: " + message);
                        progressDialog.dismiss();
                    }
                }
            });
        }
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