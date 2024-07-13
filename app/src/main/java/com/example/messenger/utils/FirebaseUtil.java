package com.example.messenger.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseUtil {
    public static String currentUserId(){
        return FirebaseAuth.getInstance().getUid();
    }
    public static boolean isLoggedIn(){
        if(currentUserId() != null){
            return true;
        }
        return false;
    }
    public static DocumentReference currentUserDetails(){
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId());
    }
    public static CollectionReference allUserCollectionReference(){
        return FirebaseFirestore.getInstance().collection("users");
    }
    public static DocumentReference getChatRoomReference(String chatRoomId){
        return FirebaseFirestore.getInstance().collection("chatrooms").document(chatRoomId);
    }
    public static CollectionReference getChatRoomMessageReference(String chatRoomId){
        return getChatRoomReference(chatRoomId).collection("chats");
    }
    public static String getChatRoomId(String userId1, String userId2){
        if(userId1.hashCode() < userId2.hashCode()){
            return userId1 + "_" + userId2;
        }
        else {
            return userId2 + "_" + userId1;
        }
    }
}
