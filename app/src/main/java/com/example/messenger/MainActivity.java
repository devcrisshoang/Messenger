package com.example.messenger;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ImageButton searchButton;
    ChatFragment chatFragment;
    ProfileFragment profileFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setWidget();
        searchButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,SearchUserActivity.class));
        });
        bottomNavigationView.setOnItemSelectedListener(Item -> {
            if (Item.getItemId() == R.id.menu_chat){
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,chatFragment).commit();
            }
            if (Item.getItemId() == R.id.menu_profile){
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,profileFragment).commit();
            }
            return true;
        });
        bottomNavigationView.setSelectedItemId(R.id.menu_chat);
    }
    public void setWidget(){
        chatFragment = new ChatFragment();
        profileFragment = new ProfileFragment();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        searchButton = findViewById(R.id.main_search_btn);
    }
}