package com.example.mareu.service;

import android.content.Context;
import android.widget.Toast;

import com.example.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public   class FakeMeetingGenerator {


  public static List<Meeting> FAKE_MEETING =  Arrays.asList(
          new Meeting("Reunion A", new Date(1597936505000L),"Peach","mike@lamzone.com, miss@lamzone.com, loulou@lamzone.com"),
          new Meeting("Reunion B", new Date(1600589705000L),"Mario","frank@lamzone.com, paul@lamzone.fr, okdac@lamzone.com"),
          new Meeting("Reunion C", new Date(1602314105000L),"Peach","natacha@lamzone.com, amandine@lamzone.fr, popo@lamzone.com"),
          new Meeting("Reunion D", new Date(1604322905000L),"Luigi","corinne@lamzone.com, alain@lamzone.com , stephanie@lamzone.com"),
          new Meeting("Reunion E", new Date(1603613705000L),"Luigi","alex@lamzone.com.fr, viviane@lamzone.com , maxime@lamzone.com")

  );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(FAKE_MEETING);
    }



}
