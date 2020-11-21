package com.example.mareu;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.repositories.MeetingRepository;
import com.example.mareu.ui.ListMeetingActivity;
import com.example.mareu.utils.MyViewAction;
import com.example.mareu.utils.RecyclerViewItemCountAssertion;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.List;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LaunchingAndFilteringInstrumentedTest {


    private List<Meeting> meetingList;
    private MeetingRepository service;
    //private static int ITEMS_COUNT = 5;
    private static String ROOM_TO_FILTER = "Peach";
    private static String ROOM_TO_ADD = "Luigi";

    @Rule
    public ActivityTestRule<ListMeetingActivity> mActivityTestRule = new ActivityTestRule<>(ListMeetingActivity.class);


    @Before
    public void setUp() {
        service = DI.createMeetingRepository();
        meetingList = service.getMeetings();

    }


    @Test
    public void A_checkIfMeetingIsLaunched() {
        onView(allOf(withId(R.id.activity_list_meeting), isDisplayed())).check(new RecyclerViewItemCountAssertion(5));
    }

    @Test
    public void B_checkFiltreByLieuIsWorking() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("filter")).perform(click());
        onView(withId(R.id.editTextFiltreRoom)).perform(click());
        onView(withText(ROOM_TO_FILTER)).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.buttonFiltrer)).perform(click());
        onView(ViewMatchers.withId(R.id.activity_list_meeting)).check(matches(hasChildCount(2)));
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("reset")).perform(click());
        onView(withId(R.id.btnResetFromDateFilter)).perform(click());

    }

    @Test
    public void C_checkFiltreByDateIsWorking() {
        //A_checkIfMeetingIsLaunched();
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("filter")).perform(click());
        onView(withId(R.id.editTextFiltreDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2020, 11, 2));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.buttonFiltrer)).perform(click());
        onView(ViewMatchers.withId(R.id.activity_list_meeting)).check(matches(hasChildCount(1)));
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("reset")).perform(click());
        onView(withId(R.id.btnResetFromDateFilter)).perform(click());
    }


    @Test
    public void checkIfNewMeetingIsCreate()  {
        //A_checkIfMeetingIsLaunched();
        onView(withId(R.id.activity_list_meeting_fab)).perform(click());
        onView(withId(R.id.editTextLieu)).perform(click());
        onView(withText(ROOM_TO_ADD)).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.editTextSujet)).perform(replaceText("economie"));
        onView(withId(R.id.editTextParticipant)).perform(replaceText("michael@lamzone.com"));
        onView(withId(R.id.editTextDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2020, 4, 12));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.editTextHeure)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(12, 15));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.buttonAdd)).perform(click());
        onView(withId(R.id.activity_list_meeting)).check(new RecyclerViewItemCountAssertion(6));

    }


    @Test
    public void checkIfMeetingIsDelete() {
        onView(ViewMatchers.withId(R.id.activity_list_meeting)).perform(actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.item_list_meeting_delete)));
        onView(withId(R.id.activity_list_meeting)).check(new RecyclerViewItemCountAssertion(4));

    }


}
