package com.example.mareu.di;

import com.example.mareu.repositories.MeetingRepository;
import com.example.mareu.service.FakeMeetingApiService;

public class DI {

    private static MeetingRepository mMeetingRepository = new MeetingRepository(new FakeMeetingApiService());

    public static MeetingRepository createMeetingRepository() {
        return mMeetingRepository;

    }




}
