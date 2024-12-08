package gui;

import javax.swing.*;
import java.awt.*;
import aspect.LogManager;
import main.ServerMain;

public class ServerGUI extends JFrame {
    private JTextArea logArea;
    private JLabel serverStatusLabel;
    private JLabel timeLabel;
    private JButton startButton;
    private JButton stopButton;
    private ServerControl serverControl;

    public ServerGUI() {
        // 기본 설정
        setTitle("Server Log Monitor");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 상태 패널
        JPanel statusPanel = new JPanel(new BorderLayout());
        serverStatusLabel = new JLabel("서버 상태: 정지됨");
        timeLabel = new JLabel();
        statusPanel.add(serverStatusLabel, BorderLayout.WEST);
        statusPanel.add(timeLabel, BorderLayout.EAST);

        // 버튼 패널
        JPanel buttonPanel = new JPanel();
        startButton = new JButton("서버 시작");
        stopButton = new JButton("서버 중지");
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
}
