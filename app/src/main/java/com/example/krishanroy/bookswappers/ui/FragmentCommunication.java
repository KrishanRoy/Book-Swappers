package com.example.krishanroy.bookswappers.ui;

public interface FragmentCommunication {
    void openTheGitHubLink();
    void openTheLinkedInPage();
    void moveToSignUpLoginFragment();
    void moveToCreateAccountFragment();
    void moveToHomeScreenFragment();
    void moveToUserDetailFragment(String name, String city, String email);
    void sendEmailToTheDonor(String email);

}
