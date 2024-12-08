package gui;

import aspect.LogManager;
import main.ServerMain;

import javax.swing.*;

public class ServerControl {
    private JButton startButton;
    private JButton stopButton;
    private JLabel serverStatusLabel;
    private ServerMain serverMain;
    private boolean bRunning;

    public ServerControl(JButton startButton, JButton stopButton, JLabel serverStatusLabel) {
        this.startButton = startButton;
        this.stopButton = stopButton;
        this.serverStatusLabel = serverStatusLabel;
        this.bRunning = false;

        startButton.addActionListener(e -> startServer());
        stopButton.addActionListener(e -> stopServer());
    }

    public void setServerMain(ServerMain serverMain) {
        this.serverMain = serverMain;
    }

    private void startServer() {
        LogManager.getInstance().log("서버가 시작되었습니다.");
        serverStatusLabel.setText("서버 상태: 실행 중");
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        serverMain.run();
        bRunning = true;
    }

    private void stopServer() {
        LogManager.getInstance().log("서버가 중지되었습니다.");
        serverStatusLabel.setText("서버 상태: 정지됨");
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        serverMain.stop();
        bRunning = false;
    }

    public boolean isRunning() {
        return bRunning;
    }
}
