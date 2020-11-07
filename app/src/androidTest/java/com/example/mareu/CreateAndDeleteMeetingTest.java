package com.example.mareu;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.repositories.MeetingRepository;
import com.example.mareu.ui.ListMeetingActivity;
import com.example.mareu.utils.MyViewAction;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;

@RunWith(AndroidJUnit4.class)
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateAndDeleteMeetingTest {

    private List<Meeting> meetingList;
    private MeetingRepository service;
    private static int ITEMS_COUNT = 6;
    private static String ROOM_TO_ADD = "Luigi";



    @Rule
    public ActivityTestRule<ListMeetingActivity> mActivityTestRule = new ActivityTestRule<>(ListMeetingActivity.class);


    @Before
    public void setUp() {
        service = DI.createMeetingRepository();
        meetingList = service.getMeetings();

    }



    @Test
    public void D_checkIfNewMeetingIsCreate() throws InterruptedException{
        onView(withId(R.id.activity_list_meeting_fab)).perform(click());
        onView(withId(R.id.editTextLieu)).perform(click());
        onView(withText(ROOM_TO_ADD)).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.editTextSujet)).perform(typeText("Economie"));
        onView(withId(R.id.editTextParticipant)).perform(typeText("michael@lamzone.com"));
        onView(withId(R.id.editTextDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2020,4,12));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.editTextHeure)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(12,15));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.buttonAdd)).perform(click());
        onView(ViewMatchers.withId(R.id.activity_list_meeting)).check(withItemCount(ITEMS_COUNT+1));
    }



    @Test
    public  void E_checkIfMeetingIsDelete(){
        //onView(allOf(withId(R.id.activity_list_meeting),isDisplayed())).check(withItemCount(ITEMS_COUNT));
        onView(ViewMatchers.withId(R.id.activity_list_meeting)).perform(actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.item_list_meeting_delete)));
        onView(withId(R.id.activity_list_meeting)).check(withItemCount(ITEMS_COUNT));
    }

}
