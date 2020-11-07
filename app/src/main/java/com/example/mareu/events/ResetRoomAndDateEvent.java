package com.example.mareu.events;

import com.example.mareu.model.Meeting;

import java.util.List;

public class ResetRoomAndDateEvent {
    public List<Meeting> meeting;



    public ResetRoomAndDateEvent(List<Meeting> meeting){
        this.meeting = meeting;
    }
}
