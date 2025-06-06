package com.example.coubustracker;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.coubustracker.model.BusLocation;
import com.google.firebase.database.*;

public class driverredbus3 extends AppCompatActivity {

    private EditText etDriverName, etMobileNumber, etLocationLink;
    private LinearLayout busListLayout;
    private DatabaseReference busesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverredbus3);

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
            getSupportActionBar().setTitle("Red Bus 3");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etDriverName = findViewById(R.id.etDriverName);
        etMobileNumber = findViewById(R.id.etMobileNumber);
        etLocationLink = findViewById(R.id.etLocationLink);
        busListLayout = findViewById(R.id.driverBusListLayout);
        busesRef = FirebaseDatabase.getInstance().getReference("buses");

        fetchBusList();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void shareBus(View view) {
        String driverName = etDriverName.getText().toString().trim();
        String mobileNumber = etMobileNumber.getText().toString().trim();
        String locationInput = etLocationLink.getText().toString().trim();
        String locationLink = extractValidLink(locationInput);


        if (driverName.isEmpty() || mobileNumber.isEmpty() || locationLink.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isValidGoogleMapsLink(locationLink)) {
            Toast.makeText(this, "Only valid Google Maps location links are allowed", Toast.LENGTH_SHORT).show();
            return;
        }

        BusLocation location = new BusLocation(driverName, mobileNumber, locationLink);
        busesRef.child("Red Bus 3").setValue(location)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Location Shared", Toast.LENGTH_SHORT).show();
                    etDriverName.setText("");
                    etMobileNumber.setText("");
                    etLocationLink.setText("");
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to share location", Toast.LENGTH_SHORT).show());
    }
    private String extractValidLink(String input) {
        int startIndex = input.indexOf("https");
        return (startIndex != -1) ? input.substring(startIndex).trim() : "";
    }

    private boolean isValidGoogleMapsLink(String link) {
        return link.startsWith("https://maps.app.goo.gl/")
                || link.startsWith("https://www.google.com/maps?q=")
                || link.startsWith("https://www.google.com/maps/place/");
    }

    private void fetchBusList() {
        busesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                busListLayout.removeAllViews();

                for (DataSnapshot child : snapshot.getChildren()) {
                    String key = child.getKey();
                    BusLocation location = child.getValue(BusLocation.class);

                    if (location != null) {
                        View itemView = getLayoutInflater().inflate(android.R.layout.simple_list_item_2, null);
                        TextView text1 = itemView.findViewById(android.R.id.text1);
                        TextView text2 = itemView.findViewById(android.R.id.text2);

                        text1.setText("Driver: " + location.getDriverName());
                        text2.setText("Mobile: " + location.getMobileNumber() + "\nLocation: " + location.getLocationLink());

                        int blackColor = ContextCompat.getColor(driverredbus3.this, android.R.color.black);
                        text1.setTextSize(18);
                        text2.setTextSize(16);
                        text1.setTextColor(blackColor);
                        text2.setTextColor(blackColor);

                        Button deleteButton = new Button(driverredbus3.this);
                        deleteButton.setText("Delete");
                        deleteButton.setTextSize(14);
                        deleteButton.setAllCaps(false);
                        deleteButton.setTextColor(ContextCompat.getColor(driverredbus3.this, android.R.color.white));
                        deleteButton.setBackgroundColor(ContextCompat.getColor(driverredbus3.this, android.R.color.holo_red_dark));

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        params.setMargins(0, 12, 0, 0);
                        deleteButton.setLayoutParams(params);
                        deleteButton.setPadding(30, 20, 30, 20);
                        deleteButton.setOnClickListener(v -> deleteBus(key));

                        LinearLayout container = new LinearLayout(driverredbus3.this);
                        container.setOrientation(LinearLayout.VERTICAL);
                        container.setPadding(0, 20, 0, 20);
                        container.addView(itemView);
                        container.addView(deleteButton);

                        busListLayout.addView(container);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(driverredbus3.this, "Failed to load buses", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteBus(String key) {
        busesRef.child(key).removeValue()
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(this, "Bus removed", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to remove", Toast.LENGTH_SHORT).show());
    }
}
