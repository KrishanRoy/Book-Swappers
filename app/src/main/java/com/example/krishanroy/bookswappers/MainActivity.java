package com.example.krishanroy.bookswappers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.krishanroy.bookswappers.signup.CreateAccountActivity;
import com.jakewharton.rxbinding3.view.RxView;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signUpButton = findViewById(R.id.sign_up_button);
        RxView.clicks(signUpButton)
                .subscribe(clicks -> moveToCreateAccountActivity());
    }

    private void moveToCreateAccountActivity() {
        startActivity(new Intent(getApplicationContext(), CreateAccountActivity.class));
    }
}
