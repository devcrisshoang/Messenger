package com.example.messenger.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messenger.R;
import com.example.messenger.models.ChatMessageModel;
import com.example.messenger.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class ChatRecyclerAdapter extends FirestoreRecyclerAdapter<ChatMessageModel,ChatRecyclerAdapter.ChatModelViewHolder> {
    Context context;
    public ChatRecyclerAdapter(@NonNull FirestoreRecyclerOptions<ChatMessageModel> options,Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatModelViewHolder ChatModelViewHolder, int i, @NonNull ChatMessageModel ChatMessageModel) {
        if(ChatMessageModel.getSenderId().equals(FirebaseUtil.currentUserId())){
            ChatModelViewHolder.leftLayout.setVisibility(View.GONE);
            ChatModelViewHolder.rightLayout.setVisibility(View.VISIBLE);
            ChatModelViewHolder.rightChatTextView.setText(ChatMessageModel.getMessage());
        }
        else {
            ChatModelViewHolder.rightLayout.setVisibility(View.GONE);
            ChatModelViewHolder.leftLayout.setVisibility(View.VISIBLE);
            ChatModelViewHolder.leftChatTextView.setText(ChatMessageModel.getMessage());
        }
    }

    @NonNull
    @Override
    public ChatModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_message_recycler_row,parent,false);
        return new ChatModelViewHolder(view);
    }

    class ChatModelViewHolder extends RecyclerView.ViewHolder{
        LinearLayout leftLayout, rightLayout;
        TextView leftChatTextView, rightChatTextView;
        public ChatModelViewHolder(@NonNull View itemView) {
            super(itemView);
            leftLayout = itemView.findViewById(R.id.left_chat_layout);
            rightLayout = itemView.findViewById(R.id.right_chat_layout);
            leftChatTextView = itemView.findViewById(R.id.left_chat_textview);
            rightChatTextView = itemView.findViewById(R.id.right_chat_textview);

        }
    }
}
