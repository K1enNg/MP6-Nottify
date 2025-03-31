package CREATIONAL_PATTERNS.Factory;

import User.Patient;

import java.util.Date;

public class AppointmentFactory {
    public static Appointment createAppointment(Type type, Date date, String details, Patient patient){
        return switch (type) {
            case InPerson -> new InPersonAppointment(date, details, patient);
            case Virtual -> new VirtualAppointment(date, details, patient);
            default -> throw new IllegalArgumentException("Invalid type");
        };
    }
}
