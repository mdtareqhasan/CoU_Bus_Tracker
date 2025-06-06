package com.example.coubustracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.content.res.Configuration;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class StudentLoginActivity extends AppCompatActivity {

    EditText etStudentId, etPassword;
    Button btnContinue;
    SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "StudentPrefs";
    private static final String KEY_ID = "studentId";
    private static final String KEY_PASS = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

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
            getSupportActionBar().setTitle("Student Login");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etStudentId = findViewById(R.id.etStudentId);
        etPassword = findViewById(R.id.etPassword);
        btnContinue = findViewById(R.id.btnContinue);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Auto-fill if data exists
        String savedId = sharedPreferences.getString(KEY_ID, null);
        String savedPass = sharedPreferences.getString(KEY_PASS, null);
        if (savedId != null && savedPass != null) {
            etStudentId.setText(savedId);
            etPassword.setText(savedPass);
        }

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentId = etStudentId.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (studentId.isEmpty() || password.isEmpty()) {
                    Toast.makeText(StudentLoginActivity.this, "Please enter ID and Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (studentId.equals("student") && password.equals("12345")) {
                    // Save to SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_ID, studentId);
                    editor.putString(KEY_PASS, password);
                    editor.apply();

                    Toast.makeText(StudentLoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(StudentLoginActivity.this, StudentFirstActivity.class));
                    finish();
                } else {
                    Toast.makeText(StudentLoginActivity.this, "Invalid ID or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
