package pomonitor.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ��ȡ��Ŀ�������ļ�
 * 
 * @author caihengyi
 *         2016��1��14�� ����2:30:48
 */
public class PropertiesReader {
	String result = "";
	InputStream inputStream;

	public String getPropertyByName(String propertyName) {
		try {
			Properties prop = new Properties();
			String propFileName = "pomonitor.properties";

			inputStream = getClass().getClassLoader().getResourceAsStream(
					propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '"
						+ propFileName + "' not found in the classpath");
			}

			// get the property value and print it out
			result = prop.getProperty(propertyName);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
