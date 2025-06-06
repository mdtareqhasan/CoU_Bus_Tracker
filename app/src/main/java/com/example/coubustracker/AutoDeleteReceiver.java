package com.example.coubustracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.database.FirebaseDatabase;

public class AutoDeleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String driverId = intent.getStringExtra("driverId");
        if (driverId != null) {
            FirebaseDatabase.getInstance().getReference("buses")
                    .child(driverId)
                    .removeValue();
        }
    }
}
