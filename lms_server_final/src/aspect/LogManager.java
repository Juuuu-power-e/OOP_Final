package aspect;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogManager {
    private static final String LOG_FILE_PATH = "lms_server_final/log";
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

    public void flushLogFile() {
        // 로그 디렉토리 생성
        try {
            Path logDir = Paths.get(LOG_FILE_PATH);
            if (!Files.exists(logDir)) {
                Files.createDirectories(logDir);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to create log directory: " + e.getMessage());
            return;
        }

        // 파일명 생성 (현재 시간 기반)
        String fileName = "log_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt";
        Path logFile = Paths.get(LOG_FILE_PATH, fileName);

        // 로그 파일 저장
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile.toFile()))) {
            writer.write(logArea.getText());
            System.out.println("Log saved to file: " + logFile.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to write log file: " + e.getMessage());
        }

    }

    public void log(Object[] objects) {
        if (logArea != null) {
            String timestamp = getTimestamp();
            logArea.append("[" + timestamp + "]  Exception!!  \n" );
            for (Object object : objects) {
                logArea.append("        " + object.toString() + "\n");
            }
            logArea.setCaretPosition(logArea.getDocument().getLength());
        }
    }
}