package com.example.coubustracker;


import android.content.Intent;
import android.os.Bundle;
import androidx.core.graphics.Insets;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        Thread thread= new Thread(){
          @Override
          public void run(){
              try {
                  sleep(3000);
              }
              catch (Exception exception){
                  exception.printStackTrace();
              }
              finally {
                  Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                  startActivity(intent);
                  finish();
              }
          }
        }; thread.start();
    }
}