package com.sirt.phidaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.hbb20.CountryCodePicker;

public class EnterMobileNumber extends AppCompatActivity {

    EditText login_mobilenumber;
    Button send_otp_btn;
    CountryCodePicker countryCodePicker;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_mobile_number);

        login_mobilenumber = findViewById(R.id.login_mobilenumber);
        send_otp_btn = findViewById(R.id.send_otp_btn);
        progressBar = findViewById(R.id.login_progress_bar);
        countryCodePicker= findViewById(R.id.login_countrycode);
        progressBar.setVisibility(View.GONE);
        countryCodePicker.registerCarrierNumberEditText(login_mobilenumber);

        send_otp_btn.setOnClickListener(view -> {
            if(!countryCodePicker.isValidFullNumber()){
                login_mobilenumber.setError("Phone number is invalid");
                return;
            }
            Intent intent = new Intent(EnterMobileNumber.this, VerifyActivity.class);
            intent.putExtra("phone",countryCodePicker.getFullNumberWithPlus());
            startActivity(intent);

        });



    }
}