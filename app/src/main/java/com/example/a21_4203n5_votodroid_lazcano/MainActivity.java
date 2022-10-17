package com.example.a21_4203n5_votodroid_lazcano;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.a21_4203n5_votodroid_lazcano.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}