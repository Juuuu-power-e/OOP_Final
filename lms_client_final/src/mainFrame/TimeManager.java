package mainFrame;

import aspect.LogManager;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeManager {
    private JLabel timeLabel;

    public TimeManager(JLabel timeLabel) {
        this.timeLabel = timeLabel;
    }

    public void startUpdatingTime() {
        Timer timer = new Timer(1000, e -> updateTime());
        timer.start();
    }

    private void updateTime() {
        String pattern = LogManager.getInstance().isShowSeconds() ? "yyyy-MM-dd HH:mm:ss" : "yyyy-MM-dd HH:mm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        timeLabel.setText(dateFormat.format(new Date()));
    }
}
