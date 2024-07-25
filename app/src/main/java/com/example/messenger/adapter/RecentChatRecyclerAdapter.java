package com.example.messenger.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messenger.ChatActivity;
import com.example.messenger.R;
import com.example.messenger.models.ChatRoomModel;
import com.example.messenger.models.UserModel;
import com.example.messenger.utils.AndroidUtil;
import com.example.messenger.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;


public class RecentChatRecyclerAdapter extends FirestoreRecyclerAdapter<ChatRoomModel,RecentChatRecyclerAdapter.ChatRoomModelViewHolder> {
    Context context;
    public RecentChatRecyclerAdapter(@NonNull FirestoreRecyclerOptions<ChatRoomModel> options,Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatRoomModelViewHolder ChatRoomModelViewHolder, int i, @NonNull ChatRoomModel model) {
        FirebaseUtil.getOtherUserFromChatRoom(model.getUserIds()).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                UserModel otherUserModel = task.getResult().toObject(UserModel.class);
                ChatRoomModelViewHolder.usernameText.setText(otherUserModel.getUsername());
                ChatRoomModelViewHolder.lastMessageText.setText(model.getLastMessage());
                ChatRoomModelViewHolder.lastMessageTime.setText(FirebaseUtil.timestampToString(model.getLastMessageTimestamp()));
            }
        });
    }

    @NonNull
    @Override
    public ChatRoomModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recent_chat_recycler_row,parent,false);
        return new ChatRoomModelViewHolder(view);
    }

    class ChatRoomModelViewHolder extends RecyclerView.ViewHolder{
        TextView usernameText;
        TextView lastMessageText;
        TextView lastMessageTime;
        ImageView profilePic;
        public ChatRoomModelViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.user_name_text);
            lastMessageText = itemView.findViewById(R.id.last_message_text);
            lastMessageTime = itemView.findViewById(R.id.last_message_time_text);
            profilePic = itemView.findViewById(R.id.profile_pic_image_view);
        }
    }
}
