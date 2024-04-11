package com.example.cst338project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cst338project2.databinding.ActivityPlayerHomePageBinding;

public class PlayerHomePageActivity extends AppCompatActivity {
    ActivityPlayerHomePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPlayerHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.playerLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.MainActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    public static Intent PlayerHomePageIntentFactory(Context context){
        return new Intent(context, PlayerHomePageActivity.class);
    }
}