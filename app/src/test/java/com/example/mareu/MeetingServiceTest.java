package com.example.mareu;

import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.repositories.MeetingRepository;
import com.example.mareu.service.FakeMeetingGenerator;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MeetingServiceTest {


    private static MeetingRepository service;


    @BeforeClass
    public static void setUp() {
        service = DI.createMeetingRepository();
    }

    @Test
    public void A_getMeetingWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        List<Meeting> expectedMeeting = FakeMeetingGenerator.FAKE_MEETING;
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeeting.toArray()));
    }

    @Test
    public void B_FilterbyDateAndByLieuWithSuccess() {
        List<Meeting> listFiltree = service.filtreByLieuAndDate("Mario", new Date(1600589705000L));
        assertEquals(1, listFiltree.size());
        assertTrue(listFiltree.contains(FakeMeetingGenerator.FAKE_MEETING.get(1)));
    }


    @Test
    public void C_addMeetingWithSuccess() {
        Meeting meetingToAdd = new Meeting("Reunion Z", new Date(1597936505005L), "Peach",  "mike@hotm.fr");
        service.addMeeting(meetingToAdd);
        assertTrue(service.getMeetings().contains(meetingToAdd));
        service.delMeeting(meetingToAdd);

    }


    @Test
    public void D_deleteMeetingWithSuccess() {
        Meeting meetingToDelete = service.getMeetings().get(0);
        service.delMeeting(meetingToDelete);
        assertFalse(service.getMeetings().contains(meetingToDelete));
    }
}