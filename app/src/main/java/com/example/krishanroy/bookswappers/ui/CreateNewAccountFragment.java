package com.example.krishanroy.bookswappers.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.krishanroy.bookswappers.R;
import com.example.krishanroy.bookswappers.ui.model.AppUsers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.jakewharton.rxbinding3.view.RxView;

public class CreateNewAccountFragment extends Fragment {
    private EditText enterName, enterCity, enterState, enterEmail, enterPassword;
    private Button registerNewUser;
    FirebaseAuth firebaseAuth;
    FragmentCommunication listener;
    private FirebaseDatabase firebaseDatabase;
    //private DatabaseReference appUsersDatabaseReference;
    private String userName, userCity, userState, userEmail, userPassword;


    public static CreateNewAccountFragment newInstance() {
        return new CreateNewAccountFragment();
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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_new_account_fragment, container, false);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewByIds(view);
        registerNewUser = view.findViewById(R.id.register_user_button);
        firebaseAuth = FirebaseAuth.getInstance();
        //firebaseDatabase = FirebaseDatabase.getInstance();
        RxView.clicks(registerNewUser)
                .subscribe(click -> {
                    registerUser();
                });
    }

    private void registerUser() {
        userName = enterName.getText().toString().trim();
        userCity = enterCity.getText().toString().trim();
        userState = enterState.getText().toString().trim();
        userEmail = enterEmail.getText().toString().trim();
        userPassword = enterPassword.getText().toString().trim();
        if (userName.length() > 0
                && userCity.length() > 0
                && userState.length() > 0
                && userEmail.length() > 0
                && userPassword.length() >= 6) {
            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(requireContext(), "Registration Successful!", Toast.LENGTH_SHORT).show();
                            sendVerificationEmailToTheNewUser();
                        } else {
                            Toast.makeText(requireContext(), "Try Again Please!", Toast.LENGTH_SHORT).show();
                        }

                    });
        } else {
            enterEmail.setError("Enter all input to proceed");
        }
    }

    private void sendVerificationEmailToTheNewUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        //appUsersDatabaseReference = firebaseDatabase.getReference(("/appUsers/" + user.getUid()));
        user.sendEmailVerification().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(requireContext(), "Email Verification is Sent! Check Your Email", Toast.LENGTH_SHORT).show();
                firebaseAuth.signOut();
                listener.finishCreateAccountFragment();
                listener.moveToSignUpLoginFragment(
                        new AppUsers(
                                enterName.getText().toString(),
                                enterCity.getText().toString(),
                                enterState.getText().toString(),
                                enterEmail.getText().toString())
                );
            } else {
                Toast.makeText(requireContext(), "verification will be sent soon! check soon!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void findViewByIds(@NonNull View view) {
        enterName = view.findViewById(R.id.create_account_name_edittext);
        enterCity = view.findViewById(R.id.create_account_city_edittext);
        enterState = view.findViewById(R.id.create_account_state_edittext);
        enterEmail = view.findViewById(R.id.create_account_useremail_edittext);
        enterPassword = view.findViewById(R.id.create_account_password_edittext);
    }
}
