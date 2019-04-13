package com.example.krishanroy.bookswappers.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.krishanroy.bookswappers.R;
import com.example.krishanroy.bookswappers.ui.controller.CurrentUserBookAdapter;
import com.example.krishanroy.bookswappers.ui.model.AppUsers;
import com.example.krishanroy.bookswappers.ui.model.Book;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UserProfileFragment extends Fragment {
    private FragmentCommunication listener;
    private TextView userNameTextview, userCityTextview, userStateTextview, userEmailTextview;
    private FloatingActionButton editFab;
    private Button backToHomeScreenButton;
    private DatabaseReference userProfileDatabaseRef;
    private FirebaseUser user;
    public static final String TAG = "UserProfileFragment";
    private CurrentUserBookAdapter bookAdapter;
    List<Book> bookList = new ArrayList<>();
    DatabaseReference ref;

    public static UserProfileFragment newInstance() {
        return new UserProfileFragment();
    }

    public UserProfileFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
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
        getCurrentUserInfo();
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
        return inflater.inflate(R.layout.user_profile_fragment, container, false);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewByIds(view);
        RecyclerView recyclerView = view.findViewById(R.id.user_profile_book_recycler_view);
        this.bookAdapter = new CurrentUserBookAdapter(new LinkedList<>());
        recyclerView.setAdapter(bookAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        ref = FirebaseDatabase.getInstance().getReference("BookUploaded");
        Query currentUserBookQuery = ref.orderByChild("uploaderEmail").equalTo(user.getEmail());
        currentUserBookQuery.addValueEventListener(bookEventListener);
        RxView.clicks(editFab).subscribe(clicks -> listener.moveToProfileUpdateFragment());
        RxView.clicks(backToHomeScreenButton).subscribe(clicks -> listener.moveToHomeScreenFragment());
    }

    ValueEventListener bookEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                Book book = ds.getValue(Book.class);
                bookList.add(book);
            }
            bookAdapter.setData(bookList);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "onCancelled: ");
        }
    };

    private void findViewByIds(@NonNull View view) {
        userNameTextview = view.findViewById(R.id.user_profile_name);
        userCityTextview = view.findViewById(R.id.user_profile_city);
        userStateTextview = view.findViewById(R.id.user_profile_state);
        userEmailTextview = view.findViewById(R.id.user_profile_email);
        editFab = view.findViewById(R.id.edit_profile_fab);
        backToHomeScreenButton = view.findViewById(R.id.user_profile_back_to_home_button);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        ref.removeEventListener(bookEventListener);
    }
}
