package com.example.krishanroy.bookswappers.ui.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public class AppUsers implements Parcelable {
    private String name;
    private String city;
    private String state;
    private String userImage;
    private String userEmail;
    private Book book;

    public AppUsers( String name, String city, String state, String userEmail) {
        this.name = name;
        this.city = city;
        this.state = state;
        this.userEmail = userEmail;
    }

    public AppUsers(String name, String city, Book book) {
        this.name = name;
        this.city = city;
        this.book = book;
    }

    public AppUsers() {
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getUserImage() {
        return userImage;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Book getBook() {
        return book;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.city);
        dest.writeString(this.state);
        dest.writeString(this.userEmail);

    }

}


