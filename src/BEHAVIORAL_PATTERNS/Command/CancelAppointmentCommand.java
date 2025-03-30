package BEHAVIORAL_PATTERNS.Command;

import CREATIONAL_PATTERNS.Factory.Appointment;

public class CancelAppointmentCommand implements Command{
    private Appointment appointment;

    public CancelAppointmentCommand(Appointment appointment){
        this.appointment = appointment;
    }
    @Override
    public void execute() {
        appointment.cancel();
    }
}
