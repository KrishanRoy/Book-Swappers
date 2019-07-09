package com.example.krishanroy.bookswappers.ui;

import android.support.v4.app.Fragment;

import com.example.krishanroy.bookswappers.ui.model.AppUsers;

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

    void moveToTabLayoutUserProfileFragment();

    void moveToTabLayoutUserBooksFragment();

//    void openTheGitHubLink();
//
//    void openTheLinkedInPage();

    void sendEmailToTheDonor(String email);

    void signOutFromTheApp();
}
