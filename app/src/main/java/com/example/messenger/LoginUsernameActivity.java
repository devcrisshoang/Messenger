package com.example.messenger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.messenger.models.UserModel;
import com.example.messenger.utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

public class LoginUsernameActivity extends AppCompatActivity {
    EditText usernameInput;
    Button letMeInButton;
    ProgressBar progressBar;
    String phoneNumber;
    UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_username);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setWidget();
        phoneNumber = getIntent().getExtras().getString("phone");
        getUsername();

        letMeInButton.setOnClickListener(v -> {
            setUsername();
        });
    }
    public void setInProgress(boolean inProgress) {
        if (inProgress) {
            progressBar.setVisibility(View.VISIBLE);
            letMeInButton.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            letMeInButton.setVisibility(View.VISIBLE);
        }
    }
    public void setWidget(){
        usernameInput = findViewById(R.id.login_username);
        letMeInButton = findViewById(R.id.login_let_me_in_btn);
        progressBar = findViewById(R.id.login_progress_bar);
    }
    public void setUsername(){

        String username = usernameInput.getText().toString();
        if(username.isEmpty() || username.length() < 3){
            usernameInput.setError("Username length should be at least 3 characters");
            return;
        }
        setInProgress(true);
        if(userModel != null){
            userModel.setUsername(username);
        }
        else {
            userModel = new UserModel(FirebaseUtil.currentUserId(),phoneNumber, username, Timestamp.now());
        }

        FirebaseUtil.currentUserDetails().set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                setInProgress(false);
                if(task.isSuccessful()){
                    Intent intent = new Intent(LoginUsernameActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });
    }
    public void getUsername(){
        setInProgress(true);
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                setInProgress(false);
                if(task.isSuccessful()){
                    UserModel userModel = task.getResult().toObject(UserModel.class);
                    if(userModel != null){
                        usernameInput.setText(userModel.getUsername());
                    }
                }
            }
        });
    }
}