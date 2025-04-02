package GUI;

import CREATIONAL_PATTERNS.Factory.Appointment;
import CREATIONAL_PATTERNS.Factory.AppointmentFactory;
import STRUCTURAL_PATTERNS.Facade.ScheduleFacade;
import CREATIONAL_PATTERNS.User.*;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppointmentGUI extends JFrame {
    private RoleSelectionPanel roleSelectionPanel;
    private AppointmentManagementPanel appointmentManagementPanel;
    private OutputPanel outputPanel;
    private ScheduleFacade facade;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private String userRole;
    private String userName;
    private List<Appointment> appointmentList;

    public AppointmentGUI() {
        appointmentList = new ArrayList<>();
        facade = new ScheduleFacade();
        setTitle("NOTTIFY - Appointment Scheduler");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

//        // Initialize components
//        roleSelectionPanel = new RoleSelectionPanel();
//        appointmentManagementPanel = new AppointmentManagementPanel();
        outputPanel = new OutputPanel();
//
//        // Add panels to the frame
//        add(roleSelectionPanel, BorderLayout.NORTH);
//        add(appointmentManagementPanel, BorderLayout.CENTER);
//        add(outputPanel, BorderLayout.SOUTH);
//
//        // Button Actions
//        appointmentManagementPanel.getBookButton().addActionListener(e -> bookAppointment());
//        appointmentManagementPanel.getMessageButton().addActionListener(e -> messageDoctor());
//        appointmentManagementPanel.getConfirmButton().addActionListener(e -> confirmAppointment());
//        appointmentManagementPanel.getDeclineButton().addActionListener(e -> declineAppointment());
//        appointmentManagementPanel.getRescheduleButton().addActionListener(e -> rescheduleAppointment());
//
//        setVisible(true);

        // Using CardLayout to switch between panels
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        roleSelectionPanel = new RoleSelectionPanel();
        appointmentManagementPanel = new AppointmentManagementPanel();

        mainPanel.add(roleSelectionPanel, "RoleSelection");
        mainPanel.add(appointmentManagementPanel, "AppointmentManagement");

        add(mainPanel, BorderLayout.CENTER);

        // Add action listener for role selection
        roleSelectionPanel.addContinueButtonListener(e -> proceedToMainScreen());

        setVisible(true);
    }

    private void proceedToMainScreen() {
        userName = roleSelectionPanel.getUserName();
        userRole = roleSelectionPanel.getSelectedRole();

        if (userName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your name.");
            return;
        }

        // Hide buttons based on role
        if ("Patient".equals(userRole)) {
            appointmentManagementPanel.getConfirmButton().setEnabled(false);
            appointmentManagementPanel.getDeclineButton().setEnabled(false);
        } else if ("Doctor".equals(userRole)) {
            appointmentManagementPanel.getBookButton().setEnabled(false);
            appointmentManagementPanel.getMessageButton().setEnabled(false);
        }

        // Switch to appointment management screen
        cardLayout.show(mainPanel, "AppointmentManagement");
    }
    private void bookAppointment() {
        try {
            String name = roleSelectionPanel.getUserName();
            String role = roleSelectionPanel.getSelectedRole();
            String dateStr = appointmentManagementPanel.getDateInput();
            String details = appointmentManagementPanel.getDetailsInput();
            CREATIONAL_PATTERNS.Factory.Type type = appointmentManagementPanel.getSelectedType();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = sdf.parse(dateStr);

            if ("Patient".equals(role)) {
                Patient patient = new Patient(name);
                Appointment appointment = AppointmentFactory.createAppointment(type, date, details, patient);

                // Store the appointment in the list
                appointmentList.add(appointment);

                patient.bookAppointment(appointment, facade);
                outputPanel.appendMessage("📅 " + name + " booked an appointment on " + sdf.format(date));

            } else {
                JOptionPane.showMessageDialog(this, "Only Patients can book appointments.");
            }
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
            Doctor doctor = new Doctor("Kien");
            patient.sendMessageToDoctor(doctor, message);
            outputPanel.appendMessage(name + " messaged Dr. "+ doctor.getName()+ ": " + message);
        } else {
            JOptionPane.showMessageDialog(this, "Only Patients can send messages.");
        }
    }

    private void confirmAppointment() {
        String name = roleSelectionPanel.getUserName();
        String role = roleSelectionPanel.getSelectedRole();

        if (!"Doctor".equals(role)) {
            JOptionPane.showMessageDialog(this, "Only Doctors can confirm appointments.");
            return;
        }

        if (appointmentList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No appointments available to confirm.");
            return;
        }

        // Doctor selects which appointment to confirm
        Appointment appointment = selectAppointment();
        if (appointment != null) {
            Doctor doctor = new Doctor(name);
            doctor.confirmAppointment(appointment);
            outputPanel.appendMessage("✅ Dr. " + name + " confirmed an appointment for " + appointment.getDetails());
        }
    }

    private void declineAppointment() {
        String name = roleSelectionPanel.getUserName();
        String role = roleSelectionPanel.getSelectedRole();

        if (!"Doctor".equals(role)) {
            JOptionPane.showMessageDialog(this, "Only Doctors can decline appointments.");
            return;
        }

        if (appointmentList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No appointments available to decline.");
            return;
        }

        Appointment appointment = selectAppointment();
        if (appointment != null) {
            Doctor doctor = new Doctor(name);
            doctor.declineAppointment(appointment);
            appointmentList.remove(appointment); // Remove the declined appointment
            outputPanel.appendMessage("❌ Dr. " + name + " declined an appointment for " + appointment.getDetails());
        }
    }


    private void rescheduleAppointment() {
        try {
            String name = roleSelectionPanel.getUserName();
            String role = roleSelectionPanel.getSelectedRole();
            String dateStr = appointmentManagementPanel.getDateInput();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date newDate = sdf.parse(dateStr);

            if (!"Doctor".equals(role)) {
                JOptionPane.showMessageDialog(this, "Only Doctors can reschedule appointments.");
                return;
            }

            if (appointmentList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No appointments available to reschedule.");
                return;
            }

            Appointment appointment = selectAppointment();
            if (appointment != null) {
                Doctor doctor = new Doctor(name);
                doctor.rescheduleAppointment(appointment, newDate);
                outputPanel.appendMessage("🔄 Dr. " + name + " rescheduled an appointment to " + sdf.format(newDate));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }


    private Appointment selectAppointment() {
        String[] appointmentDetails = new String[appointmentList.size()];

        for (int i = 0; i < appointmentList.size(); i++) {
            appointmentDetails[i] = "📅 " + appointmentList.get(i).getDate() + " - " + appointmentList.get(i).getDetails();
        }

        String selectedAppointment = (String) JOptionPane.showInputDialog(
                this,
                "Select an appointment:",
                "Appointment Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                appointmentDetails,
                appointmentDetails[0]
        );

        for (Appointment appointment : appointmentList) {
            if (selectedAppointment.contains(appointment.getDetails())) {
                return appointment;
            }
        }
        return null;
    }
}
