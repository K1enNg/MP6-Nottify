package CREATIONAL_PATTERNS.Factory;

public class VirtualAppointment extends Appointment {
    @Override
    public void schedule() {
        System.out.println("Virtual Appointment Schedule on " + date + " with details: " + details);
    }
}
