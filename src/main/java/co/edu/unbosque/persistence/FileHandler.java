package co.edu.unbosque.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileHandler {
	private static Properties prop;

	public static Properties getProperties(InputStream file) {
		prop = new Properties();
		try {
			prop.load(file);
		} catch (IOException e) {
			return null;
		}
		return prop;
	}
}
