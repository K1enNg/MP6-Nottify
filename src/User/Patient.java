package User;

import CREATIONAL_PATTERNS.Factory.Appointment;
import STRUCTURAL_PATTERNS.Facade.ScheduleFacade;

public class Patient extends User{

    public Patient(String name) {
        super(name);
    }

    public void bookAppointment(Appointment appointment, ScheduleFacade facade) {
        facade.bookAppointment(appointment);
        System.out.println(name + " booked an appointment.");
    }

    public void sendMessageToDoctor(Doctor doctor, String message) {
        System.out.println("Message from " + name + " to Dr. " + doctor.getName() + ": " + message);
    }
}
