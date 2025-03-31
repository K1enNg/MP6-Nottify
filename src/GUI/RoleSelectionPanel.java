package GUI;

import javax.swing.*;
import java.awt.*;

public class RoleSelectionPanel extends JPanel {
    private JComboBox<String> roleComboBox;
    private JTextField nameField;

    public RoleSelectionPanel() {
        setLayout(new GridLayout(2, 2, 10, 10));
        add(new JLabel("Select Role:"));
        roleComboBox = new JComboBox<>(new String[]{"Patient", "Doctor"});
        add(roleComboBox);

        add(new JLabel("Your Name:"));
        nameField = new JTextField();
        add(nameField);
    }

    public String getSelectedRole() {
        return (String) roleComboBox.getSelectedItem();
    }

    public String getUserName() {
        return nameField.getText();
    }
}
