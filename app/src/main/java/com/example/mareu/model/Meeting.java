package com.example.mareu.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class Meeting {

    private String mTopic;
    private Date mDate;
    private String mRoomName;
    private String mParticipants;

    public Meeting(String mTopic, Date mDate, String mRoomName, String mParticipants) {
        this.mTopic = mTopic;
        this.mDate = mDate;
        this.mRoomName = mRoomName;
        this.mParticipants = mParticipants;
    }




    public String getmRoomName() {
        return mRoomName;
    }


    public String getmTopic() {
        return mTopic;
    }


    public String getmParticipants() {
        return mParticipants;
    }


    public Date getmDate() {

        return mDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meeting)) return false;
        Meeting meeting = (Meeting) o;
        return mRoomName.equals(meeting.mRoomName) &&
                mTopic.equals(meeting.mTopic);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(mRoomName, mTopic);
    }


    public String getFormatDate() {
        SimpleDateFormat fmtOut = new SimpleDateFormat("HH:mm");
        return fmtOut.format(mDate);
    }

     public boolean isSameDay(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        boolean sameYear = calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR);
        boolean sameMonth = calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH);
        boolean sameDay = calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
        return (sameDay && sameMonth && sameYear );
    }

    public boolean isFill(){
         return true;
    }

}
