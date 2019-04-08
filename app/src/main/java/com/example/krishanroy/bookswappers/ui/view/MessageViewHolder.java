package com.example.krishanroy.bookswappers.ui.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.krishanroy.bookswappers.R;
import com.example.krishanroy.bookswappers.ui.model.TextMessage;

public class MessageViewHolder extends RecyclerView.ViewHolder {
    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void onBind(TextMessage textMessage) {
        TextView messageTextView = itemView.findViewById(R.id.text_message_textview);
        String message = textMessage.getName() + "\n" + textMessage.getMessage();
        messageTextView.setText(message);
    }
}
