package com.example.krishanroy.bookswappers.ui.network;

import com.example.krishanroy.bookswappers.ui.model.Persons;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface PersonService {
    String END_POINT = "KrishanRoy/GitHub_Training_MLH/master/BookOwners";

    @GET(END_POINT)
    Single<List<Persons>> getPersons();
}
