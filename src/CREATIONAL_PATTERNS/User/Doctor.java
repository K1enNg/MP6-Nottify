package CREATIONAL_PATTERNS.User;

import CREATIONAL_PATTERNS.Factory.Appointment;

import java.util.Date;

public class Doctor extends User{
    public Doctor(String name) {
        super(name);
    }

    public void confirmAppointment(Appointment appointment) {
        appointment.setConfirmed(true);
        System.out.println("Dr. " + name + " confirmed the appointment on " + appointment.getDate());
    }

    public void declineAppointment(Appointment appointment) {
        System.out.println("Dr. " + name + " declined the appointment on " + appointment.getDate());
    }

    public void rescheduleAppointment(Appointment appointment, Date newDate) {
        appointment.setDate(newDate);
        System.out.println("Dr. " + name + " rescheduled the appointment to " + newDate);
    }
}
