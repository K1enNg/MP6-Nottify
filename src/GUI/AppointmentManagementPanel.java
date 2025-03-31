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
        setLayout(new GridLayout(6, 2, 10, 10)); // Increased rows to fit combo box
        setBorder(BorderFactory.createTitledBorder("Appointment Management"));

        add(new JLabel("Appointment Type:"));
        typeJComboBox = new JComboBox<>(Type.values());  // ✅ FIXED: Initialized properly
        add(typeJComboBox);

        add(new JLabel("Date (yyyy-MM-dd HH:mm):"));
        dateField = new JTextField();
        add(dateField);

        add(new JLabel("Details:"));
        detailsField = new JTextField();
        add(detailsField);

        bookButton = new JButton("Book Appointment");
        messageButton = new JButton("Message Doctor");
        confirmButton = new JButton("Confirm Appointment");
        declineButton = new JButton("Decline Appointment");
        rescheduleButton = new JButton("Reschedule Appointment");

        add(bookButton);
        add(messageButton);
        add(confirmButton);
        add(declineButton);
        add(rescheduleButton);
    }

    public String getDateInput() {
        return dateField.getText();
    }

    public String getDetailsInput() {
        return detailsField.getText();
    }

    public Type getSelectedType() {
        return (Type) typeJComboBox.getSelectedItem(); // ✅ FIXED: Now `typeJComboBox` is properly initialized
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
