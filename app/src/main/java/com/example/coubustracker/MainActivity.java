package com.example.coubustracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String STUDENT_PREF = "StudentPrefs";
    private static final String DRIVER_PREF = "DriverPrefs";
    private static final String KEY_ID = "studentId";
    private static final String KEY_PASS = "password";
    private static final String CURRENT_APP_VERSION = "1.1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        checkAppVersion();
    }

    private void checkAppVersion() {
        FirebaseDatabase.getInstance().getReference("appVersion/required")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        String requiredVersion = snapshot.getValue(String.class);
                        if (!CURRENT_APP_VERSION.equals(requiredVersion)) {
                            showUpdateDialog();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(MainActivity.this, "Version check failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showUpdateDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Update Required")
                .setMessage("Please update the app to the latest version to continue.")
                .setCancelable(false)
                .setPositiveButton("Exit", (dialog, which) -> finish())
                .show();
    }

    public void openLogin(View view) {
        String role = view.getTag().toString();
        Intent intent;

        switch (role) {
            case "Student":
                intent = new Intent(MainActivity.this, StudentLoginActivity.class);
                break;

            case "Driver":
                intent = new Intent(MainActivity.this, LoginActivity.class);
                break;

            default:
                return;
        }

        startActivity(intent);
    }
}
