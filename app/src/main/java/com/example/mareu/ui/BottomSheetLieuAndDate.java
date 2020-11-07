package com.example.mareu.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.Toast;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.events.FilterRoomAndDateEvent;
import com.example.mareu.events.ResetRoomAndDateEvent;
import com.example.mareu.model.Meeting;
import com.example.mareu.repositories.MeetingRepository;
import com.example.mareu.service.MeetingApiService;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.mareu.R.id.room_filter;

public class BottomSheetLieuAndDate extends BottomSheetDialogFragment {

    private List<Meeting> mMeetings;
   // private RecyclerView mRecyclerView;
    private Calendar calendar = Calendar.getInstance();
    private List<Meeting> meetings;
    private EditText editTextFiltreRoom;
    private EditText editTextFiltreDate;
    private Button buttonFiltrer;
    private ListPopupWindow roomFilterList;
    private MeetingRepository meetingRepository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Add rounded corners to the bottom sheet
        meetingRepository = DI.createMeetingRepository();
    }




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.filter_room, container, false);
        editTextFiltreRoom = (EditText) v.findViewById(R.id.editTextFiltreRoom);
        List<String> room = new ArrayList<>();
        room.add("Luigi");
        room.add("Mario");
        room.add("Peach");

        roomFilterList = new ListPopupWindow(getContext());
        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.item_room, room_filter,room);
        roomFilterList.setAnchorView(editTextFiltreRoom);
        roomFilterList.setAdapter(adapter);
        editTextFiltreRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomFilterList.show();
            }
        });
        roomFilterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                editTextFiltreRoom.setText(room.get(position));
            }
        });

        editTextFiltreDate = (EditText) v.findViewById(R.id.editTextFiltreDate);
        editTextFiltreDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDateBottomDialog(editTextFiltreDate);
            }
        });

        buttonFiltrer = (Button) v.findViewById(R.id.buttonFiltrer);
        buttonFiltrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {
              EventBus.getDefault().post(new FilterRoomAndDateEvent(editTextFiltreRoom.getText().toString(),
                      calendar.getTime()));

              dismiss();
            }
        });

        return v;
    }



    private void showDateBottomDialog(EditText editTextFiltreDate) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yy");
                String dateShowing = fmtOut.format(calendar.getTime());
                editTextFiltreDate.setText(dateShowing);



            };

        };
        new DatePickerDialog(getContext(), dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

}