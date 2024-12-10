package mainFrame.menu;

import constants.Constants;

import java.awt.event.ActionListener;

public class PSettingMenu extends PMenu {


	public PSettingMenu(String menuTitle, ActionListener actionListener) {
		super(menuTitle, actionListener);

		add(new PMenuItem("menu.setting.checkVersion", Constants.EActionCommand.checkVersion, actionListener));
		add(new PMenuItem("menu.setting.updateVersion", Constants.EActionCommand.updateVersion, actionListener));
		add(new PMenuItem("menu.setting.changePort", Constants.EActionCommand.changePort, actionListener));

	}
}
