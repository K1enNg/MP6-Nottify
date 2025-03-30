package BEHAVIORAL_PATTERNS.Command;

import CREATIONAL_PATTERNS.Factory.Appointment;

import java.util.Date;

public class RescheduleAppointmentCommand implements Command{
    private Appointment appointment;
    private Date newDate;

    public RescheduleAppointmentCommand(Appointment appointment, Date newDate){
        this.appointment = appointment;
        this.newDate = newDate;
    }

    @Override
    public void execute() {
        appointment.reschedule(newDate);
    }
}
