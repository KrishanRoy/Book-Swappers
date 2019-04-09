package com.example.krishanroy.bookswappers.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.krishanroy.bookswappers.R;
import com.jakewharton.rxbinding3.view.RxView;

public class UserDetailsFragment extends Fragment {
    public static final String NAME_KEY = "name key";
    public static final String CITY_KEY = "city key";
    public static final String EMAIL_KEY = "email key";
    public static final String TAG = "UserDetailsFragment";
    private FragmentCommunication listener;

    private String donorName;
    private String donorCity;
    private String donorEmail;

    public static UserDetailsFragment newInstance(String name, String city, String email) {
        UserDetailsFragment userDetailsFragment = new UserDetailsFragment();
        Bundle args = new Bundle();
        args.putString(NAME_KEY, name);
        args.putString(CITY_KEY, city);
        args.putString(EMAIL_KEY, email);
        userDetailsFragment.setArguments(args);
        return userDetailsFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentCommunication) {
            listener = (FragmentCommunication) context;
        } else {
            throw new RuntimeException(context.toString() +
                    "must implement FragmentCommunication");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            donorName = getArguments().getString(NAME_KEY);
            donorCity = getArguments().getString(CITY_KEY);
            donorEmail = getArguments().getString(EMAIL_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_details_fragment, container, false);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView nameTextView = view.findViewById(R.id.detail_fragment_name_textView);
        TextView cityTextView = view.findViewById(R.id.detail_fragment_city_textView);
        Button emailButton = view.findViewById(R.id.detail_fragment_email_button);
        Button sendTextButton = view.findViewById(R.id.detail_fragment_send_text_button);

        nameTextView.setText(donorName);
        Log.d(TAG, "onViewCreated: " + donorName);
        cityTextView.setText(donorCity);
        RxView.clicks(emailButton)
                .subscribe(click -> listener.sendEmailToTheDonor(donorEmail));
        RxView.clicks(sendTextButton)
                .subscribe(click -> listener.moveToTextFragment());
    }
}
