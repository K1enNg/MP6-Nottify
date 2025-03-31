package CREATIONAL_PATTERNS.Builder;

import CREATIONAL_PATTERNS.Factory.Appointment;
import CREATIONAL_PATTERNS.Factory.AppointmentFactory;
import CREATIONAL_PATTERNS.Factory.Type;
import User.Patient;

import java.util.Date;

public class AppointmentBuilder {
    private Appointment appointment;

    // Constructor accepts enum Type
    public AppointmentBuilder(Type type, Date date, String details, Patient patient) {
        appointment = AppointmentFactory.createAppointment(type, date, details, patient);
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

