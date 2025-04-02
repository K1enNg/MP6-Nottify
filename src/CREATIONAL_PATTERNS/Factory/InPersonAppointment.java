package CREATIONAL_PATTERNS.Factory;

import CREATIONAL_PATTERNS.User.Patient;

import java.util.Date;

public class InPersonAppointment extends Appointment {
    public InPersonAppointment(Date date, String details, Patient patient){
        this.date = date;
        this.details = details;
        this.patient = patient;
    }
    @Override
    public void schedule() {
        System.out.println("In-Person Appointment Schedule on " + date + " with details: " + details);
    }
}
