package com.example.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.whatsappclone.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {
    ActivitySettingsBinding binder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder=ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
        getSupportActionBar().hide();
    }
}