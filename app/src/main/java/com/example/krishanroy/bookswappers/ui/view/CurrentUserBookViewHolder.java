package com.example.krishanroy.bookswappers.ui.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.krishanroy.bookswappers.R;
import com.example.krishanroy.bookswappers.ui.model.Book;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CurrentUserBookViewHolder extends RecyclerView.ViewHolder {
    public CurrentUserBookViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void onBind(Book book) {
        TextView titleTextView = itemView.findViewById(R.id.current_user_title_textView);
        TextView locationTextView = itemView.findViewById(R.id.current_user_location_textView);
        ImageView imageView = itemView.findViewById(R.id.current_user_coverpage_imageView);
        titleTextView.setText(book.getTitle());
        locationTextView.setText(book.getUploaderCity());
        Picasso.get().load(book.getBookImage())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(imageView);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("BookUploaded");
        Query currentUserBookQuery = ref.orderByChild("uploaderEmail").equalTo(book.getTitle());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUserBookQuery.addValueEventListener(bookEventListener);
            }
        });

    }

    ValueEventListener bookEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot child : dataSnapshot.getChildren()) {
                child.getRef().removeValue();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
