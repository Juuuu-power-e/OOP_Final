package mainFrame.menu;

import constants.Constants;

import java.awt.event.ActionListener;

public class PFileMenu extends PMenu {
	private static final long serialVersionUID = 1L;


	public PFileMenu(String menuTitle, ActionListener actionListener) {
		super(menuTitle, actionListener);

		add(new PMenuItem("menu.file.save", Constants.EActionCommand.save, actionListener));
		add(new PMenuItem("menu.file.print", Constants.EActionCommand.print, actionListener));
	}
}
