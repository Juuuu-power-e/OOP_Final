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
	private PToolBar pToolBar;
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
		
		this.pToolBar = new PToolBar();
		this.add(this.pToolBar, BorderLayout.NORTH);
		
		this.pSugangSincheongPanel = new PSugangSincheongPanel();
		this.add(this.pSugangSincheongPanel, BorderLayout.CENTER);

		LanguageManager.getInstance().registerObserver(pMenuBar);
		LanguageManager.getInstance().registerObserver(pToolBar);
		LanguageManager.getInstance().registerObserver(pSugangSincheongPanel);

	}

	public void initialize(VUser vUser) {
		this.vUser = vUser;
		this.setVisible(true);
		
		this.pMenuBar.initialize();
		this.pToolBar.initialize();
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
            }
            case changeLanguage -> {
				changeLanguage();
				updateText();
            }
            case accountInfo -> {
            }
            case showTimeTable -> {
            }
        }
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
