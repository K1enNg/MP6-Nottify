package GUI;

import CREATIONAL_PATTERNS.Factory.Appointment;
import CREATIONAL_PATTERNS.Factory.AppointmentFactory;
import STRUCTURAL_PATTERNS.Facade.ScheduleFacade;
import CREATIONAL_PATTERNS.User.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
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
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);

        roleSelectionPanel = new RoleSelectionPanel();
        appointmentManagementPanel = new AppointmentManagementPanel();
        outputPanel = new OutputPanel();

        appointmentManagementPanel.getBookButton().addActionListener(e -> bookAppointment());
        appointmentManagementPanel.getMessageButton().addActionListener(e -> messageDoctor());
        appointmentManagementPanel.getConfirmButton().addActionListener(e -> confirmAppointment());
        appointmentManagementPanel.getDeclineButton().addActionListener(e -> declineAppointment());
        appointmentManagementPanel.getRescheduleButton().addActionListener(e -> rescheduleAppointment());

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(roleSelectionPanel, "RoleSelection");
        mainPanel.add(appointmentManagementPanel, "AppointmentManagement");

        add(mainPanel, BorderLayout.CENTER);
        add(outputPanel, BorderLayout.SOUTH);

        roleSelectionPanel.addContinueButtonListener(e -> proceedToMainScreen());

        setVisible(true);

        roleSelectionPanel.getRoleComboBox().addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedRole = (String) e.getItem();
                if ("Doctor".equals(selectedRole)) {
                    boolean authenticated = authenticateDoctor();
                    if (authenticated) {
                        userRole = "Doctor"; // set the global role
                        appointmentManagementPanel.setWelcomeText("👨‍⚕️ Dr. " + userName);
                        updateUIForRole(userRole);
                        cardLayout.show(mainPanel, "AppointmentManagement");
                    } else {
                        JOptionPane.showMessageDialog(this, "❌ Authentication failed. Reverting to Patient.");
                        roleSelectionPanel.getRoleComboBox().setSelectedItem("Patient");
                    }
                }
            }
        });


    }

    private void proceedToMainScreen() {
        userName = roleSelectionPanel.getUserName();
        userRole = roleSelectionPanel.getSelectedRole();

        if (userName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Please enter your name.");
            return;
        }

        updateUIForRole(userRole);
        appointmentManagementPanel.setWelcomeText("👤 Patient: " + userName);
        cardLayout.show(mainPanel, "AppointmentManagement");
    }

    private void updateUIForRole(String role) {
        if ("Doctor".equals(role)) {
            boolean authenticated = authenticateDoctor();
            if (!authenticated) {
                JOptionPane.showMessageDialog(this, "❌ Authentication failed. Returning to role selection.");
                cardLayout.show(mainPanel, "RoleSelection");
                return;
            }

            appointmentManagementPanel.getBookButton().setEnabled(false);
            appointmentManagementPanel.getMessageButton().setEnabled(false);

        } else if ("Patient".equals(role)) {
            appointmentManagementPanel.getConfirmButton().setEnabled(false);
            appointmentManagementPanel.getDeclineButton().setEnabled(false);
        }
    }


    private void bookAppointment() {
        try {
            String dateStr = appointmentManagementPanel.getDateInput();
            String details = appointmentManagementPanel.getDetailsInput();
            CREATIONAL_PATTERNS.Factory.Type type = appointmentManagementPanel.getSelectedType();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = sdf.parse(dateStr);

            if ("Patient".equals(userRole)) {
                Patient patient = new Patient(userName);
                Appointment appointment = AppointmentFactory.createAppointment(type, date, details, patient);
                appointmentList.add(appointment);

                patient.bookAppointment(appointment, facade);
                outputPanel.appendMessage("📌 " + userName + " booked an appointment on **" + sdf.format(date) + "**.");

            } else {
                JOptionPane.showMessageDialog(this, "⚠️ Only Patients can book appointments.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
        }
    }

    private void messageDoctor() {
        String message = JOptionPane.showInputDialog("Enter message to doctor:");

        if ("Patient".equals(userRole) && message != null && !message.trim().isEmpty()) {
            Patient patient = new Patient(userName);
            Doctor doctor = new Doctor("Kien");
            patient.sendMessageToDoctor(doctor, message);
            outputPanel.appendMessage("📩 " + userName + " messaged Dr. " + doctor.getName() + ": " + message);
        } else {
            JOptionPane.showMessageDialog(this, "⚠️ Only Patients can send messages.");
        }
    }

    private void confirmAppointment() {
        if (!"Doctor".equals(userRole)) {
            JOptionPane.showMessageDialog(this, "⚠️ Only Doctors can confirm appointments.");
            return;
        }

        if (appointmentList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ No appointments available to confirm.");
            return;
        }

        Appointment appointment = selectAppointment();
        if (appointment != null) {
            Doctor doctor = new Doctor(userName);
            doctor.confirmAppointment(appointment);
            outputPanel.appendMessage("✅ Dr. " + userName + " confirmed an appointment: " + appointment.getDetails());
        }
    }

    private void declineAppointment() {
        if (!"Doctor".equals(userRole)) {
            JOptionPane.showMessageDialog(this, "⚠️ Only Doctors can decline appointments.");
            return;
        }

        if (appointmentList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ No appointments available to decline.");
            return;
        }

        Appointment appointment = selectAppointment();
        if (appointment != null) {
            Doctor doctor = new Doctor(userName);
            doctor.declineAppointment(appointment);
            appointmentList.remove(appointment);
            outputPanel.appendMessage("❌ Dr. " + userName + " declined an appointment: " + appointment.getDetails());
        }
    }

    private void rescheduleAppointment() {
        try {
            String dateStr = appointmentManagementPanel.getDateInput();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date newDate = sdf.parse(dateStr);

            if (!"Doctor".equals(userRole)) {
                JOptionPane.showMessageDialog(this, "⚠️ Only Doctors can reschedule appointments.");
                return;
            }

            if (appointmentList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ No appointments available to reschedule.");
                return;
            }

            Appointment appointment = selectAppointment();
            if (appointment != null) {
                Doctor doctor = new Doctor(userName);
                doctor.rescheduleAppointment(appointment, newDate);
                outputPanel.appendMessage("🔄 Dr. " + userName + " rescheduled an appointment to **" + sdf.format(newDate) + "**.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
        }
    }

    private Appointment selectAppointment() {
        if (appointmentList.isEmpty()) return null;

        String[] appointmentDetails = appointmentList.stream()
                .map(a -> "📅 " + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(a.getDate()) + " - " + a.getDetails())
                .toArray(String[]::new);

        String selectedAppointment = (String) JOptionPane.showInputDialog(
                this, "Select an appointment:", "Appointment Selection",
                JOptionPane.QUESTION_MESSAGE, null, appointmentDetails, appointmentDetails[0]);

        return appointmentList.stream().filter(a -> selectedAppointment.contains(a.getDetails())).findFirst().orElse(null);
    }

    private boolean authenticateDoctor() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] message = {
                "👨‍⚕️ Username:", usernameField,
                "🔐 Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Doctor Login", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            List<String[]> dummyDoctors = List.of(
                    new String[]{"kien", "1234"},
                    new String[]{"drsmith", "pass"},
                    new String[]{"drjane", "secure"}
            );

            for (String[] doc : dummyDoctors) {
                if (doc[0].equals(username) && doc[1].equals(password)) {
                    userName = username; // Save the name globally
                    return true;
                }
            }
        }

        return false;
    }



}
