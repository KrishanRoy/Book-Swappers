package com.example.krishanroy.bookswappers.ui;

public interface FragmentCommunication {
    void openTheGitHubLink();

    void openTheLinkedInPage();

    void moveToSignUpLoginFragment();

    void moveToCreateNewAccountFragment();

    void moveToHomeScreenFragment();

    void moveToUserDetailFragment(String name, String city, String email);

    void sendEmailToTheDonor(String email);

    void moveToTextFragment();

    void dispatchTakePictureIntent();

    void signOutFromTheApp();

    void finishFragment();
}
