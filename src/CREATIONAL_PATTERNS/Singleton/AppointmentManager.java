package CREATIONAL_PATTERNS.Singleton;

import CREATIONAL_PATTERNS.Factory.Appointment;

import java.util.ArrayList;
import java.util.List;

public class AppointmentManager {
    private static AppointmentManager instance;
    private List<Appointment> appointments = new ArrayList<>();

    private AppointmentManager() {}

    public static AppointmentManager getInstance(){
        if (instance == null) {
            instance = new AppointmentManager();
        }
        return instance;
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        System.out.println("Appointment added. Total Appointments: " + appointments.size());
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }
}
