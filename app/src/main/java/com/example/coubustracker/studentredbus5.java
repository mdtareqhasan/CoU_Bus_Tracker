package com.example.coubustracker;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coubustracker.model.BusLocation;
import com.google.firebase.database.*;

public class studentredbus5 extends AppCompatActivity {
    private TextView tvDriverName, tvMobileNumber, tvLocationLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentredbus5);

        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {

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
            getSupportActionBar().setTitle("Red Bus 5");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvDriverName = findViewById(R.id.tvDriverName);
        tvMobileNumber = findViewById(R.id.tvMobileNumber);
        tvLocationLink = findViewById(R.id.tvLocationLink);
        tvLocationLink.setTextSize(16);
        FirebaseDatabase.getInstance().getReference("buses")
                .child("Red Bus 5")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        BusLocation loc = snapshot.getValue(BusLocation.class);
                        if (loc != null) {
                            tvDriverName.setText("Driver Name: " + loc.getDriverName());
                            tvMobileNumber.setText("Mobile Number: " + loc.getMobileNumber());
                            tvLocationLink.setText("Tap here to open live location");

                            tvLocationLink.setOnClickListener(v -> {
                                String url = loc.getLocationLink();
                                if (url == null || url.isEmpty()) {
                                    Toast.makeText(studentredbus5.this, "Location link not available", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                Uri gmmIntentUri = Uri.parse(url);
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");

                                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                                    startActivity(mapIntent);
                                } else {
                                    startActivity(new Intent(Intent.ACTION_VIEW, gmmIntentUri));
                                }
                            });
                        } else {
                            Toast.makeText(studentredbus5.this, "Red Bus 2 not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(studentredbus5.this, "Error loading data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // Go back when back button pressed
        return true;
    }
}
