package mainFrame.menu;

import constants.Constants;

import java.awt.event.ActionListener;

public class PAppearanceMenu extends PMenu{
    public PAppearanceMenu(String menuTitle, ActionListener actionListener) {
        super(menuTitle, actionListener);

        add(new PMenuItem("menu.appearance.changeTimeFormat", Constants.EActionCommand.changeTimeFormat, actionListener));
        add(new PMenuItem("menu.appearance.changeLanguage", Constants.EActionCommand.changeLanguage, actionListener));
    }
}
