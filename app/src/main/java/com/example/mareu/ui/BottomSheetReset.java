package com.example.mareu.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.events.ResetRoomAndDateEvent;
import com.example.mareu.model.Meeting;
import com.example.mareu.repositories.MeetingRepository;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


public class BottomSheetReset extends BottomSheetDialogFragment {
    private View v;
    private List<Meeting> mMeetings ;
    private MeetingRepository meetingRepository;
    private Button buttonReset;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        meetingRepository = DI.createMeetingRepository();
        mMeetings = meetingRepository.getMeetings();
        //setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.bottom_sheet_filtre_date, container, false);
        buttonReset = (Button) v.findViewById(R.id.btnResetFromDateFilter);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new ResetRoomAndDateEvent(mMeetings));
                dismiss();
            }
        });
        //Gère le click sur le bouton reset

        return v;
    }



}