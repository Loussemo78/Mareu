package com.example.mareu.base;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mareu.repositories.MeetingRepository;
import com.example.mareu.service.MareuApplication;

public abstract class BaseActivity extends AppCompatActivity {

    public MeetingRepository getMeetingRepository() {
        return ((MareuApplication) getApplication()).getMeetingRepository();
    }
}