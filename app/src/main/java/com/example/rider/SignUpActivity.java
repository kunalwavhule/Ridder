package com.example.rider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnContinue = findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(getApplicationContext(), MapActivity.class);
        startActivity(i);
    }
}