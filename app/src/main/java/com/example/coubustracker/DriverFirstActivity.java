package com.example.coubustracker;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DriverFirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_first);

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
            getSupportActionBar().setTitle("Driver Bus List");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


    }

    public void openLogin(View view) {
        String role = view.getTag().toString();
        Intent intent;

        switch (role) {
            case "red2":
                intent = new Intent(DriverFirstActivity.this, driverredbus2.class);
                break;
            case "red3":
                intent = new Intent(DriverFirstActivity.this, driverredbus3.class);
                break;
            case "red4":
                intent = new Intent(DriverFirstActivity.this, driverredbus4.class);
                break;
            case "red5":
                intent = new Intent(DriverFirstActivity.this, driverredbus5.class);
                break;
            case "red6":
                intent = new Intent(DriverFirstActivity.this, driverredbus6.class);
                break;
            case "red7":
                intent = new Intent(DriverFirstActivity.this, driverredbus7.class);
                break;
            case "red8":
                intent = new Intent(DriverFirstActivity.this, driverredbus8.class);
                break;
            case "red10":
                intent = new Intent(DriverFirstActivity.this, driverredbus10.class);
                break;
            case "blue3":
                intent = new Intent(DriverFirstActivity.this, driverbluebus3.class);
                break;
            case "blue4":
                intent = new Intent(DriverFirstActivity.this, driverbluebus4.class);
                break;
            case "blue5":
                intent = new Intent(DriverFirstActivity.this, driverbluebus5.class);
                break;
            case "blue11":
                intent = new Intent(DriverFirstActivity.this, driverbluebus11.class);
                break;
            case "blue25":
                intent = new Intent(DriverFirstActivity.this, driverbluebus25.class);
                break;
            case "blue31":
                intent = new Intent(DriverFirstActivity.this, driverbluebus31.class);
                break;
            case "blue32":
                intent = new Intent(DriverFirstActivity.this, driverbluebus32.class);
                break;
            case "blue33":
                intent = new Intent(DriverFirstActivity.this, driverbluebus33.class);
                break;
            case "eve1":
                intent = new Intent(DriverFirstActivity.this, driverevebus1.class);
                break;
            case "eve2":
                intent = new Intent(DriverFirstActivity.this, driverevebus2.class);
                break;
            case "eve3":
                intent = new Intent(DriverFirstActivity.this, driverevebus3.class);
                break;
            case "eve4":
                intent = new Intent(DriverFirstActivity.this, driverevebus4.class);
                break;
            case "eve5":
                intent = new Intent(DriverFirstActivity.this, driverevebus5.class);
                break;
            case "eve6":
                intent = new Intent(DriverFirstActivity.this, driverevebus6.class);
                break;
            case "eve7":
                intent = new Intent(DriverFirstActivity.this, driverevebus7.class);
                break;
            case "busscedule":
                intent = new Intent(DriverFirstActivity.this, busscedule.class);
                break;


            default:
                return;  // Handle default or error
        }

        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // Go back to previous activity
        return true;
    }
}
