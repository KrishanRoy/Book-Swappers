package com.example.krishanroy.bookswappers.ui.view;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krishanroy.bookswappers.R;
import com.example.krishanroy.bookswappers.ui.model.Persons;
import com.squareup.picasso.Picasso;

public class BookViewHolder extends RecyclerView.ViewHolder {
    private Persons persons;
    public BookViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void onBind(final Persons persons) {
        this.persons = persons;
        TextView bookTitleTextView = itemView.findViewById(R.id.title_textView);
        ImageView bookCoverImageView = itemView.findViewById(R.id.coverpage_imageView);
        TextView locationTextView = itemView.findViewById(R.id.location_textView);
        bookTitleTextView.setText(persons.getTitle());
        Picasso.get().load(persons.getImage()).into(bookCoverImageView);
        locationTextView.setText(persons.getAddress().getCity());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialoguePopUp();
            }
        });

    }

    private void alertDialoguePopUp() {
        View view = LayoutInflater.from(itemView.getContext()).inflate(R.layout.alert_dialogue_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
        TextView alertDonorNameTextView = view.findViewById(R.id.alertd_donor_name_textView);
        TextView alertDonorEmailTextView = view.findViewById(R.id.alertd_email_donor_textView);
        ImageView alertImageView = view.findViewById(R.id.alert_imageView);
        alertDonorNameTextView.setText(persons.getName());
        Picasso.get().load(persons.getImage()).into(alertImageView);
        alertDonorEmailTextView.setText(persons.getEmail());
//        builder.setPositiveButton(persons.getName(), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(itemView.getContext(), "make it work", Toast.LENGTH_SHORT).show();
//            }
//        });
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(itemView.getContext(), "negatibe button", Toast.LENGTH_SHORT).show();
//            }
//        });
        builder.setView(view);
        builder.show();
    }
}
