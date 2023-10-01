package com.sirt.phidaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafeBrowsingThreat;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.safetynet.SafetyNetClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class PhishingActivity extends AppCompatActivity {

    private EditText url_input;
    private Button url_check_btn;
    private TextView result_textView;
    private static final String SAFE_BROWSING_API_KEY = "AIzaSyAvWrb8B19iTzLDAARneFoiDnsngNiZqr4";
    SafetyNetClient safetyNetClient;
    private String TAG = "PhishingActivity";
    private TextView chat_textView;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phishing);

        url_input = findViewById(R.id.url_input);
        url_check_btn = findViewById(R.id.url_check_btn);
        result_textView = findViewById(R.id.result_textView);
        SafetyNet.getClient(this).initSafeBrowsing();
        chat_textView = findViewById(R.id.chat_textView);

        chat_textView.setOnClickListener(view -> {
            Intent intent = new Intent(PhishingActivity.this, ChatActivity.class);
            startActivity(intent);

        });

        url_check_btn.setOnClickListener(view -> {

            String url = url_input.getText().toString();


            SafetyNet.getClient(this).lookupUri(url,
                            SAFE_BROWSING_API_KEY,
                            SafeBrowsingThreat.TYPE_POTENTIALLY_HARMFUL_APPLICATION,
                            SafeBrowsingThreat.TYPE_SOCIAL_ENGINEERING)
                    .addOnSuccessListener(this,
                            new OnSuccessListener<SafetyNetApi.SafeBrowsingResponse>() {
                                @Override
                                public void onSuccess(SafetyNetApi.SafeBrowsingResponse sbResponse) {

                                    if (sbResponse.getDetectedThreats().isEmpty()) {
                                       result_textView.setText("This URL is safe");
                                    } else {
                                       result_textView.setText("This URL is unsafe");
                                    }
                                }
                            })
                    .addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            if (e instanceof ApiException) {

                                ApiException apiException = (ApiException) e;
                                Log.d(TAG, "Error: " + CommonStatusCodes
                                        .getStatusCodeString(apiException.getStatusCode()));


                            } else {

                                Log.d(TAG, "Error: " + e.getMessage());
                            }
                        }
                    });

        });
    }
}