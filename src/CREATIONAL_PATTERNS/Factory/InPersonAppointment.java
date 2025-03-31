package CREATIONAL_PATTERNS.Factory;

import User.Patient;

import java.util.Date;

public class InPersonAppointment extends Appointment {
    public InPersonAppointment(Date date, String details, Patient patient){
        super(date,details,patient);
    }
    @Override
    public void schedule() {
        System.out.println("In-Person Appointment Schedule on " + date + " with details: " + details);
    }
}
