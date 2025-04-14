package GUI;

import CREATIONAL_PATTERNS.Factory.Appointment;
import CREATIONAL_PATTERNS.Factory.AppointmentFactory;
import CREATIONAL_PATTERNS.User.*;
import STRUCTURAL_PATTERNS.Facade.ScheduleFacade;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;

public class AppointmentGUI extends JFrame {
    // Custom colors for improved visual design
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color ACCENT_COLOR = new Color(231, 76, 60);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    private static final Font HEADER_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 18);
    private static final Font BODY_FONT = new Font("Arial", Font.PLAIN, 14);

    private RoleSelectionPanel roleSelectionPanel;
    private AppointmentManagementPanel appointmentManagementPanel;
    private OutputPanel outputPanel;
    private StatusBar statusBar;
    private ScheduleFacade facade;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private String userRole;
    private String userName;
    private List<Appointment> appointmentList;
    private JButton logoutButton;

    public AppointmentGUI() {
        appointmentList = new ArrayList<>();
        facade = new ScheduleFacade();

        setTitle("NOTTIFY - Appointment Scheduler");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(800, 600));

        // Set the look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Custom UI settings
        UIManager.put("Button.background", PRIMARY_COLOR);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Panel.background", BACKGROUND_COLOR);
        UIManager.put("Label.foreground", TEXT_COLOR);
        UIManager.put("TextField.background", Color.WHITE);
        UIManager.put("TextArea.background", Color.WHITE);

        // Create the header panel with logout button
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        roleSelectionPanel = new RoleSelectionPanel();
        appointmentManagementPanel = new AppointmentManagementPanel();
        outputPanel = new OutputPanel();
        statusBar = new StatusBar();

        // Setup action listeners
        setupActionListeners();

        // Set up the main content panel with card layout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(roleSelectionPanel, "RoleSelection");
        mainPanel.add(appointmentManagementPanel, "AppointmentManagement");

        // Add panels to frame
        add(mainPanel, BorderLayout.CENTER);
        add(outputPanel, BorderLayout.SOUTH);
        add(statusBar, BorderLayout.PAGE_END);

        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("NOTTIFY");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Appointment Management System");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(Color.WHITE);

        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);

        headerPanel.add(titlePanel, BorderLayout.WEST);

        // Create logout button
        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setBackground(new Color(231, 76, 60));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(192, 57, 43), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Initially hide the logout button (shown only when logged in)
        logoutButton.setVisible(false);

        // Add action listener to logout button
        logoutButton.addActionListener(e -> logout());

        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutButton.setBackground(new Color(231, 76, 60).brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutButton.setBackground(new Color(231, 76, 60));
            }
        });

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        rightPanel.add(logoutButton);

        headerPanel.add(rightPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private void setupActionListeners() {
        // Button action listeners
        appointmentManagementPanel.getBookButton().addActionListener(e -> bookAppointment());
        appointmentManagementPanel.getMessageButton().addActionListener(e -> messageDoctor());
        appointmentManagementPanel.getConfirmButton().addActionListener(e -> confirmAppointment());
        appointmentManagementPanel.getDeclineButton().addActionListener(e -> declineAppointment());
        appointmentManagementPanel.getRescheduleButton().addActionListener(e -> rescheduleAppointment());
        roleSelectionPanel.addContinueButtonListener(e -> proceedToMainScreen());

        // Role selection listener
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


    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            // Clear user data
            userName = null;
            userRole = null;

            // Reset UI elements
//            roleSelectionPanel.resetFields();
            appointmentManagementPanel.clearInputFields();
            logoutButton.setVisible(false);

            // Switch to role selection screen
            cardLayout.show(mainPanel, "RoleSelection");
            statusBar.setStatus("Logged out successfully");

            // Optionally clear the output panel
            // outputPanel.clearLog();
        }
    }

    private void proceedToMainScreen() {
        userName = roleSelectionPanel.getUserName();
        userRole = roleSelectionPanel.getSelectedRole();

        if (userName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Please enter your name.", "Input Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        updateUIForRole(userRole);

        if ("Doctor".equals(userRole)) {
            appointmentManagementPanel.setWelcomeText("👨‍⚕️ Dr. " + userName);
        } else {
            appointmentManagementPanel.setWelcomeText("👤 Patient: " + userName);
        }

        // Show the logout button when logged in
        logoutButton.setVisible(true);

        cardLayout.show(mainPanel, "AppointmentManagement");
        statusBar.setStatus("Logged in as " + (userRole.equals("Doctor") ? "Dr. " : "") + userName);
    }

    private void updateUIForRole(String role) {
        if ("Doctor".equals(role)) {
            boolean authenticated = authenticateDoctor();
            if (!authenticated) {
                JOptionPane.showMessageDialog(this, "❌ Authentication failed. Returning to role selection.",
                        "Authentication Failed", JOptionPane.ERROR_MESSAGE);
                cardLayout.show(mainPanel, "RoleSelection");
                return;
            }

            appointmentManagementPanel.getBookButton().setEnabled(false);
            appointmentManagementPanel.getMessageButton().setEnabled(false);
            appointmentManagementPanel.getConfirmButton().setEnabled(true);
            appointmentManagementPanel.getDeclineButton().setEnabled(true);
            appointmentManagementPanel.getRescheduleButton().setEnabled(true);

        } else if ("Patient".equals(role)) {
            appointmentManagementPanel.getBookButton().setEnabled(true);
            appointmentManagementPanel.getMessageButton().setEnabled(true);
            appointmentManagementPanel.getConfirmButton().setEnabled(false);
            appointmentManagementPanel.getDeclineButton().setEnabled(false);
            appointmentManagementPanel.getRescheduleButton().setEnabled(false);
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
                statusBar.setStatus("Appointment booked successfully");

                // Provide visual feedback
                JOptionPane.showMessageDialog(this,
                        "Appointment successfully booked for " + sdf.format(date),
                        "Booking Confirmed",
                        JOptionPane.INFORMATION_MESSAGE);

                // Clear input fields
                appointmentManagementPanel.clearInputFields();
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ Only Patients can book appointments.",
                        "Access Denied", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage(),
                    "Booking Error", JOptionPane.ERROR_MESSAGE);
            statusBar.setStatus("Appointment booking failed");
        }
    }

    private void messageDoctor() {
        if (!"Patient".equals(userRole)) {
            JOptionPane.showMessageDialog(this, "⚠️ Only Patients can send messages.",
                    "Access Denied", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Create a custom dialog for messaging
        JDialog messageDialog = new JDialog(this, "Message to Doctor", true);
        messageDialog.setLayout(new BorderLayout(10, 10));
        messageDialog.setSize(450, 300);
        messageDialog.setLocationRelativeTo(this);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(PRIMARY_COLOR);
        JLabel headerLabel = new JLabel("Send Message to Doctor");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(TITLE_FONT);
        headerPanel.add(headerLabel);

        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextArea messageArea = new JTextArea(8, 30);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setFont(BODY_FONT);
        JScrollPane scrollPane = new JScrollPane(messageArea);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton sendButton = new JButton("Send Message");
        JButton cancelButton = new JButton("Cancel");

        sendButton.addActionListener(e -> {
            String message = messageArea.getText();
            if (message != null && !message.trim().isEmpty()) {
                Patient patient = new Patient(userName);
                Doctor doctor = new Doctor("Kien");
                patient.sendMessageToDoctor(doctor, message);
                outputPanel.appendMessage("📩 " + userName + " messaged Dr. " + doctor.getName() + ": " + message);
                statusBar.setStatus("Message sent to Dr. " + doctor.getName());
                messageDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(messageDialog, "Please enter a message.",
                        "Empty Message", JOptionPane.WARNING_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> messageDialog.dispose());

        buttonPanel.add(sendButton);
        buttonPanel.add(cancelButton);

        contentPanel.add(new JLabel("Enter your message:"), BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        messageDialog.add(headerPanel, BorderLayout.NORTH);
        messageDialog.add(contentPanel, BorderLayout.CENTER);

        messageDialog.setVisible(true);
    }

    private void confirmAppointment() {
        if (!"Doctor".equals(userRole)) {
            JOptionPane.showMessageDialog(this, "⚠️ Only Doctors can confirm appointments.",
                    "Access Denied", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (appointmentList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ No appointments available to confirm.",
                    "No Appointments", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Appointment appointment = selectAppointment("Select an appointment to confirm");
        if (appointment != null) {
            Doctor doctor = new Doctor(userName);
            doctor.confirmAppointment(appointment);
            outputPanel.appendMessage("✅ Dr. " + userName + " confirmed an appointment: " + appointment.getDetails());
            statusBar.setStatus("Appointment confirmed successfully");

            JOptionPane.showMessageDialog(this,
                    "Appointment has been confirmed successfully.",
                    "Confirmation Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void declineAppointment() {
        if (!"Doctor".equals(userRole)) {
            JOptionPane.showMessageDialog(this, "⚠️ Only Doctors can decline appointments.",
                    "Access Denied", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (appointmentList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ No appointments available to decline.",
                    "No Appointments", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Appointment appointment = selectAppointment("Select an appointment to decline");
        if (appointment != null) {
            int option = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to decline this appointment?",
                    "Confirm Decline",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (option == JOptionPane.YES_OPTION) {
                Doctor doctor = new Doctor(userName);
                doctor.declineAppointment(appointment);
                appointmentList.remove(appointment);
                outputPanel.appendMessage("❌ Dr. " + userName + " declined an appointment: " + appointment.getDetails());
                statusBar.setStatus("Appointment declined");
            }
        }
    }

    private void rescheduleAppointment() {
        if (!"Doctor".equals(userRole)) {
            JOptionPane.showMessageDialog(this, "⚠️ Only Doctors can reschedule appointments.",
                    "Access Denied", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (appointmentList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ No appointments available to reschedule.",
                    "No Appointments", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Appointment appointment = selectAppointment("Select an appointment to reschedule");
        if (appointment != null) {
            try {
                // Create a custom dialog for rescheduling
                JDialog rescheduleDialog = new JDialog(this, "Reschedule Appointment", true);
                rescheduleDialog.setLayout(new BorderLayout(10, 10));
                rescheduleDialog.setSize(400, 200);
                rescheduleDialog.setLocationRelativeTo(this);

                JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                headerPanel.setBackground(PRIMARY_COLOR);
                JLabel headerLabel = new JLabel("Reschedule Appointment");
                headerLabel.setForeground(Color.WHITE);
                headerLabel.setFont(TITLE_FONT);
                headerPanel.add(headerLabel);

                JPanel contentPanel = new JPanel(new GridBagLayout());
                contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.WEST;
                gbc.insets = new Insets(5, 5, 5, 5);

                JLabel currentDateLabel = new JLabel("Current Date: " +
                        new SimpleDateFormat("yyyy-MM-dd HH:mm").format(appointment.getDate()));
                contentPanel.add(currentDateLabel, gbc);

                gbc.gridy++;
                JLabel newDateLabel = new JLabel("New Date (yyyy-MM-dd HH:mm):");
                contentPanel.add(newDateLabel, gbc);

                gbc.gridx = 1;
                JTextField newDateField = new JTextField(16);
                contentPanel.add(newDateField, gbc);

                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                JButton confirmButton = new JButton("Confirm");
                JButton cancelButton = new JButton("Cancel");

                confirmButton.addActionListener(e -> {
                    try {
                        String dateStr = newDateField.getText();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Date newDate = sdf.parse(dateStr);

                        Doctor doctor = new Doctor(userName);
                        doctor.rescheduleAppointment(appointment, newDate);
                        outputPanel.appendMessage("🔄 Dr. " + userName + " rescheduled an appointment to **" + sdf.format(newDate) + "**.");
                        statusBar.setStatus("Appointment rescheduled successfully");

                        JOptionPane.showMessageDialog(this,
                                "Appointment successfully rescheduled to " + sdf.format(newDate),
                                "Reschedule Confirmed",
                                JOptionPane.INFORMATION_MESSAGE);

                        rescheduleDialog.dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(rescheduleDialog,
                                "❌ Error: " + ex.getMessage(),
                                "Invalid Date Format",
                                JOptionPane.ERROR_MESSAGE);
                    }
                });

                cancelButton.addActionListener(e -> rescheduleDialog.dispose());

                buttonPanel.add(confirmButton);
                buttonPanel.add(cancelButton);

                rescheduleDialog.add(headerPanel, BorderLayout.NORTH);
                rescheduleDialog.add(contentPanel, BorderLayout.CENTER);
                rescheduleDialog.add(buttonPanel, BorderLayout.SOUTH);

                rescheduleDialog.setVisible(true);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage(),
                        "Rescheduling Error", JOptionPane.ERROR_MESSAGE);
                statusBar.setStatus("Appointment rescheduling failed");
            }
        }
    }

    private Appointment selectAppointment(String title) {
        if (appointmentList.isEmpty()) return null;

        JDialog selectionDialog = new JDialog(this, title, true);
        selectionDialog.setLayout(new BorderLayout(10, 10));
        selectionDialog.setSize(500, 300);
        selectionDialog.setLocationRelativeTo(this);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(PRIMARY_COLOR);
        JLabel headerLabel = new JLabel(title);
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(TITLE_FONT);
        headerPanel.add(headerLabel);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Appointment appointment : appointmentList) {
            listModel.addElement("📅 " + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(appointment.getDate()) +
                    " - " + appointment.getDetails());
        }

        JList<String> appointmentList = new JList<>(listModel);
        appointmentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        appointmentList.setFont(BODY_FONT);
        JScrollPane scrollPane = new JScrollPane(appointmentList);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton selectButton = new JButton("Select");
        JButton cancelButton = new JButton("Cancel");

        final Appointment[] selected = {null};

        selectButton.addActionListener(e -> {
            int index = appointmentList.getSelectedIndex();
            if (index != -1) {
                selected[0] = this.appointmentList.get(index);
                selectionDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(selectionDialog,
                        "Please select an appointment.",
                        "No Selection",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> selectionDialog.dispose());

        buttonPanel.add(selectButton);
        buttonPanel.add(cancelButton);

        selectionDialog.add(headerPanel, BorderLayout.NORTH);
        selectionDialog.add(scrollPane, BorderLayout.CENTER);
        selectionDialog.add(buttonPanel, BorderLayout.SOUTH);

        selectionDialog.setVisible(true);

        return selected[0];
    }

    private boolean authenticateDoctor() {
        // Create a custom login dialog
        JDialog loginDialog = new JDialog(this, "Doctor Login", true);
        loginDialog.setLayout(new BorderLayout(10, 10));
        loginDialog.setSize(400, 250);
        loginDialog.setLocationRelativeTo(this);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(PRIMARY_COLOR);
        JLabel headerLabel = new JLabel("👨‍⚕️ Doctor Authentication");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(TITLE_FONT);
        headerPanel.add(headerLabel);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel usernameLabel = new JLabel("Username:");
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JTextField usernameField = new JTextField(15);
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        JLabel passwordLabel = new JLabel("Password:");
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JPasswordField passwordField = new JPasswordField(15);
        formPanel.add(passwordField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton loginButton = new JButton("Login");
        JButton cancelButton = new JButton("Cancel");

        final boolean[] authenticated = {false};

        loginButton.addActionListener(e -> {
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
                    authenticated[0] = true;
                    loginDialog.dispose();
                    return;
                }
            }

            JOptionPane.showMessageDialog(loginDialog,
                    "Invalid username or password. Please try again.",
                    "Authentication Failed",
                    JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        });

        cancelButton.addActionListener(e -> loginDialog.dispose());

        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);

        loginDialog.add(headerPanel, BorderLayout.NORTH);
        loginDialog.add(formPanel, BorderLayout.CENTER);
        loginDialog.add(buttonPanel, BorderLayout.SOUTH);

        // Set default button and focus
        loginDialog.getRootPane().setDefaultButton(loginButton);
        SwingUtilities.invokeLater(() -> usernameField.requestFocusInWindow());

        loginDialog.setVisible(true);

        return authenticated[0];
    }

    // Enhanced Role Selection Panel
    class RoleSelectionPanel extends JPanel {
        private JTextField nameField;
        private JComboBox<String> roleComboBox;
        private JButton continueButton;

        public RoleSelectionPanel() {
            setLayout(new BorderLayout(20, 20));
            setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

            // Welcome panel
            JPanel welcomePanel = new JPanel(new BorderLayout());
            welcomePanel.setOpaque(false);

            JLabel welcomeLabel = new JLabel("Welcome to NOTTIFY");
            welcomeLabel.setFont(HEADER_FONT);
            welcomeLabel.setForeground(PRIMARY_COLOR);
            welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JLabel subtitleLabel = new JLabel("Your Appointment Scheduling System");
            subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            subtitleLabel.setForeground(SECONDARY_COLOR);
            subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

            welcomePanel.add(welcomeLabel, BorderLayout.CENTER);
            welcomePanel.add(subtitleLabel, BorderLayout.SOUTH);

            // Form panel with input fields
            JPanel formPanel = new JPanel();
            formPanel.setLayout(new GridBagLayout());
            formPanel.setOpaque(false);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 10, 10, 10);

            // Name input
            gbc.gridx = 0;
            gbc.gridy = 0;
            JLabel nameLabel = new JLabel("Your Name:");
            nameLabel.setFont(BODY_FONT);
            formPanel.add(nameLabel, gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            nameField = new JTextField(20);
            nameField.setFont(BODY_FONT);
            formPanel.add(nameField, gbc);

            // Role selection
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 0.0;
            JLabel roleLabel = new JLabel("I am a:");
            roleLabel.setFont(BODY_FONT);
            formPanel.add(roleLabel, gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            String[] roles = {"Patient", "Doctor" };
            roleComboBox = new JComboBox<>(roles);
            roleComboBox.setFont(BODY_FONT);
            formPanel.add(roleComboBox, gbc);

            // Continue button
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            continueButton = new JButton("Continue");
            continueButton.setFont(BODY_FONT);
            continueButton.setBackground(PRIMARY_COLOR);
            continueButton.setForeground(Color.WHITE);
            continueButton.setFocusPainted(false);
            continueButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            continueButton.putClientProperty("JButton.buttonType", "roundRect");
            formPanel.add(continueButton, gbc);

            // Create a card with shadow effect
            JPanel card = new JPanel(new BorderLayout(20, 20));
            card.setBorder(BorderFactory.createCompoundBorder(
                    new SoftBevelBorder(BevelBorder.RAISED),
                    BorderFactory.createEmptyBorder(20, 20, 20, 20)));
            card.setBackground(Color.WHITE);

            card.add(welcomePanel, BorderLayout.NORTH);
            card.add(formPanel, BorderLayout.CENTER);

            // Center the card
            JPanel centerPanel = new JPanel(new GridBagLayout());
            centerPanel.setOpaque(false);
            centerPanel.add(card);

            add(centerPanel, BorderLayout.CENTER);
        }

        public void addContinueButtonListener(ActionListener listener) {
            continueButton.addActionListener(listener);
        }

        public String getUserName() {
            return nameField.getText().trim();
        }

        public String getSelectedRole() {
            return (String) roleComboBox.getSelectedItem();
        }

        public JComboBox<String> getRoleComboBox() {
            return roleComboBox;
        }
    }
}