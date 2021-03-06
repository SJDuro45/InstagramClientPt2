package com.amyhuyen.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

    // the views
    @BindView(R.id.etName) EditText etName;
    @BindView(R.id.etEmail) EditText etEmail;
    @BindView(R.id.etPhone) EditText etPhone;
    @BindView(R.id.etUsername) EditText etUsername;
    @BindView(R.id.etPassword) EditText etPassword;
    @BindView(R.id.etConfirmPassword) EditText etConfirmPassword;
    @BindView(R.id.btnSignUp) Button btnSignUp;
    @BindView(R.id.tvLogin) TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // bind using butterknife
        ButterKnife.bind(this);

    }

    // on click for create account button
    @OnClick(R.id.btnSignUp)
    public void onSignUpClick(){
        if (ParseUser.getCurrentUser() != null){
            ParseUser.logOut();
        }
        // access text in views
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String phone = etName.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        // create user conditions
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(username)
                && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)){
            if (password.equals(confirmPassword)){
                createUser(email, name, username, password, phone);
            } else{
                Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(SignUpActivity.this, "Please fill in all required fields", Toast.LENGTH_LONG).show();

        }
    }

    // on click for login text view
    @OnClick(R.id.tvLogin)
    public void onLoginClick(){
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    // method to create the user on the parse server
    public void createUser(String email, String name, String username, String password, @Nullable String phone){
        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(name);
        user.setPassword(password);
        user.setEmail(email);
        // Set custom properties
        user.put("handle", username);
        if (phone != null){
            user.put("phone", phone);
        }
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Login success
                    Intent intent = new Intent(SignUpActivity.this, LandingActivity.class);
                    Toast.makeText(SignUpActivity.this, "Account created", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    finish();
                } else {
                    // Sign up fails
                    e.printStackTrace();
                }
            }
        });
    }
}
