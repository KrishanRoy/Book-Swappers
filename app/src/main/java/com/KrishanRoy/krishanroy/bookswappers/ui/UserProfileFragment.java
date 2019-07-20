package com.KrishanRoy.krishanroy.bookswappers.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.KrishanRoy.krishanroy.bookswappers.R;
import com.KrishanRoy.krishanroy.bookswappers.ui.controller.CurrentUserBookAdapter;
import com.KrishanRoy.krishanroy.bookswappers.ui.controller.ProfileAdapter;
import com.KrishanRoy.krishanroy.bookswappers.ui.model.Book;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
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
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static UserProfileFragment newInstance() {
        return new UserProfileFragment();
    }

    public UserProfileFragment() {
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
        return inflater.inflate(R.layout.user_profile_fragment, container, false);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewByIds(view);
        ref = FirebaseDatabase.getInstance().getReference("BookUploaded");
        tabLayout.addTab(tabLayout.newTab().setText("user profile"));
        tabLayout.addTab(tabLayout.newTab().setText("your books"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ProfileAdapter profileAdapter = new ProfileAdapter(requireContext(), getChildFragmentManager(), 2);
        viewPager.setAdapter(profileAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void findViewByIds(View view) {
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.user_profile_viewpager);
    }
}
