package com.KrishanRoy.krishanroy.bookswappers.ui.controller;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.KrishanRoy.krishanroy.bookswappers.R;
import com.KrishanRoy.krishanroy.bookswappers.ui.model.Book;
import com.KrishanRoy.krishanroy.bookswappers.ui.view.CurrentUserBookViewHolder;

import java.util.List;

public class CurrentUserBookAdapter extends RecyclerView.Adapter<CurrentUserBookViewHolder> {

    private List<Book> bookList;

    public CurrentUserBookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public CurrentUserBookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CurrentUserBookViewHolder(LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.current_user_book_itemview, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentUserBookViewHolder currentUserBookViewHolder, int i) {
        currentUserBookViewHolder.onBind(bookList.get(i));

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public void setData(List<Book> bookList) {
        this.bookList = bookList;
        notifyDataSetChanged();
    }

    public void deleteItem(int index) {
        bookList.remove(index);
        notifyItemRemoved(index);
    }
}
