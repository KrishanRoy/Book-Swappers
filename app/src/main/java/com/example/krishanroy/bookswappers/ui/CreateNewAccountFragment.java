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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jakewharton.rxbinding3.view.RxView;

import org.jetbrains.annotations.NotNull;

import io.reactivex.disposables.Disposable;

public class CreateNewAccountFragment extends Fragment {
    EditText enterEmail, enterPassword;
    Button registerNewUser;
    FirebaseAuth firebaseAuth;
    FragmentCommunication listener;

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

    public static CreateNewAccountFragment newInstance() {
        return new CreateNewAccountFragment();
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
        enterEmail = view.findViewById(R.id.create_account_username_edittext);
        enterPassword = view.findViewById(R.id.create_account_password_edittext);
        registerNewUser = view.findViewById(R.id.register_user_button);
        firebaseAuth = FirebaseAuth.getInstance();

        registerNewUser();
    }

    @NotNull
    private Disposable registerNewUser() {
        return RxView.clicks(registerNewUser)
                .subscribe(click -> {
                    String email = enterEmail.getText().toString().trim();
                    String password = enterPassword.getText().toString().trim();
                    if (email.length() > 0 && password.length() >= 6) {
                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(requireContext(), "Registration Successful!", Toast.LENGTH_SHORT).show();
                                        sendVerificationEmailToTheNewUser();
                                        //listener.moveToHomeScreenFragment();
                                    } else {
                                        Toast.makeText(requireContext(), "Try Again Please!", Toast.LENGTH_SHORT).show();
                                    }

                                });
                    } else {
                        enterEmail.setError("Enter input to proceed");
                    }
                });
    }

    private void sendVerificationEmailToTheNewUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(requireContext(), "Email Verification Sent! Check Your Email", Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    listener.finishFragment();
                    listener.moveToSignUpLoginFragment();
                } else {
                    Toast.makeText(requireContext(), "verification couldn't be sent! check soon!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
