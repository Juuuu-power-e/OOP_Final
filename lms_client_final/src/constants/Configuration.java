package constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
	public static final String CONFIG_FILE_PATH = "lms_server_final/src/resources/config.properties";
	public static int PORT_NUM = 2732; // 기본값
	public static String ROOT_FILE_NAME = "root";
	public static int LOGIN_DIALOG_WIDTH = 300;
	public static int LOGIN_DIALOG_HEIGHT = 200;
	public static int MAIN_FRAME_WIDTH = 1000;
	public static int MAIN_FRAME_HEIGHT = 600;
	public static int LOGIN_DIALOG_SIZE_NAME_TEXT = 10;
	public static int LOGIN_DIALOG_SIZE_PW_TEXT = 10;
	public static final String miridamgiFilePostfix = "M";
	public static final String singcheongFilePostfix = "S";

	public static String[] supportedLanguages = { "English", "한국어", "Español", "Français", "Deutsch" };
	public static String[] locales = { "en", "ko", "es", "fr", "de" };


	static {
		loadConfig();
	}

	private static void loadConfig() {
		try (FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)) {
			Properties properties = new Properties();
			properties.load(fis);

			String port = properties.getProperty("client.port");
			if (port != null) {
				PORT_NUM = Integer.parseInt(port);
			}

		} catch (IOException | NumberFormatException e) {
			System.err.println("설정 파일 로드 중 오류 발생: " + e.getMessage());
		}
	}
}