package com.example.mareu.repositories;


import com.example.mareu.model.Meeting;
import com.example.mareu.service.FakeMeetingApiService;

import java.util.Date;
import java.util.List;

public class MeetingRepository {

    private final FakeMeetingApiService fakeMeetingApiService;

    public MeetingRepository (FakeMeetingApiService fakeMeetingApiService){
        this.fakeMeetingApiService = fakeMeetingApiService;}

    public List<Meeting> getMeetings(){
        return this.fakeMeetingApiService.getMeetings();
    }


    public List<Meeting> filtreByLieuAndDate(String salle , Date date){
        return this.fakeMeetingApiService.filtreByLieuAndDate(salle,date);}


    public void addMeeting(Meeting meeting){ this.fakeMeetingApiService.addMeeting(meeting);
    }

    public void delMeeting(Meeting meeting){
        this.fakeMeetingApiService.delMeeting(meeting);
    }







}
