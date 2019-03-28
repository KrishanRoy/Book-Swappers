package com.example.krishanroy.bookswappers.ui;

public interface FragmentCommunication {
    interface createAccount{
        void moveToCreateAccountActivity();
    }
    interface homeScreen{
        void moveToHomeScreenFragment();
    }
    interface detailScreen{
        void moveToUserDetailFragment();
    }

}
