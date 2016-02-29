package pomonitor.analyse.segment;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author luoxu 2015��12��15�� ����1:44:49
 */
public class ReadData {
	/*
	 * �ӱ����ı���ȡ��������
	 */
	public static String getContentFromText(String filePath) {
		String Content = "";
		String fileName = filePath;
		File file = new File(fileName);
		Reader reader = null;
		try {
			int tempchar;
			reader = new InputStreamReader(new FileInputStream(file), "utf-8");
			while ((tempchar = reader.read()) != -1) {
				if (((char) tempchar) != '\r') {
					Content += (char) tempchar;
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Pattern p = Pattern.compile("\\s*|\t|\r|\n|");
		Matcher m = p.matcher(Content);
		Content = m.replaceAll("");
		return Content;
	}
}
