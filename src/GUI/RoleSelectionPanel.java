package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RoleSelectionPanel extends JPanel {
    private JTextField nameField;
    private JComboBox<String> roleComboBox;
    private JButton continueButton;

    public RoleSelectionPanel() {
        setLayout(new GridLayout(3, 2, 10, 10));
        setBorder(BorderFactory.createTitledBorder("User Role Selection"));

        add(new JLabel("Enter Your Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Select Role:"));
        roleComboBox = new JComboBox<>(new String[]{"Patient", "Doctor"});
        add(roleComboBox);

        continueButton = new JButton("Continue");
        add(continueButton);
    }

    public String getUserName() {
        return nameField.getText();
    }

    public String getSelectedRole() {
        return (String) roleComboBox.getSelectedItem();
    }

    public void addContinueButtonListener(ActionListener listener) {
        continueButton.addActionListener(listener);
    }
}
