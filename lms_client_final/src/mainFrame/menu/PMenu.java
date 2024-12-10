package mainFrame.menu;

import constants.LanguageManager;
import mainFrame.MyComponent;

import javax.swing.*;
import java.awt.event.ActionListener;

public class PMenu extends JMenu implements MyComponent {

    protected String menuTitle;
    protected ActionListener actionListener;

    public PMenu(String menuTitle, ActionListener actionListener) {
        super(LanguageManager.getInstance().get(menuTitle));
        this.menuTitle = menuTitle;
        setActionListener(actionListener);
        LanguageManager.getInstance().registerObserver(this);
    }

    @Override
    public void updateText() {
        setText(LanguageManager.getInstance().get(menuTitle));
    }

    @Override
    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }
}
