package STRUCTURAL_PATTERNS.Adapter;

import CREATIONAL_PATTERNS.Factory.Appointment;

import java.util.Date;

public class GoogleCalendarAPI{
    public void addEvent(String detail, Date date) {
        System.out.println("Event added to Google Calendar: " + detail + " on " + date);
    }
}
