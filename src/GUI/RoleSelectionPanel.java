package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RoleSelectionPanel extends JPanel {
    private JTextField nameField;
    private JComboBox<String> roleComboBox;
    private JButton continueButton;

    public RoleSelectionPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Select Your Role"));
        setBackground(new Color(240, 248, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel logoLabel = new JLabel(new ImageIcon("logo.png"));
        add(logoLabel, gbc);

        gbc.gridy++;
        add(new JLabel("Enter Your Name:"), gbc);
        gbc.gridy++;
        nameField = new JTextField(15);
        add(nameField, gbc);

        gbc.gridy++;
        add(new JLabel("Select Your Role:"), gbc);
        gbc.gridy++;
        roleComboBox = new JComboBox<>(new String[]{"Patient", "Doctor"});
        add(roleComboBox, gbc);

        gbc.gridy++;
        continueButton = new JButton("Continue");
        continueButton.setBackground(new Color(100, 149, 237)); // Cornflower Blue
        continueButton.setForeground(Color.BLACK);
        add(continueButton, gbc);
    }

    public String getUserName() {
        return nameField.getText().trim();
    }

    public String getSelectedRole() {
        return (String) roleComboBox.getSelectedItem();
    }

    public void addContinueButtonListener(ActionListener listener) {
        continueButton.addActionListener(listener);
    }

    public JComboBox<String> getRoleComboBox() {
        return roleComboBox;
    }
}
