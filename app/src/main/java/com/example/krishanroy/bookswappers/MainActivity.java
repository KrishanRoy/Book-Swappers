package com.example.krishanroy.bookswappers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.krishanroy.bookswappers.ui.CreateAccountFragment;
import com.example.krishanroy.bookswappers.ui.FragmentCommunication;
import com.example.krishanroy.bookswappers.ui.HomeScreenFragment;
import com.example.krishanroy.bookswappers.ui.SignUpLoginFragment;
import com.example.krishanroy.bookswappers.ui.SplashScreenFragment;
import com.example.krishanroy.bookswappers.ui.UserDetailsFragment;

public class MainActivity extends AppCompatActivity implements FragmentCommunication {

    private static final String TAG = "Main Activity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initiateSplashScreen();

    }

    private void initiateSplashScreen() {
        SplashScreenFragment splashScreenFragment = SplashScreenFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, splashScreenFragment)
                .commit();
    }

    @Override
    public void moveToSignUpLoginFragment() {
        SignUpLoginFragment signUpLoginFragment = SignUpLoginFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, signUpLoginFragment)
                .commit();
    }

    @Override
    public void moveToCreateAccountFragment() {
        CreateAccountFragment createAccountFragment = CreateAccountFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, createAccountFragment)
                .addToBackStack(getString(R.string.create_account_add_to_backStack))
                .commit();
    }

    @Override
    public void moveToHomeScreenFragment() {
        HomeScreenFragment homeScreenFragment = HomeScreenFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, homeScreenFragment)
                .addToBackStack(getString(R.string.homescreen_add_toBackstack))
                .commit();
    }

    @Override
    public void openTheGitHubLink() {
        Intent githubIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.githublink_of_the_currentproject)));
        if(githubIntent.resolveActivity(getPackageManager()) != null){
            startActivity(githubIntent);
        }else{
            Log.d(TAG, "openTheGitHubLink: " + "Cannot handle this link");
        }
    }

    @Override
    public void openTheLinkedInPage() {
        Intent linkedInIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.linkedIn_link)));
        if(linkedInIntent.resolveActivity(getPackageManager()) != null){
            startActivity(linkedInIntent);
        }else{
            Log.d(TAG, "openTheGitHubLink: " + "Cannot handle this link");
        }
    }

    @Override
    public void moveToUserDetailFragment(String name, String city, String email) {
        UserDetailsFragment userDetailsFragment = UserDetailsFragment.newInstance(name, city, email);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, userDetailsFragment)
                .addToBackStack(getString(R.string.user_detail_add_to_backstack))
                .commit();
    }

    @Override
    public void sendEmailToTheDonor(String email) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, R.string.email_toDonor_text);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
