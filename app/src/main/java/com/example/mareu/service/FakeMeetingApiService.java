package com.example.mareu.service;

import com.example.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.mareu.service.FakeMeetingGenerator.generateMeetings;

public class FakeMeetingApiService implements MeetingApiService {

    private List<Meeting> mMeetings = generateMeetings();


    @Override
    public List<Meeting> getMeetings() {
        return mMeetings;
    }


    @Override
    public List<Meeting> filtreByLieuAndDate(String salle, Date date) {

        List<Meeting> listeFiltreeParLieuEtDate = new ArrayList<>();

        for (Meeting meeting : mMeetings) {
            if (meeting.getmRoomName().equals(salle) || salle.equals("") && (meeting.isSameDay(date, meeting.getmDate()) || date == null))
                listeFiltreeParLieuEtDate.add(meeting);
        }
        return listeFiltreeParLieuEtDate;

    }


    @Override
    public void addMeeting(Meeting meeting) {
        mMeetings.add(meeting);
    }


    @Override
    public void delMeeting(Meeting meeting) {
        mMeetings.remove(meeting);
    }
}
