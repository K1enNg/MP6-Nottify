package CREATIONAL_PATTERNS.Builder;

import CREATIONAL_PATTERNS.Factory.Appointment;
import CREATIONAL_PATTERNS.Factory.AppointmentFactory;
import CREATIONAL_PATTERNS.Factory.Type;

import java.util.Date;

public class AppointmentBuilder {
    private Appointment appointment;

    // Constructor accepts enum Type
    public AppointmentBuilder(Type type) {
        appointment = AppointmentFactory.createAppointment(type);
    }

    public AppointmentBuilder setDate(Date date) {
        appointment.date = date;
        return this;
    }

    public AppointmentBuilder setDetails(String details) {
        appointment.details = details;
        return this;
    }

    public Appointment build() {
        return appointment;
    }
}

