package com.example.rider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnContinue;
    ProgressBar progressBar1;
    TextInputLayout layout;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        progressBar1 = findViewById(R.id.progressBar1);


        btnContinue = findViewById(R.id.btnContinue);
        layout = findViewById(R.id.etxt_password);

        btnContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (layout.getEditText().getText().length() == 10){
            btnContinue.setVisibility(View.INVISIBLE);
            progressBar1.setVisibility(View.VISIBLE);
            otpSend();
        }else{
            Toast.makeText(this, "Invalid Phone no.", Toast.LENGTH_SHORT).show();
        }



    }

    private void otpSend() {


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            private PhoneAuthCredential credential;

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                btnContinue.setVisibility(View.VISIBLE);
                progressBar1.setVisibility(View.GONE);
                Toast.makeText(SignUpActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("kkkkkk",e.getLocalizedMessage());

            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {

                Intent intent = new Intent(SignUpActivity.this,VerificationActivity.class);
                intent.putExtra("phone",layout.getEditText().getText().toString().trim());
                intent.putExtra("verificationId",verificationId);
                startActivity(intent);

            }


        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+layout.getEditText().getText().toString().trim())       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}