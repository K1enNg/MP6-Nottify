package GUI;

import CREATIONAL_PATTERNS.Factory.Appointment;
import CREATIONAL_PATTERNS.Factory.InPersonAppointment;
import GUI.AppointmentManagementPanel;
import GUI.OutputPanel;
import GUI.RoleSelectionPanel;
import STRUCTURAL_PATTERNS.Facade.ScheduleFacade;
import User.*;
import CREATIONAL_PATTERNS.*;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppointmentGUI extends JFrame {
    private RoleSelectionPanel roleSelectionPanel;
    private AppointmentManagementPanel appointmentManagementPanel;
    private OutputPanel outputPanel;
    private ScheduleFacade facade;

    public AppointmentGUI() {
        facade = new ScheduleFacade();
        setTitle("Smart Appointment System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize components
        roleSelectionPanel = new RoleSelectionPanel();
        appointmentManagementPanel = new AppointmentManagementPanel();
        outputPanel = new OutputPanel();

        // Add panels to the frame
        add(roleSelectionPanel, BorderLayout.NORTH);
        add(appointmentManagementPanel, BorderLayout.CENTER);
        add(outputPanel, BorderLayout.SOUTH);

        // Button Actions
        appointmentManagementPanel.getBookButton().addActionListener(e -> bookAppointment());
        appointmentManagementPanel.getMessageButton().addActionListener(e -> messageDoctor());
        appointmentManagementPanel.getConfirmButton().addActionListener(e -> confirmAppointment());
        appointmentManagementPanel.getDeclineButton().addActionListener(e -> declineAppointment());
        appointmentManagementPanel.getRescheduleButton().addActionListener(e -> rescheduleAppointment());

        setVisible(true);
    }

    private void bookAppointment() {
        try {
            String name = roleSelectionPanel.getUserName();
            String role = roleSelectionPanel.getSelectedRole();
            String dateStr = appointmentManagementPanel.getDateInput();
            String details = appointmentManagementPanel.getDetailsInput();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = sdf.parse(dateStr);

//            if ("Patient".equals(role)) {
//                Patient patient = new Patient(name);
//                Appointment appointment = new Appointment(date, details, patient);
//                patient.bookAppointment(appointment, facade);
//                outputPanel.appendMessage("Booked appointment for " + name + " on " + sdf.format(date));
//            } else {
//                JOptionPane.showMessageDialog(this, "Only Patients can book appointments.");
//            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void messageDoctor() {
        String name = roleSelectionPanel.getUserName();
        String role = roleSelectionPanel.getSelectedRole();
        String message = JOptionPane.showInputDialog("Enter message to doctor:");

        if ("Patient".equals(role)) {
            Patient patient = new Patient(name);
            Doctor doctor = new Doctor("Dr. Smith");
            patient.sendMessageToDoctor(doctor, message);
            outputPanel.appendMessage(name + " messaged Dr. Smith: " + message);
        } else {
            JOptionPane.showMessageDialog(this, "Only Patients can send messages.");
        }
    }

    private void confirmAppointment() {
        String name = roleSelectionPanel.getUserName();
        String role = roleSelectionPanel.getSelectedRole();

        if ("Doctor".equals(role)) {
            Doctor doctor = new Doctor(name);
            // Assuming InPersonAppointment is a concrete subclass of Appointment
            Appointment appointment = new InPersonAppointment(new Date(), "General Check-up", new Patient("Alice"));
            doctor.confirmAppointment(appointment);
            outputPanel.appendMessage("Dr. " + name + " confirmed an appointment.");
        } else {
            JOptionPane.showMessageDialog(this, "Only Doctors can confirm appointments.");
        }
    }

    private void declineAppointment() {
        String name = roleSelectionPanel.getUserName();
        String role = roleSelectionPanel.getSelectedRole();

        if ("Doctor".equals(role)) {
            Doctor doctor = new Doctor(name);
            // Assuming InPersonAppointment is a concrete subclass of Appointment
            Appointment appointment = new InPersonAppointment(new Date(), "Check-up", new Patient("Alice"));
            doctor.declineAppointment(appointment);
            outputPanel.appendMessage("Dr. " + name + " declined an appointment.");
        } else {
            JOptionPane.showMessageDialog(this, "Only Doctors can decline appointments.");
        }
    }

    private void rescheduleAppointment() {
        try {
            String name = roleSelectionPanel.getUserName();
            String role = roleSelectionPanel.getSelectedRole();
            String dateStr = appointmentManagementPanel.getDateInput();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date newDate = sdf.parse(dateStr);

            if ("Doctor".equals(role)) {
                Doctor doctor = new Doctor(name);
                // Assuming InPersonAppointment is a concrete subclass of Appointment
                Appointment appointment = new InPersonAppointment(new Date(), "Check-up", new Patient("Alice"));
                doctor.rescheduleAppointment(appointment, newDate);
                outputPanel.appendMessage("Dr. " + name + " rescheduled an appointment to " + sdf.format(newDate));
            } else {
                JOptionPane.showMessageDialog(this, "Only Doctors can reschedule appointments.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
