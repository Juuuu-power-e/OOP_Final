package main;

import gui.ServerGUI;

public class Main {

    private ServerMain serverMain;
    private ServerGUI serverGUI;

    public Main() {
        serverMain = new ServerMain();
        serverGUI = new ServerGUI();
    }

    public void initialize(){
        serverMain.initialize();
        serverGUI.associate(serverMain);
        serverGUI.initialize();
    }

    public void run(){
        serverGUI.run();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.initialize();
        main.run();
    }

}
