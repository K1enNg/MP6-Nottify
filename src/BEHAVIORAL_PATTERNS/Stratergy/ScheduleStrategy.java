package BEHAVIORAL_PATTERNS.Stratergy;

import CREATIONAL_PATTERNS.Factory.Appointment;

public interface ScheduleStrategy {
    void schedule(Appointment appointment);
}
