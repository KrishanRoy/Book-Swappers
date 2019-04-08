package com.example.krishanroy.bookswappers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.krishanroy.bookswappers.ui.CreateNewAccountFragment;
import com.example.krishanroy.bookswappers.ui.FragmentCommunication;
import com.example.krishanroy.bookswappers.ui.HomeScreenFragment;
import com.example.krishanroy.bookswappers.ui.LoginFragment;
import com.example.krishanroy.bookswappers.ui.SplashScreenFragment;
import com.example.krishanroy.bookswappers.ui.TextSendDisplayFragment;
import com.example.krishanroy.bookswappers.ui.UserDetailsFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements FragmentCommunication {

    private static final String TAG = "Main Activity";
    public static final int RC_SIGN_IN = 1;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference messagesDatabaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private String editTextText;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imageView;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private String currentPhotoPath;

    private static final String BACK_STACK_ROOT_TAG = "root_fragment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initiateSplashScreen();
        firebaseDatabase = FirebaseDatabase.getInstance();
        messagesDatabaseReference = firebaseDatabase.getReference().child("messages");
        firebaseAuth = FirebaseAuth.getInstance();
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
        LoginFragment loginFragment = LoginFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, loginFragment)
                .commit();
    }

    @Override
    public void moveToCreateNewAccountFragment() {
        CreateNewAccountFragment createNewAccountFragment = CreateNewAccountFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, createNewAccountFragment)
                .addToBackStack(getString(R.string.create_account_add_to_backStack))
                .commit();
    }

    @Override
    public void moveToHomeScreenFragment() {
        HomeScreenFragment homeScreenFragment = HomeScreenFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, homeScreenFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void openTheGitHubLink() {
        Intent githubIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.githublink_of_the_currentproject)));
        if (githubIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(githubIntent);
        }
    }

    @Override
    public void openTheLinkedInPage() {
        Intent linkedInIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.linkedIn_link)));
        if (linkedInIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(linkedInIntent);
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


    @Override
    public void moveToTextFragment() {
        TextSendDisplayFragment textSendDisplayFragment = TextSendDisplayFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, textSendDisplayFragment)
                .addToBackStack("sendtext")
                .commit();
    }

    @Override
    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    public void signOutFromTheApp() {
        FirebaseAuth.getInstance().signOut();
        moveToSignUpLoginFragment();
    }

    @Override
    public void finishFragment() {
        getSupportFragmentManager().beginTransaction().remove(HomeScreenFragment.newInstance()).commit();
    }

    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

}
