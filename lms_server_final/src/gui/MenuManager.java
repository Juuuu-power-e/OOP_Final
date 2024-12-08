package gui;

import aspect.LogManager;
import constants.Configuration;
import main.ServerMain;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class MenuManager {
    private JFrame parent;
    private JTextArea logArea;
    private ServerControl serverControl;

    public MenuManager(JFrame parent, JTextArea logArea, ServerControl serverControl) {
        this.parent = parent;
        this.logArea = logArea;
        this.serverControl = serverControl;
    }

    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // 설정 메뉴
        JMenu settingsMenu = new JMenu("설정");
        JMenuItem changePortItem = new JMenuItem("포트 변경");
        changePortItem.addActionListener(e -> changePort());
        JMenuItem changeSerialItem = new JMenuItem("시리얼 번호 변경");
        changeSerialItem.addActionListener(e -> changeSerialNumber());
        settingsMenu.add(changePortItem);
        settingsMenu.add(changeSerialItem);

        // 로그 메뉴
        JMenu logMenu = new JMenu("로그");
        JMenuItem clearLogItem = new JMenuItem("로그 삭제");
        clearLogItem.addActionListener(e -> logArea.setText(""));
        JMenuItem saveLogItem = new JMenuItem("로그 저장");
        saveLogItem.addActionListener(e -> saveLogToFile());
        logMenu.add(clearLogItem);
        logMenu.add(saveLogItem);

        // 표시 메뉴
        JMenu displayMenu = new JMenu("표시");
        JMenuItem changeLanguageItem = new JMenuItem("언어 변경");
        JMenuItem changeTimeFormatItem = new JMenuItem("시간 형식 변경");
        changeTimeFormatItem.addActionListener(e -> toggleTimeFormat());
        displayMenu.add(changeLanguageItem);
        displayMenu.add(changeTimeFormatItem);

        // 메뉴바 구성
        menuBar.add(settingsMenu);
        menuBar.add(logMenu);
        menuBar.add(displayMenu);

        return menuBar;
    }

    private void changePort() {
        if (serverControl.isRunning()) {
            JOptionPane.showMessageDialog(parent, "서버가 실행 중일 때는 포트를 변경할 수 없습니다.", "변경 불가", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String newPort = JOptionPane.showInputDialog(parent, "새 포트를 입력하세요 (1~65535):");
        if (newPort == null || newPort.isEmpty()) return;

        try {
            int port = Integer.parseInt(newPort);
            if (port < 1 || port > 65535) {
                JOptionPane.showMessageDialog(parent, "포트는 1~65535 사이의 숫자여야 합니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
                return;
            }

            updateConfig("server.port", newPort);
            JOptionPane.showMessageDialog(parent, "포트가 성공적으로 변경되었습니다.", "변경 완료", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(parent, "유효한 숫자를 입력하세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void changeSerialNumber() {
        if (serverControl.isRunning()) {
            JOptionPane.showMessageDialog(parent, "서버가 실행 중일 때는 시리얼 번호를 변경할 수 없습니다.", "변경 불가", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String newSerial = JOptionPane.showInputDialog(parent, "새 시리얼 번호를 입력하세요:");
        if (newSerial == null || newSerial.isEmpty()) return;

        updateConfig("server.serial", newSerial);
        JOptionPane.showMessageDialog(parent, "시리얼 번호가 성공적으로 변경되었습니다.", "변경 완료", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateConfig(String key, String value) {
        Properties properties = new Properties();

        // 기존 설정 파일의 내용을 먼저 읽어오기
        try (FileInputStream fis = new FileInputStream(Configuration.CONFIG_FILE_PATH)) {
            properties.load(fis);
        } catch (IOException ex) {
            // 설정 파일이 없는 경우 기본 상태로 진행
            System.err.println("기존 설정 파일을 읽는 중 오류 발생. 새 파일을 생성합니다.");
        }

        // 변경된 값 업데이트
        properties.setProperty(key, value);

        // 업데이트된 내용을 파일에 저장
        try (FileOutputStream fos = new FileOutputStream(Configuration.CONFIG_FILE_PATH)) {
            properties.store(fos, "Updated " + key);
            JOptionPane.showMessageDialog(parent, "설정이 성공적으로 업데이트되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
            LogManager.getInstance().log("Updated " + key +" to " + value);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(parent, "설정 파일을 업데이트하는 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveLogToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("로그 파일 저장");

        // 기본 확장자를 txt로 설정
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Files (*.txt)", "txt"));

        int option = fileChooser.showSaveDialog(parent);
        if (option == JFileChooser.APPROVE_OPTION) {
            try {
                // 선택한 파일의 경로 가져오기
                java.io.File file = fileChooser.getSelectedFile();

                // 파일 확장자가 .txt가 아니면 추가
                if (!file.getName().toLowerCase().endsWith(".txt")) {
                    file = new java.io.File(file.getAbsolutePath() + ".txt");
                }

                // 파일 저장
                java.nio.file.Files.write(file.toPath(), logArea.getText().getBytes());
                JOptionPane.showMessageDialog(parent, "로그가 저장되었습니다.\n파일 경로: " + file.getAbsolutePath());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, "로그 저장에 실패했습니다.");
            }
        }
    }

    private void toggleTimeFormat() {
        boolean currentFormat = LogManager.getInstance().isShowSeconds();
        LogManager.getInstance().setShowSeconds(!currentFormat);
        JOptionPane.showMessageDialog(parent, "시간 형식이 변경되었습니다.");
    }
}
