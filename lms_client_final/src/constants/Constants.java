package constants;

import java.util.Locale;
import java.util.ResourceBundle;

public class Constants {

	public enum EPResultPanel {
		gangjwaNo("EPResultPanel.gangjwaNo"),
		gangjwaName("EPResultPanel.gangjwaName");

		private final String key;

		EPResultPanel(String key) {
			this.key = key;
		}

		public String getText() {
			return LanguageManager.getInstance().get(key);
		}
	}

	public enum EPHeaderPanel {
		greetings("EPHeaderPanel.greetings");

		private final String key;

		EPHeaderPanel(String key) {
			this.key = key;
		}

		public String getText() {
			return LanguageManager.getInstance().get(key);
		}
	}

	public enum EPGangjwaSelectionPanel {
		gangjwaNo("EPGangjwaSelectionPanel.gangjwaNo"),
		gangjwaName("EPGangjwaSelectionPanel.gangjwaName"),
		damdangGyosu("EPGangjwaSelectionPanel.damdangGyosu"),
		hakjeom("EPGangjwaSelectionPanel.hakjeom"),
		time("EPGangjwaSelectionPanel.time");

		private final String key;

		EPGangjwaSelectionPanel(String key) {
			this.key = key;
		}

		public String getText() {
			return LanguageManager.getInstance().get(key);
		}
	}

	public enum EPHakgwaSelectionPanel {
		campus("EPHakgwaSelectionPanel.campus"),
		college("EPHakgwaSelectionPanel.college"),
		hakgwa("EPHakgwaSelectionPanel.hakgwa");

		private final String key;

		EPHakgwaSelectionPanel(String key) {
			this.key = key;
		}

		public String getText() {
			return LanguageManager.getInstance().get(key);
		}
	}

	public enum ELoginDialog {
		nameLabel("ELoginDialog.nameLabel"),
		passwordLabel("ELoginDialog.passwordLabel"),
		okButtonLabel("ELoginDialog.okButtonLabel"),
		cancelButtonLabel("ELoginDialog.cancelButtonLabel"),
		loginFailed("ELoginDialog.loginFailed");

		private final String key;

		ELoginDialog(String key) {
			this.key = key;
		}

		public String getText() {
			return LanguageManager.getInstance().get(key);
		}
	}

	public enum EActionCommand {
		save,
		print,
		checkVersion,
		updateVersion,
		changePort,
		changeTimeFormat,
		changeLanguage,
		accountInfo,
		showTimeTable
	}

}
