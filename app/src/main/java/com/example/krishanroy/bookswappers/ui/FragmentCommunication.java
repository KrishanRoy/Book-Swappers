package com.example.krishanroy.bookswappers.ui;

public interface FragmentCommunication {
    void openTheGitHubLink();
    void openTheLinkedInPage();
    interface loginPage{
        void moveToSignUpLoginFragment();
    }
    interface createAccount{
        void moveToCreateAccountFragment();
    }
    interface homeScreen{
        void moveToHomeScreenFragment();

    }
    interface detailScreen{
        void moveToUserDetailFragment();
    }
    interface sendEmail{
        void sendEmailToTheDonor(String email);
    }

}
