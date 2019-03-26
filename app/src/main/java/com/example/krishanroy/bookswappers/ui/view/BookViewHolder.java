package com.example.krishanroy.bookswappers.ui.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.krishanroy.bookswappers.R;
import com.example.krishanroy.bookswappers.ui.model.Persons;

public class BookViewHolder extends RecyclerView.ViewHolder {
    public BookViewHolder(@NonNull View itemView) {
        super(itemView);
    }
    public void onBind(Persons persons){
        TextView bookTitleTextView = itemView.findViewById(R.id.book_title_textview);
        ImageView bookCoverImageView = itemView.findViewById(R.id.book_cover_imageview);
        TextView locationTextView = itemView.findViewById(R.id.location_textview);
        bookTitleTextView.setText(persons.);

    }
}
