package mainFrame.menu;

import constants.Constants;
import constants.LanguageManager;
import mainFrame.MyComponent;

import javax.swing.*;
import java.awt.event.ActionListener;

public class PMenuItem extends JMenuItem implements MyComponent {

    protected String menuItemTitle;


    public PMenuItem(String menuItemTitle, Constants.EActionCommand eActionCommand, ActionListener actionListener) {
        super(LanguageManager.getInstance().get(menuItemTitle));
        setActionListener(actionListener);
        setActionCommand(eActionCommand.name());
        this.menuItemTitle = menuItemTitle;
        LanguageManager.getInstance().registerObserver(this);
    }

    @Override
    public void updateText() {
        setText(LanguageManager.getInstance().get(menuItemTitle));
    }

    @Override
    public void setActionListener(ActionListener actionListener) {
        this.addActionListener(actionListener);
    }
}
