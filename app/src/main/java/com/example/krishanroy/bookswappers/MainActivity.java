package com.example.krishanroy.bookswappers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.krishanroy.bookswappers.ui.CreateNewAccountFragment;
import com.example.krishanroy.bookswappers.ui.FragmentCommunication;
import com.example.krishanroy.bookswappers.ui.HomeScreenFragment;
import com.example.krishanroy.bookswappers.ui.LoginFragment;
import com.example.krishanroy.bookswappers.ui.ProfileUpdateFragment;
import com.example.krishanroy.bookswappers.ui.SplashScreenFragment;
import com.example.krishanroy.bookswappers.ui.UploadNewBooksFragment;
import com.example.krishanroy.bookswappers.ui.UserDetailsFragment;
import com.example.krishanroy.bookswappers.ui.UserProfileFragment;
import com.example.krishanroy.bookswappers.ui.model.AppUsers;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements FragmentCommunication {

    private static final String TAG = "Main Activity";
    public static final int RC_SIGN_IN = 1;
    private static final String PROFILE_UPDATE_FRAGMENT_KEY = "user profile update";
    private static final String USER_PROFILE_FRAGMENT_KEY = "user profile";
    private static final String USER_DETAIL_FRAGMENT_KEY = "user detail fragment";
    private static final String UPLOAD_NEW_BOOK_FRAGMENT_KEY = "upload new book fragment";
    private static final String CREATE_NEW_AC_FRAGMENT_KEY = "create new accoutn fragment";
    private static final String HOME_SCREEN_FRAGMENT_KEY = "home screen fragment";
    private String editTextText;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imageView;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private String currentPhotoPath;

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
        LoginFragment fragment = LoginFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void moveToHomeScreenFragment() {
        HomeScreenFragment fragment = HomeScreenFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(HOME_SCREEN_FRAGMENT_KEY)
                .commit();
    }

    @Override
    public void moveToCreateNewAccountFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, CreateNewAccountFragment.newInstance())
                .addToBackStack(CREATE_NEW_AC_FRAGMENT_KEY)
                .commit();
    }

    @Override
    public void moveToUploadNewBookFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, UploadNewBooksFragment.newInstance())
                .addToBackStack(UPLOAD_NEW_BOOK_FRAGMENT_KEY)
                .commit();
    }

    @Override
    public void moveToUserDetailFragment(String name, String city, String email) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, UserDetailsFragment.newInstance(name, city, email))
                .addToBackStack(USER_DETAIL_FRAGMENT_KEY)
                .commit();
    }

    @Override
    public void moveToUserProfileFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, UserProfileFragment.newInstance())
                .addToBackStack(USER_PROFILE_FRAGMENT_KEY)
                .commit();
    }

    @Override
    public void moveToProfileUpdateFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, ProfileUpdateFragment.newInstance())
                .addToBackStack(PROFILE_UPDATE_FRAGMENT_KEY)
                .commit();
    }

    @Override
    public void finishFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .remove(fragment)
                .commit();
    }


    @Override
    public void signOutFromTheApp() {
        FirebaseAuth.getInstance().signOut();
        moveToSignUpLoginFragment();
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
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
