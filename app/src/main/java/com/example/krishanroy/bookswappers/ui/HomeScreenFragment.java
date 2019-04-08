package com.example.krishanroy.bookswappers.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.krishanroy.bookswappers.R;
import com.example.krishanroy.bookswappers.ui.controller.BookAdapter;
import com.example.krishanroy.bookswappers.ui.model.Persons;
import com.example.krishanroy.bookswappers.ui.network.PersonService;
import com.example.krishanroy.bookswappers.ui.network.RetrofitSingleton;
import com.jakewharton.rxbinding3.view.RxView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;
import static android.telephony.MbmsDownloadSession.RESULT_CANCELLED;

public class HomeScreenFragment extends Fragment implements SearchView.OnQueryTextListener {
    List<Persons> personsList;
    public static final String TAG = "HomeScreenFragment";
    private BookAdapter bookAdapter;
    SearchView searchView;
    private FragmentCommunication listener;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imageView;
    private String currentPhotoPath;
    private static final int REQUEST_TAKE_PHOTO = 1;


    public static HomeScreenFragment newInstance() {
        return new HomeScreenFragment();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentCommunication) {
            listener = (FragmentCommunication) context;
        } else {
            throw new RuntimeException(context.toString() +
                    "must implement FragmentCommunication");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.home_screen_fragment, container, false);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.book_recycler_view);
        searchView = view.findViewById(R.id.home_screen_searchview);
        searchView.setOnQueryTextListener(this);
        FloatingActionButton fab = view.findViewById(R.id.add_books_fab);
        RxView.clicks(fab).subscribe(clicks -> takePicturesAndSave());

        bookAdapter = new BookAdapter(new LinkedList<>(), listener);
        recyclerView.setAdapter(bookAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        RetrofitSingleton
                .getInstance()
                .create(PersonService.class)
                .getPersons()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        persons -> {
                            Log.d(TAG, "onViewCreated: " + persons.get(0).getImage());
                            this.personsList = persons;
                            bookAdapter.setData(personsList, listener);
                            //recyclerView.setAdapter(bookAdapter);
                        },
                        throwable -> Log.e(TAG, "onFailure: " + throwable));
    }

    private void takePicturesAndSave() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(requireContext(),
                        "com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //do something
        } else if (resultCode == RESULT_CANCELLED) {
            // User Cancelled the action
            Toast.makeText(requireContext(), "cancelled image capturing", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        List<Persons> newPersonsList = new LinkedList<>();
        for (Persons p : personsList) {
            if (p.getAddress().getCity().toLowerCase().startsWith(s.toLowerCase())) {
                newPersonsList.add(p);
            }
        }
        bookAdapter.setData(newPersonsList, listener);
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.links_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_github_link:
                listener.openTheGitHubLink();
            case R.id.menu_linkedin_link:
                listener.openTheLinkedInPage();
            case R.id.menu_sign_out:
                listener.signOutFromTheApp();
        }
        return super.onOptionsItemSelected(item);
    }
}
