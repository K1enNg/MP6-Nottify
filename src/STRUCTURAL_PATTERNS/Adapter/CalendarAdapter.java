package STRUCTURAL_PATTERNS.Adapter;

import CREATIONAL_PATTERNS.Factory.Appointment;

public class CalendarAdapter implements CalendarAPI{
    private final GoogleCalendarAPI googleCalendar = new GoogleCalendarAPI();
    @Override
    public void syncAppointment(Appointment appointment) {
        googleCalendar.addEvent(appointment.getDetails(), appointment.getDate());
    }
}
