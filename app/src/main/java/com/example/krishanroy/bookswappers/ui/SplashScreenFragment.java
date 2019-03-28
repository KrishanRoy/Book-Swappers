package com.example.krishanroy.bookswappers.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.krishanroy.bookswappers.R;

public class SplashScreenFragment extends Fragment {
    private FragmentCommunication listener;

    public static SplashScreenFragment newInstance(){
        return new SplashScreenFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof FragmentCommunication){
            listener = (FragmentCommunication) context;
        }else{
            throw new RuntimeException(context.toString() +
                    " must implement FragmentCommunication");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.splash_screen_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        LogoLauncher logoLauncher = new LogoLauncher();
        logoLauncher.start();
    }

    private class LogoLauncher extends Thread {
        public void run() {

            try {
                sleep(1200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            listener.moveToSignUpLoginFragment();
        }
    }

    }

