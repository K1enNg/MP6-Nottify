package CREATIONAL_PATTERNS.Factory;

import User.Patient;

import java.util.Date;

public abstract class Appointment {
    public Date date;
    public String details;
    public Patient patient;
    public abstract void schedule();
    public void reschedule(Date newDate) {
        this.date = newDate;
        System.out.println("Appointment rescheduled to: " + newDate);
    }
    public void cancel() {
        System.out.println("Appointment on : " + date +" is cancelled");
    }

    public Date getDate() {return date; }
    public String getDetails() {return details;}
    public void setDate(Date newDate){
        this.date = newDate;
    }
}
