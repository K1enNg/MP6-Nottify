package GUI;

import CREATIONAL_PATTERNS.Factory.AppointmentFactory;
import CREATIONAL_PATTERNS.Factory.Type;

import javax.swing.*;
import java.awt.*;

public class AppointmentManagementPanel extends JPanel {
    private JTextField dateField, detailsField;
    private JComboBox<Type> typeJComboBox;
    private JButton bookButton, messageButton, confirmButton, declineButton, rescheduleButton;

    public AppointmentManagementPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Manage Your Appointments"));
        setBackground(new Color(245, 245, 245)); // Light Gray Background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        add(new JLabel("📅 Date (yyyy-MM-dd HH:mm):"), gbc);
        gbc.gridy++;
        dateField = new JTextField(15);
        add(dateField, gbc);

        gbc.gridy++;
        add(new JLabel("📝 Details:"), gbc);
        gbc.gridy++;
        detailsField = new JTextField(15);
        add(detailsField, gbc);

        gbc.gridy++;
        bookButton = new JButton("📌 Book Appointment");
        bookButton.setBackground(new Color(60, 179, 113)); // Green
        bookButton.setForeground(Color.WHITE);
        add(bookButton, gbc);

        gbc.gridy++;
        messageButton = new JButton("📩 Message Doctor");
        messageButton.setBackground(new Color(70, 130, 180)); // Steel Blue
        messageButton.setForeground(Color.WHITE);
        add(messageButton, gbc);

        gbc.gridy++;
        confirmButton = new JButton("✅ Confirm Appointment");
        confirmButton.setBackground(new Color(46, 139, 87)); // Dark Green
        confirmButton.setForeground(Color.BLACK);
        add(confirmButton, gbc);

        gbc.gridy++;
        declineButton = new JButton("❌ Decline Appointment");
        declineButton.setBackground(new Color(178, 34, 34)); // Firebrick Red
        declineButton.setForeground(Color.BLACK);
        add(declineButton, gbc);

        gbc.gridy++;
        rescheduleButton = new JButton("🔄 Reschedule Appointment");
        rescheduleButton.setBackground(new Color(255, 140, 0)); // Dark Orange
        rescheduleButton.setForeground(Color.BLACK);
        add(rescheduleButton, gbc);
    }

    public String getDateInput() {
        return dateField.getText();
    }

    public String getDetailsInput() {
        return detailsField.getText();
    }

    public Type getSelectedType() {
        return (Type) typeJComboBox.getSelectedItem();
    }

    public JButton getBookButton() {
        return bookButton;
    }

    public JButton getMessageButton() {
        return messageButton;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JButton getDeclineButton() {
        return declineButton;
    }

    public JButton getRescheduleButton() {
        return rescheduleButton;
    }
}
