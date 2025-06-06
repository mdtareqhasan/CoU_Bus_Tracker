package com.example.coubustracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText etDriverId, etPassword;
    Button btnContinue;
    FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;

    private static final String PREF_NAME = "DriverPrefs";
    private static final String KEY_ID = "driverId";
    private static final String KEY_PASS = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
            // Light mode: set dark icons on white background
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                getWindow().getInsetsController().setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                );
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.white));
        } else {
            // Dark mode: set light icons on dark background
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                getWindow().getInsetsController().setSystemBarsAppearance(
                        0,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                );
            } else {
                getWindow().getDecorView().setSystemUiVisibility(0); // default light icons
            }
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.black));
        }


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Driver Login");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etDriverId = findViewById(R.id.etDriverId);
        etPassword = findViewById(R.id.etPassword);
        btnContinue = findViewById(R.id.btnContinue);
        mAuth = FirebaseAuth.getInstance();

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String savedId = sharedPreferences.getString(KEY_ID, null);
        String savedPass = sharedPreferences.getString(KEY_PASS, null);

        if (savedId != null && savedPass != null) {
            etDriverId.setText(savedId);
            etPassword.setText(savedPass);
        }

        btnContinue.setOnClickListener(v -> {
            String driverId = etDriverId.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (driverId.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter ID and Password", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!driverId.equals("driver")) {
                Toast.makeText(LoginActivity.this, "Only 'driver' ID is allowed", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword("driver@coubus.com", password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(KEY_ID, driverId);
                            editor.putString(KEY_PASS, password);
                            editor.apply();

                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, DriverFirstActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
