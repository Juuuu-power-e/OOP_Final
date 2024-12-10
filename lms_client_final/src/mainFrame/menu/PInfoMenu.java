package mainFrame.menu;

import constants.Constants;

import java.awt.event.ActionListener;

public class PInfoMenu extends PMenu{
    public PInfoMenu(String menuTitle, ActionListener actionListener) {
        super(menuTitle, actionListener);

        add(new PMenuItem("menu.info.account", Constants.EActionCommand.accountInfo, actionListener));
        add(new PMenuItem("menu.info.showTimeTable", Constants.EActionCommand.showTimeTable, actionListener));

    }
}
