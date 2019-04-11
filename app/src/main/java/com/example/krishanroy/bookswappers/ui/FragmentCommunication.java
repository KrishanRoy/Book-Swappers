package com.example.krishanroy.bookswappers.ui;

import com.example.krishanroy.bookswappers.ui.model.AppUsers;

public interface FragmentCommunication {
    void openTheGitHubLink();

    void openTheLinkedInPage();

    void moveToSignUpLoginFragment(AppUsers appUsers);

    void moveToCreateNewAccountFragment();

    void moveToHomeScreenFragment();

    void moveToUserDetailFragment(String name, String city, String email);

    void sendEmailToTheDonor(String email);

    void moveToTextFragment();

    void dispatchTakePictureIntent();

    void signOutFromTheApp();

    void finishHomeScreenFragment();

    void moveToUserProfileFragment();

    void finishCreateAccountFragment();

    void moveToUploadNewBooksFragment();
    void finishLoginScreenFragment();
}
