package STRUCTURAL_PATTERNS.Facade;

import CREATIONAL_PATTERNS.Factory.Appointment;
import CREATIONAL_PATTERNS.Singleton.AppointmentManager;

public class ScheduleFacade {
    private final AppointmentManager manager = AppointmentManager.getInstance();
    public void bookAppointment(Appointment appointment) {
        manager.addAppointment(appointment);
        appointment.schedule();
    }
    
}
