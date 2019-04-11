package com.example.krishanroy.bookswappers.ui.controller;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.krishanroy.bookswappers.R;
import com.example.krishanroy.bookswappers.ui.FragmentCommunication;
import com.example.krishanroy.bookswappers.ui.model.Book;
import com.example.krishanroy.bookswappers.ui.view.BookViewHolder;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookViewHolder> {
    List<Book> booksList;
    FragmentCommunication listener;

    public BookAdapter(List<Book> booksList, FragmentCommunication listener) {
        this.booksList = booksList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BookViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.book_itemview, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder bookViewHolder, int position) {
        bookViewHolder.onBind(booksList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public void setData(List<Book> booksList,
                        final FragmentCommunication listener) {
        this.booksList = booksList;
        this.listener = listener;
        notifyDataSetChanged();
    }
}
