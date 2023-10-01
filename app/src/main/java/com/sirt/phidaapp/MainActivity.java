package com.sirt.phidaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        textView.animate().translationX(800).setDuration(800).setStartDelay(1200);
        Thread thread = new Thread(){
            @Override
            public void run() {
               try {
                   Thread.sleep(2000);
               }
               catch (Exception e){
                   e.printStackTrace();
               }
               finally {
                   Intent intent = new Intent(MainActivity.this, EnterMobileNumber.class);
                   startActivity(intent);
                   finish();
               }
            }
        };
        thread.start();

    }
}