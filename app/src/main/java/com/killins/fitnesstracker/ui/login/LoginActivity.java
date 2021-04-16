package com.killins.fitnesstracker.ui.login;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.killins.fitnesstracker.R;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private LoginViewModel loginViewModel;

    private EditText usernameText;
    private EditText passwordText;
    private Button loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this, new LoginViewModel.Factory(getApplicationContext())).get(LoginViewModel.class);
        usernameText = findViewById(R.id.input_username);
        passwordText = findViewById(R.id.goal_value);
        loginButton = findViewById(R.id.btn_login);
        TextView signupLink = findViewById(R.id.link_signup);

        loginButton.setOnClickListener(v -> login());

        signupLink.setOnClickListener(v -> {
            // Start the Signup activity
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            startActivityForResult(intent, REQUEST_SIGNUP);
            finish();
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        String email = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        // TODO:
        //  Fix authentication threading
        //  Hash passwords? Right now passwords are just stored in db as plaintext

        boolean isAuthenticated = loginViewModel.checkValidLogin(email, password);
        if(isAuthenticated){
            Toast.makeText(getBaseContext(), "User Authenticated!", Toast.LENGTH_LONG).show();
            onLoginSuccess();
        }else{
            onLoginFailed();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SIGNUP && resultCode == RESULT_OK) {
                // TODO: Decide what to do after successful sign up.  Make users log in or navigate directly to the app?
                Toast.makeText(getBaseContext(), "User Successfully Created! Please Log In.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        getSharedPreferences("LOGINPREFERENCE", MODE_PRIVATE).edit().putBoolean("signIn", false).apply();
        getSharedPreferences("LOGINPREFERENCE", MODE_PRIVATE).edit().putString("currentUser", usernameText.getText().toString()).apply();
        Toast.makeText(getBaseContext(), "Successfully Logged In!", Toast.LENGTH_LONG).show();
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty()) {
            usernameText.setError("Enter a valid username!");
            valid = false;
        } else {
            usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            passwordText.setError("Password must be at least 6 characters long!");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }
}
