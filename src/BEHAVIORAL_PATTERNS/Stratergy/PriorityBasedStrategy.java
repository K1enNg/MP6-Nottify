package BEHAVIORAL_PATTERNS.Stratergy;

import CREATIONAL_PATTERNS.Factory.Appointment;

public class PriorityBasedStrategy implements ScheduleStrategy{
    @Override
    public void schedule(Appointment appointment) {
        System.out.println("Appointment schedule using Priority-Based policy on " + appointment.getDetails());
    }
}
