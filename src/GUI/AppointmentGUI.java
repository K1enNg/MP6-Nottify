package GUI;

import BEHAVIORAL_PATTERNS.Command.CancelAppointmentCommand;
import BEHAVIORAL_PATTERNS.Command.Command;
import BEHAVIORAL_PATTERNS.Command.RescheduleAppointmentCommand;
import BEHAVIORAL_PATTERNS.Observer.NotificationSystem;
import BEHAVIORAL_PATTERNS.Observer.User;
import CREATIONAL_PATTERNS.Builder.AppointmentBuilder;
import CREATIONAL_PATTERNS.Factory.Appointment;
import CREATIONAL_PATTERNS.Factory.AppointmentFactory;
import CREATIONAL_PATTERNS.Factory.Type;
import STRUCTURAL_PATTERNS.Facade.ScheduleFacade;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppointmentGUI extends JFrame {
    private JComboBox<CREATIONAL_PATTERNS.Factory.Type> typeComboBox;
    private JTextField dateField, detailsField;
    private JTextArea outputArea;
    private ScheduleFacade facade;
    private NotificationSystem notifier;
    private Appointment currentAppointment; // Stores the last booked appointment

    public AppointmentGUI() {
        facade = new ScheduleFacade();
        notifier = new NotificationSystem();
        notifier.subscribe(new User("Kien"));

        setTitle("NOTTIFY");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== Form Panel =====
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Appointment Manager"));

        formPanel.add(new JLabel("Appointment Type:"));
        typeComboBox = new JComboBox<>(CREATIONAL_PATTERNS.Factory.Type.values());
        formPanel.add(typeComboBox);

        formPanel.add(new JLabel("Date (yyyy-MM-dd HH:mm):"));
        dateField = new JTextField();
        formPanel.add(dateField);

        formPanel.add(new JLabel("Details:"));
        detailsField = new JTextField();
        formPanel.add(detailsField);

        JButton bookButton = new JButton("Book Appointment");
        JButton rescheduleButton = new JButton("Reschedule");
        JButton cancelButton = new JButton("Cancel");

        formPanel.add(bookButton);
        formPanel.add(rescheduleButton);
        formPanel.add(cancelButton);

        add(formPanel, BorderLayout.NORTH);

        // ===== Output Area =====
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // ===== Button Actions =====
        bookButton.addActionListener(e -> bookAppointment());
        rescheduleButton.addActionListener(e -> rescheduleAppointment());
        cancelButton.addActionListener(e -> cancelAppointment());

        setVisible(true);
    }

    private void bookAppointment() {
        try {
            CREATIONAL_PATTERNS.Factory.Type type = (CREATIONAL_PATTERNS.Factory.Type) typeComboBox.getSelectedItem();
            String dateStr = dateField.getText();
            String details = detailsField.getText();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = sdf.parse(dateStr);

            currentAppointment = new AppointmentBuilder(type)
                    .setDate(date)
                    .setDetails(details)
                    .build();

            facade.bookAppointment(currentAppointment);
            notifier.notifyUser("Your appointment is confirmed for: " + sdf.format(date));

            outputArea.append("Booked " + type + " appointment on " + sdf.format(date) + " with details: " + details + "\n");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void rescheduleAppointment() {
        if (currentAppointment == null) {
            JOptionPane.showMessageDialog(this, "No appointment to reschedule.");
            return;
        }

        try {
            String dateStr = dateField.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date newDate = sdf.parse(dateStr);

            Command rescheduleCommand = new RescheduleAppointmentCommand(currentAppointment, newDate);
            rescheduleCommand.execute();

            JOptionPane.showMessageDialog(this, "Appointment rescheduled to: " + sdf.format(newDate));

            outputArea.append("Rescheduled appointment to: " + sdf.format(newDate) + "\n");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void cancelAppointment() {
        if (currentAppointment == null) {
            JOptionPane.showMessageDialog(this, "No appointment to cancel.");
            return;
        }

        Command cancelCommand = new CancelAppointmentCommand(currentAppointment);
        cancelCommand.execute();

        JOptionPane.showMessageDialog(this, "Appointment canceled.");

        outputArea.append("Canceled appointment on: " + currentAppointment.getDate() + "\n");

        currentAppointment = null; // Reset appointment after cancellation
    }
}
