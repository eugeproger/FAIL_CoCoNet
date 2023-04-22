package com.proger.coconet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    private Button signUpButton;
    private EditText userSignupEmailField, userSignupPasswordField;
    private TextView logInLink;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private void initializeElements() {
        signUpButton = findViewById(R.id.sign_up_button);
        userSignupEmailField = findViewById(R.id.user_signup_email_field);
        userSignupPasswordField = findViewById(R.id.user_signup_password_field);
        logInLink = findViewById(R.id.log_in_link);
        progressDialog = new ProgressDialog(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initializeElements();

        firebaseAuth = FirebaseAuth.getInstance();

        logInLink.setOnClickListener(view -> sendUserToLoginActivity());
        signUpButton.setOnClickListener(view -> createAccount());

    }

    private void createAccount() {
        String email = userSignupEmailField.getText().toString();
        String password = userSignupPasswordField.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Utility.showShortToast(this,"Please, enter your email.");
        } else if (TextUtils.isEmpty(password)) {
            Utility.showShortToast(this,"Please, enter your password.");
        } else {
            progressDialog.setTitle("Creating account");
            progressDialog.setMessage("Your account are creating...");
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        sendUserToLoginActivity();
                        Utility.showLongToast(SignupActivity.this, "Account created successfully");
                        progressDialog.dismiss();
                    } else {
                        String message = task.getException().toString();
                        Utility.showLongToast(SignupActivity.this, "Error: " + message);
                        progressDialog.dismiss();
                    }
                }
            });
        }
    }


    private void sendUserToLoginActivity() {
        Intent loginIntent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }
}