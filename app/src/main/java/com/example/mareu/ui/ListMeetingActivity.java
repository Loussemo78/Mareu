package com.example.mareu.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.base.BaseActivity;
import com.example.mareu.di.DI;
import com.example.mareu.events.DeleteMeetingEvent;
import com.example.mareu.events.FilterRoomAndDateEvent;
import com.example.mareu.events.ResetRoomAndDateEvent;
import com.example.mareu.model.Meeting;
import com.example.mareu.repositories.MeetingRepository;
import com.example.mareu.service.MeetingApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hootsuite.nachos.NachoTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;


public class ListMeetingActivity extends BaseActivity {

    RecyclerView mRecyclerView;
    FloatingActionButton mFab;
    private Dialog mDialog;
    private ListPopupWindow sallePopUpList;
    private EditText editTextLieu;
    private EditText editTextSujet;
    private EditText editTextHeure;
    private EditText editTextDate;
    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;
    private EditText editTextParticipants;
    private Button ajouter;
    private List<Meeting> mMeetings;
    private MeetingApiService mMeetingApiService;
    private MeetingListAdapter adapter;
    private MeetingRepository mMeetingRepository;
    private Calendar calendar = Calendar.getInstance();


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meeting);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        EventBus.getDefault().register(this);
        mMeetingRepository = DI.createMeetingRepository();
        mMeetings = mMeetingRepository.getMeetings();
        configureRecyclerView();
        datePicker = new DatePickerDialog(this);
        //timePicker = new TimePickerDialog(this);
        mDialog = new Dialog(this);
        mDialog.setContentView(R.layout.popup);
        mFab = findViewById(R.id.activity_list_meeting_fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.filtre) {
            //Si l'user choisit de filtrer par lieu
            if (!(mMeetings.isEmpty())) {
                BottomSheetLieuAndDate bottomSheetLieuAndDate = new BottomSheetLieuAndDate();
                bottomSheetLieuAndDate.show(getSupportFragmentManager(), "BottomSheet");
            }

            return true;
        } else if (id == R.id.reset) {
            if (!mMeetings.isEmpty()) {
                BottomSheetReset bottomSheetReset = new BottomSheetReset();
                bottomSheetReset.show(getSupportFragmentManager(), "BottomSheet");
            }
            return true;

        }
        return super.onOptionsItemSelected(item);
    }


    private void configureRecyclerView() {
        mRecyclerView = findViewById(R.id.activity_list_meeting);
        adapter = new MeetingListAdapter(mMeetings);
        mRecyclerView.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        configureRecyclerView();
    }

    // Eventbus permet de faciliter la communication entre les differents composants.


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe //mettre à jour la vue lorsque que l'évenement est appelé.
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        mMeetingRepository.delMeeting(event.meeting);
        configureRecyclerView();
    }

    @Subscribe
    public void onFilterRoom(FilterRoomAndDateEvent event) {
        mMeetings = mMeetingRepository.filtreByLieuAndDate(event.room, event.date);
        configureRecyclerView();
    }

    @Subscribe
    public void onResetList(ResetRoomAndDateEvent event) {
        mMeetings = mMeetingRepository.getMeetings();
        configureRecyclerView();
    }

    public void showPopup() {
        initEditText();
        onClickEditText();
        setPopUpList();

        ajouter = mDialog.findViewById(R.id.buttonAdd);

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtLieu = editTextLieu.getText().toString();
                String txtSujet = editTextSujet.getText().toString();
                String txtParticipant = editTextParticipants.getText().toString();
                // instancier les meeting meeting = new Meeting()


                if (txtSujet.equals("")) {
                    Toast.makeText(ListMeetingActivity.this, R.string.FillSubject, Toast.LENGTH_SHORT).show();

                } else {
                    editTextSujet.setText("");
                }

                if (txtParticipant.equals("")) {
                    Toast.makeText(ListMeetingActivity.this, R.string.FillPart, Toast.LENGTH_SHORT).show();

                } else {
                    editTextParticipants.setText("");
                }

                Meeting meeting = new Meeting(txtSujet,calendar.getTime(), txtLieu, txtParticipant);
                mMeetingRepository.addMeeting(meeting);
                Objects.requireNonNull(mRecyclerView.getAdapter()).notifyDataSetChanged();
                mDialog.dismiss();

            }

        });
        mDialog.show();
    }

    private void initEditText(){
        editTextHeure = mDialog.findViewById(R.id.editTextHeure);
        editTextDate = mDialog.findViewById(R.id.editTextDate);
        editTextLieu = mDialog.findViewById(R.id.editTextLieu);
        editTextSujet = mDialog.findViewById(R.id.editTextSujet);
        editTextParticipants = mDialog.findViewById(R.id.editTextParticipant);

        /*nachoParticipants.addChipTerminator(' ', 3);
        nachoParticipants.enableEditChipOnTouch(false, true);*/

        editTextHeure.setInputType(InputType.TYPE_NULL);
        editTextDate.setInputType(InputType.TYPE_NULL);

    }

    private void onClickEditText() {
    editTextLieu.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sallePopUpList.show();
        }
    });

    editTextDate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showDateDialog(editTextDate);
        }
    });
    editTextHeure.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showTimeDialog(editTextHeure);
        }
    });
}
    private void setPopUpList() {
        List<String> salle = new ArrayList<>();
        salle.add("Luigi");
        salle.add("Mario");
        salle.add("Peach");
        sallePopUpList = new ListPopupWindow(ListMeetingActivity.this);
        ArrayAdapter adapter = new ArrayAdapter(ListMeetingActivity.this, R.layout.item_salle, R.id.element, salle);
        sallePopUpList.setAnchorView(editTextLieu);
        sallePopUpList.setAdapter(adapter);
        sallePopUpList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                editTextLieu.setText(salle.get(position));
            }
        });
    }

    private void showTimeDialog(EditText editTextHeure) {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                SimpleDateFormat fmtOut = new SimpleDateFormat("HH:mm");
                String timeShowing = fmtOut.format(calendar.getTime());
                editTextHeure.setText(timeShowing);


            }

        };
        new TimePickerDialog(ListMeetingActivity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), false).show();
    }

    private void showDateDialog(EditText editTextDate) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(YEAR, year);
                calendar.set(MONTH, month);
                calendar.set(DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yy");
                String dateShowing = fmtOut.format(calendar.getTime());
                editTextDate.setText(dateShowing);


            }

            ;

        };
        new DatePickerDialog(ListMeetingActivity.this, dateSetListener, calendar.get(YEAR),
                calendar.get(MONTH), calendar.get(DAY_OF_MONTH)).show();


    }


}