package com.example.krishanroy.bookswappers.ui.controller;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.krishanroy.bookswappers.R;
import com.example.krishanroy.bookswappers.ui.FragmentCommunication;
import com.example.krishanroy.bookswappers.ui.model.Persons;
import com.example.krishanroy.bookswappers.ui.view.BookViewHolder;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookViewHolder> {
    List<Persons> personsList;
    FragmentCommunication listener;

    public BookAdapter(List<Persons> personsList, FragmentCommunication listener) {
        this.personsList = personsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BookViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.book_itemview, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder bookViewHolder, int position) {
        bookViewHolder.onBind(personsList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return personsList.size();
    }

    public void setData(List<Persons> personsList,
                        final FragmentCommunication listener) {
        this.personsList = personsList;
        this.listener = listener;
        notifyDataSetChanged();
    }
}
