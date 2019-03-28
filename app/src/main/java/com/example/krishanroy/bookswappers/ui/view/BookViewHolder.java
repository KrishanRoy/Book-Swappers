package com.example.krishanroy.bookswappers.ui.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
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
    private FragmentCommunication.detailScreen detailScreen;
    private Persons persons;
    private String email;
    private View view;

    public BookViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;

    }

    @SuppressLint("CheckResult")
    public void onBind(final Persons persons,
                       final FragmentCommunication.detailScreen detailScreen) {
        this.persons = persons;
        this.detailScreen = detailScreen;
        this.email = persons.getEmail();
        TextView bookTitleTextView = itemView.findViewById(R.id.title_textView);
        ImageView bookCoverImageView = itemView.findViewById(R.id.coverpage_imageView);
        TextView locationTextView = itemView.findViewById(R.id.location_textView);
        bookTitleTextView.setText(persons.getTitle());
        Picasso.get().load(persons.getImage()).into(bookCoverImageView);
        locationTextView.setText(persons.getAddress().getCity());
        RxView.clicks(itemView)
                .subscribe(v -> alertDialoguePopUp());
    }

    @SuppressLint("CheckResult")
    private void alertDialoguePopUp() {
        View view = LayoutInflater.from(itemView.getContext()).inflate(R.layout.alert_dialogue_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
        TextView alertDonorNameTextView = view.findViewById(R.id.alertd_donor_name_textView);
        TextView alertDonorEmailTextView = view.findViewById(R.id.alertd_email_donor_textView);
        ImageView alertImageView = view.findViewById(R.id.alert_imageView);
        alertDonorNameTextView.setText(persons.getName());
        Picasso.get().load(persons.getImage()).into(alertImageView);
        alertDonorEmailTextView.setText(persons.getEmail());
        builder.setView(view);
        builder.show();
        RxView.clicks(alertDonorNameTextView)
                .subscribe(fromAlertDialogue -> detailScreen.moveToUserDetailFragment());
        RxView.clicks(alertDonorEmailTextView)
                .subscribe(sendEmail -> emailToTheBookDonor());


    }

    private void emailToTheBookDonor() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Hi, I am interested in that book");
        if (intent.resolveActivity(view.getContext().getPackageManager()) != null) {
            view.getContext().startActivity(intent);
        }
    }
}
