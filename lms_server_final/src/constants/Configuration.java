package constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
	public static final String CONFIG_FILE_PATH = "lms_server_final/src/resources/config.properties";
	public static int PORT_NUM = 2732; // 기본값
	public static String SERIAL_NUM = "1";

	static {
		loadConfig();
	}

	private static void loadConfig() {
		try (FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)) {
			Properties properties = new Properties();
			properties.load(fis);

			String port = properties.getProperty("server.port");
			if (port != null) {
				PORT_NUM = Integer.parseInt(port);
			}

			String serial = properties.getProperty("server.serial");
			if (serial != null) {
				SERIAL_NUM = serial;
			}
		} catch (IOException | NumberFormatException e) {
			System.err.println("설정 파일 로드 중 오류 발생: " + e.getMessage());
		}
	}
}
