package GUI;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;


public class StatusBar extends JPanel {
    private JLabel statusLabel;
    private JLabel timeLabel;
    private Timer timer;

    public StatusBar() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));
        setPreferredSize(new Dimension(getWidth(), 25));
        
        // Status message on the left
        statusLabel = new JLabel("Ready");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // Current time on the right
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        add(statusLabel, BorderLayout.WEST);
        add(timeLabel, BorderLayout.EAST);
        
        // Update time every second
        timer = new Timer(1000, e -> updateTime());
        timer.start();
        updateTime();
    }
    
    /**
     * Sets the status message in the status bar
     * @param status The status message to display
     */
    public void setStatus(String status) {
        statusLabel.setText(status);
    }
    
    /**
     * Updates the time display in the status bar
     */
    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        timeLabel.setText(sdf.format(new Date()));
    }
    
    /**
     * Cleans up resources when the component is no longer needed
     */
    public void cleanup() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }
}