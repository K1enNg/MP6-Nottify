package CREATIONAL_PATTERNS;

public class VirtualAppointment extends Appointment{
    @Override
    public void schedule() {
        System.out.println("Virtual Appointment Schedule on " + date + " with details: " + details);
    }
}
