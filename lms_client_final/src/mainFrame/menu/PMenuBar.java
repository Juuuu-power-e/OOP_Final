package mainFrame.menu;

import mainFrame.MyComponent;

import javax.swing.JMenuBar;
import java.awt.event.ActionListener;

public class PMenuBar extends JMenuBar implements MyComponent {

	public PMenuBar(ActionListener actionListener) {
		add(new PFileMenu("menu.file", actionListener));
		add(new PSettingMenu("menu.setting", actionListener));
		add(new PAppearanceMenu("menu.appearance", actionListener));
		add(new PInfoMenu("menu.info", actionListener));
	}

	public void initialize() {

	}

	@Override
	public void updateText() {

	}

	@Override
	public void setActionListener(ActionListener actionListener) {

	}
}
