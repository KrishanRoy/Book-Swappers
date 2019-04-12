package com.example.krishanroy.bookswappers.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jakewharton.rxbinding3.view.RxView;

public class LoginFragment extends Fragment {
    private static final String USER_INFO = "user info";
    public static final String TAG = "LoginFragment";
    private FragmentCommunication listener;
    Button signUpButton, loginButton;
    EditText emailEditText, passwordEditText;
    private ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference appUsersDatabaseReference;


    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    public LoginFragment() {
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
        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            listener.finishFragment(this);
            listener.navigateTo(HomeScreenFragment.newInstance());
        }

        RxView.clicks(signUpButton).subscribe(clicks -> listener.navigateTo(CreateNewAccountFragment.newInstance()));
        RxView.clicks(loginButton).subscribe(clicks -> authenticateAndLogin());

    }

    private void authenticateAndLogin() {
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
            this.user = firebaseAuth.getCurrentUser();
            if (task.isSuccessful()) {
                progressDialog.dismiss();
                emailVerificationCheckAndPermitLogin();
            } else {
                progressDialog.dismiss();
                Toast.makeText(requireContext(), getString(R.string.login_fragment_failed_toast_text), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findViewByIds(@NonNull View view) {
        signUpButton = view.findViewById(R.id.sign_up_button);
        loginButton = view.findViewById(R.id.log_in_button);
        emailEditText = view.findViewById(R.id.login_email_edittext);
        passwordEditText = view.findViewById(R.id.login_password_edittext);
    }

    private void emailVerificationCheckAndPermitLogin() {
        if (user != null) {
            if (user.isEmailVerified()) {
                listener.navigateTo(HomeScreenFragment.newInstance());
            } else {
                Toast.makeText(requireContext(), "Verify your email", Toast.LENGTH_SHORT).show();
                firebaseAuth.signOut();
            }
        }
    }
}
