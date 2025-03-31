package GUI;

import javax.swing.*;

public class OutputPanel extends JPanel {
    private JTextArea outputArea;

    public OutputPanel() {
        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea));
    }

    public void appendMessage(String message) {
        outputArea.append(message + "\n");
    }
}
