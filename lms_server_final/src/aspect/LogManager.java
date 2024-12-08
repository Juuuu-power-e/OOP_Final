package aspect;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogManager {
    private static final LogManager instance = new LogManager();
    private JTextArea logArea;
    private boolean showSeconds = true; // 시간 형식: 초 단위 포함 여부

    private LogManager() {}

    public static LogManager getInstance() {
        return instance;
    }

    public void initialize(JTextArea textArea) {
        this.logArea = textArea;
    }

    public void setShowSeconds(boolean showSeconds) {
        this.showSeconds = showSeconds;
    }

    public boolean isShowSeconds() { // showSeconds 필드의 현재 상태를 반환
        return showSeconds;
    }

    public void log(String message) {
        if (logArea != null) {
            String timestamp = getTimestamp();
            logArea.append("[" + timestamp + "] " + message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        }
    }

    private String getTimestamp() {
        String pattern = showSeconds ? "HH:mm:ss" : "HH:mm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(new Date());
    }
}