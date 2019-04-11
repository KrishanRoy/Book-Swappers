package com.example.krishanroy.bookswappers.ui;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.krishanroy.bookswappers.R;
import com.example.krishanroy.bookswappers.ui.model.AppUsers;
import com.example.krishanroy.bookswappers.ui.model.Book;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.jakewharton.rxbinding3.view.RxView;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;
import static android.telephony.MbmsDownloadSession.RESULT_CANCELLED;

public class UploadNewBooksFragment extends Fragment {
    private FragmentCommunication listener;
    private Button openGalleryButton, uploadImageToFireBaseButton, doneButton;
    private EditText bookTitleEdText, authorNameEdText;
    private ProgressBar progressBar;
    private ImageView bookPreviewImageView;
    public static final int PICK_IMAGE_REQUEST = 2;
    private Uri imageUri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private StorageTask storageTask;
    public static final String TAG = "UploadNewBooksFragment";
    public static final String BOOK_REFERENCE = "BookUploaded";

    private DatabaseReference currentUserReference;
    private String uploaderCity, uploaderName, uploaderEmail;

    public static final String CURRENT_USER_ID = FirebaseAuth.getInstance().getUid();


    FirebaseStorage storage;


    public static UploadNewBooksFragment newInstance() {
        return new UploadNewBooksFragment();
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
        getCurrentUserInfoFromFirebase();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.upload_new_books_fragment, container, false);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewByIds(view);
        storageReference = FirebaseStorage.getInstance().getReference(BOOK_REFERENCE);
        databaseReference = FirebaseDatabase.getInstance().getReference(BOOK_REFERENCE);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        RxView.clicks(openGalleryButton)
                .subscribe(clicks -> openGalleryAndPickImage());
        RxView.clicks(uploadImageToFireBaseButton)
                .subscribe(clicks -> {
                    uploadImageFileToFireBase();
                });
        RxView.clicks(doneButton).subscribe(clicks -> listener.moveToHomeScreenFragment());

    }

    private void getCurrentUserInfoFromFirebase() {
        currentUserReference = FirebaseDatabase.getInstance().getReference("/appUsers/" + CURRENT_USER_ID);
        currentUserReference.addValueEventListener(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            AppUsers appUsers = dataSnapshot.getValue(AppUsers.class);
            uploaderName = appUsers.getName();
            uploaderCity = appUsers.getCity();
            uploaderEmail = appUsers.getUserEmail();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    public void openGalleryAndPickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = (getActivity()).getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImageFileToFireBase() {
        if (imageUri != null) {

            StorageReference imageFileReference = storageReference.child(System.currentTimeMillis() +
                    "." + getFileExtension(imageUri));
            storageTask = imageFileReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        Handler handler = new Handler();
                        handler.postDelayed(() -> progressBar.setProgress(0), 5000);
                        Toast.makeText(requireContext(), "Upload Successful", Toast.LENGTH_SHORT).show();
                        imageFileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                            Book newBook = new Book(
                                    bookTitleEdText.getText().toString().trim(),
                                    authorNameEdText.getText().toString().trim(),
                                    uri.toString(),
                                    uploaderCity,
                                    uploaderName,
                                    uploaderEmail);
                            String uploadId = databaseReference.push().getKey();
                            databaseReference.child(uploadId).setValue(newBook);

                        });
                    }).addOnFailureListener(e -> Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show())
                    .addOnProgressListener(taskSnapshot -> {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressBar.setProgress((int) progress);
                    });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(bookPreviewImageView);
        } else if (resultCode == RESULT_CANCELLED) {
            Toast.makeText(requireContext(), "cancelled image capturing", Toast.LENGTH_SHORT).show();
        }
    }


    private void findViewByIds(@NonNull View view) {
        openGalleryButton = view.findViewById(R.id.up_new_books_frag_open_gallery_button);
        uploadImageToFireBaseButton = view.findViewById(R.id.up_new_books_frag_upload_button);
        doneButton = view.findViewById(R.id.up_new_books_frag_backtohome_button);
        bookTitleEdText = view.findViewById(R.id.up_new_books_frag_book_title_edtext);
        authorNameEdText = view.findViewById(R.id.up_new_books_frag_author_name_edtext);
        bookPreviewImageView = view.findViewById(R.id.book_preview_imageview);
        progressBar = view.findViewById(R.id.up_new_books_frag_progress_bar);
    }
}