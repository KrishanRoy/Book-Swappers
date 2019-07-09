package com.example.krishanroy.bookswappers.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.krishanroy.bookswappers.R;


public class TabUserBooksFragment extends Fragment {
    public static TabUserBooksFragment newInstance(){
        return new TabUserBooksFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_layout_user_books_fragment, container, false);
    }
}
