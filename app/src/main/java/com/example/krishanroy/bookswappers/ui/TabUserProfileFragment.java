package com.example.krishanroy.bookswappers.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.krishanroy.bookswappers.R;
import com.example.krishanroy.bookswappers.ui.model.AppUsers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jakewharton.rxbinding3.view.RxView;

public class TabUserProfileFragment extends Fragment {
    private TextView userNameTextview, userCityTextview, userStateTextview, userEmailTextview;
    private DatabaseReference userProfileDatabaseRef;
    private FirebaseUser user;
    private FloatingActionButton editFab;
    private FragmentCommunication listener;

    public static TabUserProfileFragment newInstance() {
        return new TabUserProfileFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCurrentUserInfo();
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

    private void getCurrentUserInfo() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        userProfileDatabaseRef = FirebaseDatabase.getInstance().getReference().child("/appUsers/" + user.getUid());
        if (user != null) {
            userProfileDatabaseRef.addValueEventListener(valueEventListener);
        }
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            AppUsers appUsers = dataSnapshot.getValue(AppUsers.class);
            String uploaderName = "Name: " + appUsers.getName();
            String uploaderCity = "City: " + appUsers.getCity();
            String uploaderState = "State: " + appUsers.getState();
            String uploaderEmail = "Email: " + appUsers.getUserEmail();

            userNameTextview.setText(uploaderName);
            userCityTextview.setText(uploaderCity);
            userStateTextview.setText(uploaderState);
            userEmailTextview.setText(uploaderEmail);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_layout_user_profile_fragment, container, false);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewByIds(view);

        RxView.clicks(editFab).subscribe(clicks -> listener.moveToProfileUpdateFragment());
    }

    private void findViewByIds(@NonNull View view) {
        userNameTextview = view.findViewById(R.id.user_profile_name);
        userCityTextview = view.findViewById(R.id.user_profile_city);
        userStateTextview = view.findViewById(R.id.user_profile_state);
        userEmailTextview = view.findViewById(R.id.user_profile_email);
        editFab = view.findViewById(R.id.edit_profile_fab);

    }
}
