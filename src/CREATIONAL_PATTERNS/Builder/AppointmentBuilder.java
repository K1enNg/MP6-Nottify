package CREATIONAL_PATTERNS.Builder;

import CREATIONAL_PATTERNS.Factory.Appointment;
import CREATIONAL_PATTERNS.Factory.AppointmentFactory;
import CREATIONAL_PATTERNS.Factory.Type;
import CREATIONAL_PATTERNS.User.Patient;

import java.util.Date;

public class AppointmentBuilder {
    private Type type;
    private Date date;
    private String details;
    private Patient patient;

    // Constructor only stores type initially
    public AppointmentBuilder(Type type) {
        this.type = type;
    }

    public AppointmentBuilder setDate(Date date) {
        this.date = date;
        return this;
    }

    public AppointmentBuilder setDetails(String details) {
        this.details = details;
        return this;
    }

    public AppointmentBuilder setPatient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public Appointment build() {
        if (type == null || date == null || details == null || patient == null) {
            throw new IllegalStateException("All fields must be set before building an appointment.");
        }
        return AppointmentFactory.createAppointment(type, date, details, patient);
    }
}
