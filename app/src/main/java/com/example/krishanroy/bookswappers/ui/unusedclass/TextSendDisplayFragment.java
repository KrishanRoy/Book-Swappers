package com.example.krishanroy.bookswappers.ui.unusedclass;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.krishanroy.bookswappers.R;
import com.example.krishanroy.bookswappers.ui.FragmentCommunication;
import com.example.krishanroy.bookswappers.ui.controller.MessageAdapter;
import com.example.krishanroy.bookswappers.ui.model.TextMessage;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TextSendDisplayFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentCommunication listener;
    private String senderName;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference messagesDatabaseReference;

    private ChildEventListener childEventListener;
    private RecyclerView messageRecyclerView;
    private MessageAdapter messageAdapter;
    private List<TextMessage> messagelist = new ArrayList<>();
    public static final String TAG = "TextSendDisplayFragment";

    private String mParam1;
    private String mParam2;


    public TextSendDisplayFragment() {
    }

    public static TextSendDisplayFragment newInstance() {
        TextSendDisplayFragment fragment = new TextSendDisplayFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentCommunication) {
            listener = (FragmentCommunication) context;
        } else {
            throw new RuntimeException(context.toString() +
                    "must implement Fragment Communication");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.text_send_display_fragment, container, false);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        messagesDatabaseReference = firebaseDatabase.getReference().child("messages");
        messageRecyclerView = view.findViewById(R.id.message_display_recycler_view);
        this.messagelist = new LinkedList<>();
        this.messageAdapter = new MessageAdapter(messagelist);

        EditText typeTextEditText = view.findViewById(R.id.send_text_edit_text);
        Button sendTextButton = view.findViewById(R.id.send_text_button);

        senderName = "Anonymous";
        preventEmptyMessageSending(typeTextEditText, sendTextButton);

        RxView.clicks(sendTextButton)
                .subscribe(clicks -> {
                            String message = typeTextEditText.getText().toString();
                            TextMessage textMessage = new TextMessage(message, senderName, null);
                            messagesDatabaseReference.push().setValue(textMessage);
                            typeTextEditText.setText("");
                        }
                );
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String message = ds.child("message").getValue(String.class);
                    String name = ds.child("name").getValue(String.class);
                    messagelist.add(new TextMessage(message, name, null));
                    messageAdapter = new MessageAdapter(messagelist);
                    messageRecyclerView.setAdapter(messageAdapter);
                    messageRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        messagesDatabaseReference.addListenerForSingleValueEvent(eventListener);
    }

    private void preventEmptyMessageSending(EditText typeTextEditText, Button sendTextButton) {
        typeTextEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    sendTextButton.setEnabled(true);
                } else {
                    sendTextButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}
