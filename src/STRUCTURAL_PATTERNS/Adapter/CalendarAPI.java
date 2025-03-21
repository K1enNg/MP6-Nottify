package STRUCTURAL_PATTERNS.Adapter;

import CREATIONAL_PATTERNS.Factory.Appointment;

public interface CalendarAPI {
    void syncAppointment(Appointment appointment);
}
