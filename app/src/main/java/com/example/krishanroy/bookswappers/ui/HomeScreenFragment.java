package com.example.krishanroy.bookswappers.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.krishanroy.bookswappers.R;
import com.example.krishanroy.bookswappers.ui.network.PersonService;
import com.example.krishanroy.bookswappers.ui.network.RetrofitSingleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeScreenFragment extends Fragment {
    public static final String TAG = "HomeScreenFragment";
    public static HomeScreenFragment newInstance() {
        return new HomeScreenFragment();
    }

    @SuppressLint("CheckResult")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RetrofitSingleton
                .getInstance()
                .create(PersonService.class)
                .getPersons()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        persons -> Log.d(TAG, "onSuccess: " + persons.get(0).getName()),
                        throwable -> Log.e(TAG, "onFailure: " + throwable));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_screen_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
