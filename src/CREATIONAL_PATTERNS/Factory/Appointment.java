package CREATIONAL_PATTERNS.Factory;

import java.util.Date;

public abstract class Appointment {
    protected Date date;
    protected String details;
    public abstract void schedule();
    public Date getDate() {return date; }
    public String getDetails() {return details;}
}
