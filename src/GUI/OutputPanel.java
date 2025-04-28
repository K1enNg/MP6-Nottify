package GUI;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

class OutputPanel extends JPanel {
    private JTextArea outputArea;

    public OutputPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Activity Log"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        setPreferredSize(new Dimension(getWidth(), 150));

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setFont(new Font("Arial", Font.PLAIN, 14));
        outputArea.setBackground(new Color(245, 245, 245));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        // Add clear button
        JButton clearButton = new JButton("Clear Log");
        clearButton.addActionListener(e -> outputArea.setText(""));
        clearButton.setFocusPainted(false);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(clearButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void appendMessage(String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String timestamp = sdf.format(new Date());
        outputArea.append("[" + timestamp + "] " + message + "\n");

        // Scroll to bottom
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }
}
