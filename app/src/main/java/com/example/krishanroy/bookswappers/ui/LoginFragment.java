package com.example.krishanroy.bookswappers.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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

public class LoginFragment extends Fragment {
    private FragmentCommunication listener;
    Button signUpButton, loginButton;
    EditText emailEditText, passwordEditText;
    private ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    public static LoginFragment newInstance() {
        return new LoginFragment();
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
        return inflater.inflate(R.layout.login_screen_fragment, container, false);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewByIds(view);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        progressDialog = new ProgressDialog(requireContext());

        if (user != null) {
            listener.finishFragment();
            listener.moveToHomeScreenFragment();
        }

        RxView.clicks(signUpButton)
                .subscribe(clicks -> listener.moveToCreateNewAccountFragment());
        authenticateUserAndLogin();
    }

    private void findViewByIds(@NonNull View view) {
        signUpButton = view.findViewById(R.id.sign_up_button);
        loginButton = view.findViewById(R.id.log_in_button);
        emailEditText = view.findViewById(R.id.login_email_edittext);
        passwordEditText = view.findViewById(R.id.login_password_edittext);
    }

    private void checkIfEmailVerificationIsDone() {
        user = firebaseAuth.getCurrentUser();
        if (user.isEmailVerified()) {
            listener.finishFragment();
            listener.moveToHomeScreenFragment();
        } else {
            Toast.makeText(requireContext(), "Verify your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

    @NotNull
    private Disposable authenticateUserAndLogin() {
        return RxView.clicks(loginButton)
                .subscribe(clicks -> {
                    String email = emailEditText.getText().toString();
                    String password = passwordEditText.getText().toString();
                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(requireContext(), getString(R.string.login_frag_enter_email_toast), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(requireContext(), getString(R.string.login_frag_enter_password_toast), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    progressDialog.show();
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            //checkIfEmailVerificationIsDone();
                            listener.moveToHomeScreenFragment();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(requireContext(), getString(R.string.login_fragment_failed_toast_text), Toast.LENGTH_SHORT).show();
                        }
                    });
                });
    }

}
