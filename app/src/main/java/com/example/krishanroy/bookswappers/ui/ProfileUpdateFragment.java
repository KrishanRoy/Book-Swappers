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
import android.widget.EditText;

import com.example.krishanroy.bookswappers.R;
import com.example.krishanroy.bookswappers.ui.model.AppUsers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jakewharton.rxbinding3.view.RxView;

public class ProfileUpdateFragment extends Fragment {

    private FragmentCommunication listener;
    private EditText updateNameEdText, updateCityEdText, updateStateEdText, updateEmailEdText;
    private Button updateProfileButton;

    private FirebaseUser user;
    private DatabaseReference databaseReference;

    public static ProfileUpdateFragment newInstance() {
        return new ProfileUpdateFragment();
    }

    public ProfileUpdateFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        if (context instanceof FragmentCommunication) {
            listener = (FragmentCommunication) context;
        } else {
            throw new RuntimeException(context.toString() +
                    "must implement FragmentCommunication");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_update_fragment, container, false);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewByIds(view);
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("/appUsers/" + user.getUid());
        if (user != null) {
            databaseReference.addValueEventListener(valueEventListener);
        }
        RxView.clicks(updateProfileButton).subscribe(clicks -> updateProfile());
    }

    private void updateProfile() {

        String name, city, state, email;
        name = updateNameEdText.getText().toString();
        city = updateCityEdText.getText().toString();
        state = updateStateEdText.getText().toString();
        email = updateEmailEdText.getText().toString();
        AppUsers appUsers = new AppUsers(name, city, state, email);
        databaseReference.setValue(appUsers);
        listener.finishFragment(this);
        listener.moveToUserProfileFragment();
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            AppUsers appUsers = dataSnapshot.getValue(AppUsers.class);
            updateNameEdText.setText(appUsers.getName());
            updateCityEdText.setText(appUsers.getCity());
            updateStateEdText.setText(appUsers.getState());
            updateEmailEdText.setText(appUsers.getUserEmail());
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private void findViewByIds(View view) {
        updateNameEdText = view.findViewById(R.id.update_account_name_edittext);
        updateCityEdText = view.findViewById(R.id.update_account_city_edittext);
        updateStateEdText = view.findViewById(R.id.update_account_state_edittext);
        updateEmailEdText = view.findViewById(R.id.update_account_useremail_edittext);
        updateProfileButton = view.findViewById(R.id.update_user_profile_button);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        databaseReference.removeEventListener(valueEventListener);
    }
}