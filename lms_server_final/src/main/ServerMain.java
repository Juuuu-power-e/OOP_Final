package main;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

import aspect.ExceptionManager;
import aspect.LogManager;
import model.Dao;
import model.DaoDB;
import model.DaoFile;

import javax.swing.*;

public class ServerMain {

	private Skeleton skeleton;

	public ExceptionManager getExceptionManager() {
		return exceptionManager;
	}

	private ExceptionManager exceptionManager;
	private Dao dao;


	public ServerMain() {
		exceptionManager = new ExceptionManager();
	}

	public void initialize() {
		// 다이얼로그를 통한 Dao 선택
		String[] options = {"DB", "File"};
		int choice = JOptionPane.showOptionDialog(
				null,
				"데이터를 불러올 방식을 선택하세요",
				"DAO 선택",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE,
				null,
				options,
				options[0]
		);

		// 선택된 옵션에 따라 Dao 구현체 생성
		switch (choice) {
			case 0 -> {
				dao = new DaoDB();
				LogManager.getInstance().log("DaoDB selected.");
			}
			case 1 -> {
				dao = new DaoFile();
				LogManager.getInstance().log("DaoFile selected.");
			}
			default -> {
				JOptionPane.showMessageDialog(null, "No DAO selected. Exiting.");
				System.exit(0); // 선택하지 않으면 프로그램 종료
			}
		}
	}

	public void run() {
		try {
			this.skeleton = new Skeleton();
			this.skeleton.initialie(dao);
			this.skeleton.run();
		} catch (RemoteException | AlreadyBoundException e) {
			exceptionManager.process(e);
		}

	}


	public void stop() {
		skeleton.stop();
	}

}
