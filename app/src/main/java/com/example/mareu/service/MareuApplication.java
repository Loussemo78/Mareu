package com.example.mareu.service;

import android.app.Application;

import com.example.mareu.di.DI;
import com.example.mareu.repositories.MeetingRepository;

public class MareuApplication extends Application {

    private MeetingRepository meetingRepository;

    public MeetingRepository getMeetingRepository() {

        if (meetingRepository == null) meetingRepository = DI.createMeetingRepository();
        return meetingRepository;
    }
}
