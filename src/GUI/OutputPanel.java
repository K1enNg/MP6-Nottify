package GUI;

import javax.swing.*;
import java.awt.*;

public class OutputPanel extends JPanel {
    private JTextArea outputArea;

    public OutputPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("📝 Activity Log"));
        setBackground(new Color(230, 230, 250)); // Lavender Background

        outputArea = new JTextArea(10, 50);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void appendMessage(String message) {
        outputArea.append(message + "\n");
    }
}
