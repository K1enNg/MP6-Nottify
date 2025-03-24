package CREATIONAL_PATTERNS.Factory;

import java.util.Date;

public abstract class Appointment {
    public Date date;
    public String details;
    public abstract void schedule();
    public Date getDate() {return date; }
    public String getDetails() {return details;}
}
