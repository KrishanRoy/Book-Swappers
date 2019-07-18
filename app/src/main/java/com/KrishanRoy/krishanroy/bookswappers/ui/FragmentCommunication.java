package com.KrishanRoy.krishanroy.bookswappers.ui;

import android.support.v4.app.Fragment;

public interface FragmentCommunication {
    void moveToSignUpLoginFragment();

    void moveToHomeScreenFragment();

    void moveToCreateNewAccountFragment();

    void moveToUploadNewBookFragment();

    void moveToUserDetailFragment(String name, String city, String email);

    void moveToUserProfileFragment();

    void moveToProfileUpdateFragment();

    void finishFragment(Fragment fragment);

    void dispatchTakePictureIntent();

//    void openTheGitHubLink();
//
//    void openTheLinkedInPage();

    void sendEmailToTheDonor(String email);

    void signOutFromTheApp();
}
