package CREATIONAL_PATTERNS.Factory;

import CREATIONAL_PATTERNS.User.Patient;

import java.util.Date;

public class VirtualAppointment extends Appointment {
    public VirtualAppointment(Date date, String details, Patient patient){
        this.date = date;
        this.details = details;
        this.patient = patient;
    }
    @Override
    public void schedule() {
        System.out.println("Virtual Appointment Schedule on " + date + " with details: " + details);
    }
}
