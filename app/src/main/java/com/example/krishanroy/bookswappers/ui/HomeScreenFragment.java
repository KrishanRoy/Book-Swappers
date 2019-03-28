package com.example.krishanroy.bookswappers.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.krishanroy.bookswappers.R;
import com.example.krishanroy.bookswappers.ui.controller.BookAdapter;
import com.example.krishanroy.bookswappers.ui.model.Persons;
import com.example.krishanroy.bookswappers.ui.network.PersonService;
import com.example.krishanroy.bookswappers.ui.network.RetrofitSingleton;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeScreenFragment extends Fragment implements SearchView.OnQueryTextListener {
    List<Persons> personsList;
    public static final String TAG = "HomeScreenFragment";
    private BookAdapter bookAdapter;
    SearchView searchView;
    private FragmentCommunication.detailScreen moveToDetailScreenlistener;
    private FragmentCommunication.sendEmail sendEmailListener;
    private FragmentCommunication listener;

    public static HomeScreenFragment newInstance() {
        return new HomeScreenFragment();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentCommunication.detailScreen) {
            moveToDetailScreenlistener = (FragmentCommunication.detailScreen) context;
        } else {
            throw new RuntimeException(context.toString() +
                    "must implement FragmentCommunication.detailScreen");
        }
        if (context instanceof FragmentCommunication.sendEmail) {
            sendEmailListener = (FragmentCommunication.sendEmail) context;
        } else {
            throw new RuntimeException(context.toString() +
                    "must implement FragmentCommunication.sendEmail");
        }
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
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.home_screen_fragment, container, false);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.book_recycler_view);
        searchView = view.findViewById(R.id.home_screen_searchview);
        searchView.setOnQueryTextListener(this);

        bookAdapter = new BookAdapter(new LinkedList<>(), moveToDetailScreenlistener, sendEmailListener);
        recyclerView.setAdapter(bookAdapter);

        RetrofitSingleton
                .getInstance()
                .create(PersonService.class)
                .getPersons()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        persons -> {
                            Log.d(TAG, "onViewCreated: " + persons.get(0).getImage());
                            this.personsList = persons;
                            bookAdapter.setData(personsList, moveToDetailScreenlistener, sendEmailListener);
                            recyclerView.setAdapter(bookAdapter);
                            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                        },
                        throwable -> Log.e(TAG, "onFailure: " + throwable));
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        List<Persons> newPersonsList = new LinkedList<>();
        for (Persons p : personsList) {
            if (p.getAddress().getCity().toLowerCase().startsWith(s.toLowerCase())) {
                newPersonsList.add(p);
            }
        }
        bookAdapter.setData(newPersonsList, moveToDetailScreenlistener, sendEmailListener);
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.links_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_github_link:
                listener.openTheGitHubLink();
            case R.id.menu_linkedin_link:
                listener.openTheLinkedInPage();
        }
        return super.onOptionsItemSelected(item);
    }
}
