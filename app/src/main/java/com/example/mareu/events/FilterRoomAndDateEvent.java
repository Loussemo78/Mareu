package com.example.mareu.events;



import java.util.Date;
import java.util.List;

public class FilterRoomAndDateEvent {

    public String room;
    public Date date;

    public FilterRoomAndDateEvent(String room , Date date ) {
        this.room = room;
        this.date = date;
    }
}


