package gui;

import aspect.LogManager;
import constants.LanguageManager;
import constants.TextObserver;
import main.ServerMain;

import javax.swing.*;
import java.awt.*;

public class ServerGUI extends JFrame implements TextObserver {
    private JTextArea logArea;
    private JLabel serverStatusLabel;
    private JLabel timeLabel;
    private JButton startButton;
    private JButton stopButton;
    private ServerControl serverControl;

    public ServerGUI() {
        // 기본 설정
        setTitle(LanguageManager.getInstance().get("window.title")); // 초기 언어 설정
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 상태 패널
        JPanel statusPanel = new JPanel(new BorderLayout());
        serverStatusLabel = new JLabel(LanguageManager.getInstance().get("label.status") + ": " + LanguageManager.getInstance().get("label.stopped"));
        timeLabel = new JLabel(); // 시간 라벨
        statusPanel.add(serverStatusLabel, BorderLayout.WEST);
        statusPanel.add(timeLabel, BorderLayout.EAST);

        // 버튼 패널
        JPanel buttonPanel = new JPanel();
        startButton = new JButton(LanguageManager.getInstance().get("button.start"));
        stopButton = new JButton(LanguageManager.getInstance().get("button.stop"));
        stopButton.setEnabled(false); // 초기 상태에서 비활성화
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        // 로그 영역
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);

        // 레이아웃 구성
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(statusPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // 초기화
        serverControl = new ServerControl(startButton, stopButton, serverStatusLabel);
        MenuManager menuManager = new MenuManager(this, logArea, serverControl);
        setJMenuBar(menuManager.createMenuBar());
        new TimeManager(timeLabel).startUpdatingTime();

        // 옵저버 등록
        LanguageManager.getInstance().registerObserver(this);
    }

    public void associate(ServerMain serverMain) {
        serverControl.setServerMain(serverMain);
    }

    public void initialize() {
        LogManager.getInstance().initialize(logArea);
    }

    public void run() {
        SwingUtilities.invokeLater(() -> {
            this.setVisible(true);
        });
    }

    @Override
    public void onLanguageChanged() {
        // 창 제목 업데이트
        setTitle(LanguageManager.getInstance().get("window.title"));

        // 상태 라벨 업데이트
        serverStatusLabel.setText(LanguageManager.getInstance().get("label.status") + ": " +
                (serverControl.isRunning() ? LanguageManager.getInstance().get("label.running") : LanguageManager.getInstance().get("label.stopped")));

        // 버튼 텍스트 업데이트
        startButton.setText(LanguageManager.getInstance().get("button.start"));
        stopButton.setText(LanguageManager.getInstance().get("button.stop"));
    }

    @Override
    public void dispose() {
        // 옵저버 해제
        LanguageManager.getInstance().unregisterObserver(this);
        super.dispose();
    }
}
