package com.example.krishanroy.bookswappers.ui;

import android.support.v4.app.Fragment;

import com.example.krishanroy.bookswappers.ui.model.AppUsers;

public interface FragmentCommunication {
    void openTheGitHubLink();

    void openTheLinkedInPage();

    void moveToSignUpLoginFragment(AppUsers appUsers);

    void sendEmailToTheDonor(String email);

    void dispatchTakePictureIntent();

    void signOutFromTheApp();

    void finishFragment(Fragment fragment);

    void navigateTo(Fragment fragment);

}
