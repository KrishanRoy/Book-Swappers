package com.example.krishanroy.bookswappers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.krishanroy.bookswappers.ui.CreateAccountFragment;
import com.example.krishanroy.bookswappers.ui.FragmentCommunication;
import com.example.krishanroy.bookswappers.ui.SignUpLoginFragment;

public class MainActivity extends AppCompatActivity implements FragmentCommunication.createAccount, FragmentCommunication.homeScreen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initiateSignUpLoginFragment();

    }

    private void initiateSignUpLoginFragment() {
        SignUpLoginFragment signUpLoginFragment = SignUpLoginFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,signUpLoginFragment)
                .addToBackStack("signUpLogin")
                .commit();
    }

    @Override
    public void moveToCreateAccountActivity() {
        CreateAccountFragment createAccountFragment = CreateAccountFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, createAccountFragment)
                .addToBackStack("createAccount")
                .commit();
    }

    @Override
    public void moveToHomeScreenFragment() {
        CreateAccountFragment createAccountFragment = CreateAccountFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, createAccountFragment)
                .addToBackStack("createAccount")
                .commit();
    }
}
