package mainFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.*;

import aspect.LogManager;
import constants.Configuration;
import constants.Constants;
import constants.LanguageManager;
import mainFrame.menu.PMenuBar;
import sugangSincheong.PSugangSincheongPanel;
import valueObject.VUser;

public class PMainFrame extends JFrame{
	// attributes
	private static final long serialVersionUID = 1L;	
	// components	
	private PMenuBar pMenuBar;
	private JLabel timeLabel;
	private PSugangSincheongPanel pSugangSincheongPanel;
	// utility
	private WindowListener windowsHandler;
	// value object
	private VUser vUser;
	private MenuActionListener menuActionListener;
	
	// constructor
	public PMainFrame() {
		// attributes
		this.setSize(Configuration.MAIN_FRAME_WIDTH, Configuration.MAIN_FRAME_HEIGHT);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);		
		
		this.setLayout(new BorderLayout());
		
		// utility
		this.windowsHandler = new WinodowsHandler();
		this.addWindowListener(this.windowsHandler);
		this.menuActionListener = new MenuActionListener();

		// components
		this.pMenuBar = new PMenuBar(menuActionListener);
		this.setJMenuBar(this.pMenuBar);

		JPanel statusPanel = new JPanel(new BorderLayout());
		this.timeLabel = new JLabel();
		statusPanel.add(timeLabel, BorderLayout.EAST);
		this.add(statusPanel, BorderLayout.NORTH);
		new TimeManager(timeLabel).startUpdatingTime();
		
		this.pSugangSincheongPanel = new PSugangSincheongPanel();
		this.add(this.pSugangSincheongPanel, BorderLayout.CENTER);

		LanguageManager.getInstance().registerObserver(pMenuBar);
		LanguageManager.getInstance().registerObserver(pSugangSincheongPanel);

	}

	public void initialize(VUser vUser) {
		this.vUser = vUser;
		this.setVisible(true);
		this.pMenuBar.initialize();
		new TimeManager(this.timeLabel);
		this.pSugangSincheongPanel.initialize(this.vUser);
	}
	private void finish() {
		this.pSugangSincheongPanel.finish();
		System.out.println("save");
	}

	private void handleAction(ActionEvent e) {
		switch (Constants.EActionCommand.valueOf(e.getActionCommand())) {
            case save -> {
				finish();
            }
            case print -> {
				printResult();
            }
            case checkVersion -> {
            }
            case updateVersion -> {
            }
            case changePort -> {
            }
            case changeTimeFormat -> {
				toggleTimeFormat();
            }
            case changeLanguage -> {
				changeLanguage();
				updateText();
            }
            case accountInfo -> {
				showAccountInfo();
            }
            case showTimeTable -> {
				TimetableHTMLGenerator.generateHTML(pSugangSincheongPanel.getResult());
//				TimetableSwing.displayTimetable(pSugangSincheongPanel.getResult());
            }
        }
	}
	private void toggleTimeFormat() {
		boolean currentFormat = LogManager.getInstance().isShowSeconds();
		LogManager.getInstance().setShowSeconds(!currentFormat);
		JOptionPane.showMessageDialog(this,
				LanguageManager.getInstance().get("dialog.changeTimeFormat.success"),
				LanguageManager.getInstance().get("dialog.success.title"),
				JOptionPane.INFORMATION_MESSAGE
		);
	}


	private void showAccountInfo() {
		// Create a JDialog for the popup
		JDialog dialog = new JDialog(this, "계정 정보", true);
		dialog.setSize(300, 200);
		dialog.setLayout(new BorderLayout());

		// Create a panel to hold user information
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));

		// Add labels and values for user information
		panel.add(new JLabel("ID:"));
		panel.add(new JLabel(vUser.getUserId()));

		panel.add(new JLabel("이름:"));
		panel.add(new JLabel(vUser.getName()));

		panel.add(new JLabel("주소:"));
		panel.add(new JLabel(vUser.getAddress()));

		// Add the panel to the dialog
		dialog.add(panel, BorderLayout.CENTER);

		// Add a button to close the dialog
		JButton closeButton = new JButton("닫기");
		closeButton.addActionListener(e -> dialog.dispose());
		dialog.add(closeButton, BorderLayout.SOUTH);

		// Set the dialog to be visible
		dialog.setLocationRelativeTo(this); // Center relative to the parent frame
		dialog.setVisible(true);
	}


	private void printResult() {
		PDFPrinter pdfPrinter = new PDFPrinter();
		pdfPrinter.print(pSugangSincheongPanel.getResult());
	}

	private void updateText() {

	}

	private void changeLanguage() {


		// 드롭다운 메뉴 생성
		JComboBox<String> languageDropdown = new JComboBox<>(Configuration.supportedLanguages);

		// 선택 다이얼로그
		int option = JOptionPane.showConfirmDialog(
				this,
				languageDropdown,
				LanguageManager.getInstance().get("dialog.changeLanguage.message"),
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE
		);

		if (option == JOptionPane.OK_OPTION) {
			int selectedIndex = languageDropdown.getSelectedIndex();
			String selectedLocale = Configuration.locales[selectedIndex];

			if (hasLanguagePack(selectedLocale)) {
				LanguageManager.getInstance().setLocale(new Locale(selectedLocale));
			}
		}
	}
	private boolean hasLanguagePack(String locale) {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("messages", new Locale(locale));
			return bundle.getLocale().getLanguage().equals(locale);
		} catch (MissingResourceException e) {
			return false;
		}
	}





	private class MenuActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			handleAction(e);
		}
	}
	
	private class WinodowsHandler implements WindowListener {
		@Override
		public void windowOpened(WindowEvent e) {
		}
		
		@Override
		public void windowClosing(WindowEvent e) {			
			finish();
		}
		
		@Override
		public void windowClosed(WindowEvent e) {
		}
		@Override
		public void windowIconified(WindowEvent e) {
		}
		@Override
		public void windowDeiconified(WindowEvent e) {
		}
		@Override
		public void windowActivated(WindowEvent e) {
		}
		@Override
		public void windowDeactivated(WindowEvent e) {
		}		
	}
}
