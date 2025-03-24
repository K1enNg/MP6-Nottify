package BEHAVIORAL_PATTERNS.Stratergy;

import CREATIONAL_PATTERNS.Factory.Appointment;

public class FirstComeFirstServeStrategy implements ScheduleStrategy{
    @Override
    public void schedule(Appointment appointment) {
        System.out.println("Appointment schedule using First-Come-First-Serve policy on " + appointment.getDetails());
    }
}
