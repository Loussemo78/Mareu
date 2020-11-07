package com.example.mareu.service;

import com.example.mareu.model.Meeting;

import java.util.Date;
import java.util.List;

public interface MeetingApiService {


    List<Meeting> getMeetings();

    List<Meeting> filtreByLieuAndDate(String salle, Date date);

    void addMeeting(Meeting meeting);

    void delMeeting(Meeting meeting);
}
