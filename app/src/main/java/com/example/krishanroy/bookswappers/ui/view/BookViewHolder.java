package com.example.krishanroy.bookswappers.ui.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.krishanroy.bookswappers.R;
import com.example.krishanroy.bookswappers.ui.model.Persons;
import com.squareup.picasso.Picasso;

public class BookViewHolder extends RecyclerView.ViewHolder {
    public BookViewHolder(@NonNull View itemView) {
        super(itemView);
    }
    public void onBind(Persons persons){
        TextView bookTitleTextView = itemView.findViewById(R.id.title_textView);
        ImageView bookCoverImageView = itemView.findViewById(R.id.coverpage_imageView);
        TextView locationTextView = itemView.findViewById(R.id.location_textView);
        bookTitleTextView.setText(persons.getTitle());
        Picasso.get().load(persons.getImage()).into(bookCoverImageView);
        locationTextView.setText(persons.getAddress().getCity());

    }
}
