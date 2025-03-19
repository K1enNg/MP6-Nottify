package CREATIONAL_PATTERNS;

public class AppointmentFactory {
    public static Appointment createAppointment(Type type){
        return switch (type) {
            case InPerson -> new InPersonAppointment();
            case Virtual -> new VirtualAppointment();
            default -> throw new IllegalArgumentException("Invalid type");
        };
    }
}
