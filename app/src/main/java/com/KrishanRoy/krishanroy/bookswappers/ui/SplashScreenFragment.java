package com.KrishanRoy.krishanroy.bookswappers.ui;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.KrishanRoy.krishanroy.bookswappers.R;

import java.util.Objects;

public class SplashScreenFragment extends Fragment {
    private FragmentCommunication listener;
    private long totalTime = 4000;
    Thread splashTread;
    AnimationDrawable splashAnimation;

    public static SplashScreenFragment newInstance() {
        return new SplashScreenFragment();
    }

    public SplashScreenFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentCommunication) {
            listener = (FragmentCommunication) context;
        } else {
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
        ImageView animationImageView = view.findViewById(R.id.splash_page_animation_imageView);
        showAnimation(animationImageView);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();
        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(totalTime);
                    }
                } catch (InterruptedException ignored) {
                } finally {
                    listener.moveToSignUpLoginFragment();
                }
            }
        };
        splashTread.start();
    }

    private void showAnimation(ImageView animationImageView) {
        animationImageView.setBackgroundResource(R.drawable.splash_screen_animation);
        splashAnimation = (AnimationDrawable) animationImageView.getBackground();
        splashAnimation.start();
    }
}

