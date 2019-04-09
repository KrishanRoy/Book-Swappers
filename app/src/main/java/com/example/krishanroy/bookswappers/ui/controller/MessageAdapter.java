package com.example.krishanroy.bookswappers.ui.controller;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.krishanroy.bookswappers.R;
import com.example.krishanroy.bookswappers.ui.model.TextMessage;
import com.example.krishanroy.bookswappers.ui.view.MessageViewHolder;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {
    List<TextMessage> messageList;

    public MessageAdapter(List<TextMessage> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MessageViewHolder(LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.message_itemview, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int i) {
        messageViewHolder.onBind(messageList.get(i));

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
