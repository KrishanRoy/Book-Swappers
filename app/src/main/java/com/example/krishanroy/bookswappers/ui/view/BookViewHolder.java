package com.example.krishanroy.bookswappers.ui.view;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.krishanroy.bookswappers.R;
import com.example.krishanroy.bookswappers.ui.FragmentCommunication;
import com.example.krishanroy.bookswappers.ui.model.Persons;
import com.jakewharton.rxbinding3.view.RxView;
import com.squareup.picasso.Picasso;

public class BookViewHolder extends RecyclerView.ViewHolder {
    private FragmentCommunication listener;
    private Persons persons;
    private String email;
    private View view;

    public BookViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;

    }

    @SuppressLint("CheckResult")
    public void onBind( final Persons persons,
                       final FragmentCommunication listener) {
        this.persons = persons;
        this.listener = listener;
        this.email = persons.getEmail();

        final TextView bookTitleTextView = itemView.findViewById(R.id.title_textView);
        final ImageView bookCoverImageView = itemView.findViewById(R.id.coverpage_imageView);
        final TextView locationTextView = itemView.findViewById(R.id.location_textView);

        bookTitleTextView.setText(persons.getTitle());
        Picasso.get().load(persons.getImage()).into(bookCoverImageView);
        locationTextView.setText(persons.getAddress().getCity());
        RxView.clicks(itemView)
                .subscribe(v -> alertDialoguePopUp());
    }

    @SuppressLint("CheckResult")
    private void alertDialoguePopUp() {
        final View view = LayoutInflater.from(itemView.getContext()).inflate(R.layout.alert_dialogue_layout, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());

        final TextView alertDonorNameTextView = view.findViewById(R.id.alertd_donor_name_textView);
        final TextView alertDonorEmailTextView = view.findViewById(R.id.alertd_email_donor_textView);
        final ImageView alertImageView = view.findViewById(R.id.alert_imageView);

        alertDonorNameTextView.setText(persons.getName());
        Picasso.get().load(persons.getImage()).into(alertImageView);
        alertDonorEmailTextView.setText(persons.getEmail());
        builder.setView(view);
        builder.show();

        RxView.clicks(alertDonorNameTextView)
                .subscribe(fromAlertDialogue -> listener.moveToUserDetailFragment());
        RxView.clicks(alertDonorEmailTextView)
                .subscribe(email -> listener.sendEmailToTheDonor(persons.getEmail()));

    }
}
