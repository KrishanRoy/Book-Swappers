package com.example.krishanroy.bookswappers.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.krishanroy.bookswappers.R;
import com.jakewharton.rxbinding3.view.RxView;

public class SignUpLoginFragment extends Fragment {
    private FragmentCommunication listener;

    public static SignUpLoginFragment newInstance(){
        return new SignUpLoginFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof FragmentCommunication){
            listener = (FragmentCommunication) context;
        }else {
            throw new RuntimeException(context.toString() +
                    "must implement FragmentCommunication");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_screen_fragment, container, false);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        Button signUpButton = view.findViewById(R.id.sign_up_button);
        Button logInButton = view.findViewById(R.id.log_in_button);
        RxView.clicks(signUpButton)
                .subscribe(clicks -> listener.moveToCreateAccountFragment());
        RxView.clicks(logInButton)
                .subscribe(clicks -> listener.moveToHomeScreenFragment());
    }

}
