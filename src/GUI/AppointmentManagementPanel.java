package GUI;

import java.awt.*;
import javax.swing.*;

class AppointmentManagementPanel extends JPanel {
    final private JLabel welcomeLabel;
    final private JTextField dateField;
    final private JTextArea detailsArea;
    final private JComboBox<String> typeComboBox;
    final private JComboBox<String> doctorComboBox;
    final private JButton bookButton;
    final private JButton messageButton;
    final private JButton confirmButton;
    final private JButton declineButton;
    final private JButton rescheduleButton;

    public AppointmentManagementPanel() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Welcome panel
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        welcomePanel.setBackground(new Color(236, 240, 241));
        
        welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(41, 128, 185));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        welcomePanel.add(welcomeLabel, BorderLayout.WEST);
        
        // Main content panel with input form and action buttons
        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        
        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Appointment Information"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        
        // Date/time input
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel dateLabel = new JLabel("Date (yyyy-MM-dd HH:mm):");
        dateField = new JTextField(20);
        datePanel.add(dateLabel);
        datePanel.add(dateField);
        
        // Appointment type selection
        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel typeLabel = new JLabel("Appointment Type:");
        String[] types = {"InPerson", "Virtual"};
        typeComboBox = new JComboBox<>(types);
        typePanel.add(typeLabel);
        typePanel.add(typeComboBox);
        
        // Doctor selection
        JPanel doctorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel doctorLabel = new JLabel("Select Doctor:");
        doctorComboBox = new JComboBox<>(new String[]{"Dr. Kien", "Dr. Smith", "Dr. Jane"});
        doctorPanel.add(doctorLabel);
        doctorPanel.add(doctorComboBox);
        
        // Details input
        JPanel detailsPanel = new JPanel(new BorderLayout());
        JLabel detailsLabel = new JLabel("Appointment Details:");
        detailsArea = new JTextArea(5, 30);
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(detailsArea);
        detailsPanel.add(detailsLabel, BorderLayout.NORTH);
        detailsPanel.add(scrollPane, BorderLayout.CENTER);
        
        inputPanel.add(datePanel);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(typePanel);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(doctorPanel);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(detailsPanel);
        
        // Action buttons panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 0));
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Actions"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        
        // Create styled buttons
        bookButton = createStyledButton("Book", new Color(46, 204, 113));
        messageButton = createStyledButton("Message", new Color(52, 152, 219));
        confirmButton = createStyledButton("Confirm", new Color(155, 89, 182));
        declineButton = createStyledButton("Decline", new Color(231, 76, 60));
        rescheduleButton = createStyledButton("Reschedule", new Color(243, 156, 18));
        
        buttonPanel.add(bookButton);
        buttonPanel.add(messageButton);
        buttonPanel.add(confirmButton);
        buttonPanel.add(declineButton);
        buttonPanel.add(rescheduleButton);
        
        contentPanel.add(inputPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(welcomePanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    public void setWelcomeText(String text) {
        welcomeLabel.setText(text);
    }
    
    public String getDateInput() {
        return dateField.getText().trim();
    }
    
    public String getDetailsInput() {
        return detailsArea.getText().trim();
    }
    
    public CREATIONAL_PATTERNS.Factory.Type getSelectedType() {
        String selected = (String) typeComboBox.getSelectedItem();
        return CREATIONAL_PATTERNS.Factory.Type.valueOf(selected);
    }
    
    public void clearInputFields() {
        dateField.setText("");
        detailsArea.setText("");
        doctorComboBox.setSelectedIndex(0);
    }
    
    public JButton getBookButton() {
        return bookButton;
    }
    
    public JButton getMessageButton() {
        return messageButton;
    }

    public JButton getRescheduleButton() {
        return rescheduleButton;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }
    
    public JButton getDeclineButton() {
        return declineButton;
    }
    
    public String getSelectedDoctor() {
        return (String) doctorComboBox.getSelectedItem();
    }
}