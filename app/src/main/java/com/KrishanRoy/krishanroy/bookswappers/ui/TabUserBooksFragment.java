package com.KrishanRoy.krishanroy.bookswappers.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.KrishanRoy.krishanroy.bookswappers.R;
import com.KrishanRoy.krishanroy.bookswappers.ui.controller.CurrentUserBookAdapter;
import com.KrishanRoy.krishanroy.bookswappers.ui.model.Book;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class TabUserBooksFragment extends Fragment {
    private DatabaseReference ref;
    private FirebaseUser user;
    public static final String TAG = "UserProfileFragment";
    private CurrentUserBookAdapter bookAdapter;
    private List<Book> bookList = new ArrayList<>();

    public static TabUserBooksFragment newInstance() {
        return new TabUserBooksFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_layout_user_books_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.tab_layout_user_profile_book_recycler_view);
        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference("BookUploaded");
        Query currentUserBookQuery = ref.orderByChild("uploaderEmail").equalTo(user.getEmail());
        currentUserBookQuery.addValueEventListener(bookEventListener);
        bookAdapter = new CurrentUserBookAdapter(new LinkedList<>());
        recyclerView.setAdapter(bookAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
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

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        ref.removeEventListener(bookEventListener);
//    }
}
