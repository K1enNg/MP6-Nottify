package BEHAVIORAL_PATTERNS.Command;

import CREATIONAL_PATTERNS.Factory.Appointment;
import STRUCTURAL_PATTERNS.Facade.ScheduleFacade;

public class BookAppointmentCommand implements Command{
    private ScheduleFacade facade;
    private Appointment appointment;

    public BookAppointmentCommand(ScheduleFacade facade, Appointment appointment){
        this.facade = facade;
        this.appointment = appointment;
    }

    public void execute() {
        System.out.println("Executing booking command...");
        facade.bookAppointment(appointment);
    }
}
