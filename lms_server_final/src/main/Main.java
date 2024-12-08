package main;

import gui.ServerGUI;
import test.TestExceptionFrame;

public class Main {

    private ServerMain serverMain;
    private ServerGUI serverGUI;
    private TestExceptionFrame testExceptionFrame;

    public Main() {
        serverMain = new ServerMain();
        serverGUI = new ServerGUI();
        testExceptionFrame = new TestExceptionFrame();
    }

    public void initialize(){
        serverMain.initialize();
        serverGUI.associate(serverMain);
        serverGUI.initialize();
    }

    public void run(){
        serverGUI.run();
        testExceptionFrame.run();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.initialize();
        main.run();
    }

}
