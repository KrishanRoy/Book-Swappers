package com.example.krishanroy.bookswappers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.krishanroy.bookswappers.ui.CreateAccountFragment;
import com.example.krishanroy.bookswappers.ui.FragmentCommunication;
import com.example.krishanroy.bookswappers.ui.HomeScreenFragment;
import com.example.krishanroy.bookswappers.ui.SignUpLoginFragment;
import com.example.krishanroy.bookswappers.ui.UserDetailsFragment;

public class MainActivity extends AppCompatActivity implements FragmentCommunication.createAccount,
                                                                FragmentCommunication.homeScreen,
                                                                FragmentCommunication.detailScreen,
                                                                FragmentCommunication.sendEmail{
    public static final String TAG = "MainActivity";

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
                .replace(R.id.fragment_container, signUpLoginFragment)
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
        HomeScreenFragment homeScreenFragment = HomeScreenFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, homeScreenFragment)
                .addToBackStack("homeScreen")
                .commit();
    }

    @Override
    public void moveToUserDetailFragment() {
        UserDetailsFragment userDetailsFragment = UserDetailsFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, userDetailsFragment)
                .addToBackStack("userdetail")
                .commit();
    }

    @Override
    public void sendEmailToTheDonor(String email) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Hi, I am interested in that book");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
