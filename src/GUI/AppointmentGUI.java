package GUI;

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
    private JComboBox<Type> typeComboBox;
    private JTextField dateField, detailsField;
    private JTextArea outputArea;
    private ScheduleFacade facade;
    private NotificationSystem notifier;

    public AppointmentGUI() {
        facade = new ScheduleFacade();
        notifier = new NotificationSystem();
        notifier.subscribe(new User("Kien"));

        setTitle("Smart Appointment Scheduler");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== Form Panel =====
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Create Appointment"));

        formPanel.add(new JLabel("Appointment Type:"));
        typeComboBox = new JComboBox<>(Type.values());
        formPanel.add(typeComboBox);

        formPanel.add(new JLabel("Date (yyyy-MM-dd HH:mm):"));
        dateField = new JTextField();
        formPanel.add(dateField);

        formPanel.add(new JLabel("Details:"));
        detailsField = new JTextField();
        formPanel.add(detailsField);

        JButton bookButton = new JButton("Book Appointment");
        formPanel.add(bookButton);

        // Placeholder
        formPanel.add(new JLabel());

        add(formPanel, BorderLayout.NORTH);

        // ===== Output Area =====
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // ===== Button Action =====
        bookButton.addActionListener(e -> bookAppointment());

        setVisible(true);
    }

    private void bookAppointment() {
        try {
            Type type = (Type) typeComboBox.getSelectedItem();
            String dateStr = dateField.getText();
            String details = detailsField.getText();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = sdf.parse(dateStr);

//            Appointment appointment = new AppointmentBuilder(type)
//                    .setDate(date)
//                    .setDetails(details)
//                    .build();

//            facade.bookAppointment(appointment);

            notifier.notifyUser("Your appointment is confirmed for: " + sdf.format(date));

            outputArea.append("Booked " + type + " appointment on " + sdf.format(date) + " with details: " + details + "\n");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
