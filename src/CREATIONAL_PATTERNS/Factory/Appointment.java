package CREATIONAL_PATTERNS.Factory;

import CREATIONAL_PATTERNS.User.Patient;

import java.util.Date;

public abstract class Appointment {
    public Date date;
    public String details;
    public Patient patient;
    public String doctorName;
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
    public String getDoctorName() {
        return doctorName;
    }
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
    private boolean confirmed;
    public Appointment() {
        this.confirmed = false;
    }
    public boolean isConfirmed() {
        return confirmed;
    }
    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}
