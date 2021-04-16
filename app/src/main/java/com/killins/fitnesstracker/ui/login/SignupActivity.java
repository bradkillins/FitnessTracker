package com.killins.fitnesstracker.ui.login;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.killins.fitnesstracker.R;


public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private LoginViewModel loginViewModel;

    EditText userNameText;
    EditText nameText;
    EditText emailText;
    EditText passwordText;
    EditText reEnterPasswordText;
    Button signupButton;
    TextView loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        userNameText = findViewById(R.id.input_username);
        nameText = findViewById(R.id.input_name);
        emailText = findViewById(R.id.input_email);
        passwordText = findViewById(R.id.goal_value);
        reEnterPasswordText = findViewById(R.id.input_reEnterPassword);
        signupButton = findViewById(R.id.btn_signup);
        loginLink = findViewById(R.id.link_login);

        signupButton.setOnClickListener(v -> signup());

        loginLink.setOnClickListener(v -> {
            // Finish the registration screen and return to the Login activity
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        });
    }

    public void signup() {

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signupButton.setEnabled(false);

        String username = userNameText.getText().toString();
        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        loginViewModel.createUser(username, name, email, password);
        loginViewModel.populateGoals(username);

        onSignupSuccess();
        //onSignupFailed();
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        getSharedPreferences("LOGINPREFERENCE", MODE_PRIVATE).edit().putBoolean("signIn", false).apply();
        getSharedPreferences("LOGINPREFERENCE", MODE_PRIVATE).edit().putString("currentUser", userNameText.getText().toString()).apply();
        setResult(RESULT_OK, null);
        finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = userNameText.getText().toString();
        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();

        if (username.isEmpty()) {
            nameText.setError("Enter a Username!");
            valid = false;
        } else {
            userNameText.setError(null);
        }

        if (name.isEmpty()) {
            nameText.setError("Enter a name!");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Enter a valid email address!");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            passwordText.setError("Password must be at least 6 characters long!");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 6 || !(reEnterPassword.equals(password))) {
            reEnterPasswordText.setError("Passwords do not match!");
            valid = false;
        } else {
            reEnterPasswordText.setError(null);
        }

        return valid;
    }
}
