package CREATIONAL_PATTERNS;

public class InPersonAppointment extends Appointment{
    @Override
    public void schedule() {
        System.out.println("In-Person Appointment Schedule on " + date + " with details: " + details);
    }
}
