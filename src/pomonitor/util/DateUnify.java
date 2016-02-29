package pomonitor.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUnify {
	public static String DataUn(String dates) {
		String datatime = null;
		String datatimes = null;
		String reExpression = "[0-9]{4}\\-[0-9]+\\-[0-9]+";
		Pattern p = Pattern.compile(reExpression);
		Matcher date = p.matcher(dates);
		while (date.find()) {
			datatime = date.group();
		}
		if (datatime != null) {
			String[] Idatatime = datatime.split("-");
			if (Idatatime[1].length() < 2) {
				Idatatime[1] = "0" + Idatatime[1];
			}
			if (Idatatime[2].length() < 2) {
				Idatatime[2] = "0" + Idatatime[2];
			}
			datatimes = Idatatime[0] + "-" + Idatatime[1] + "-" + Idatatime[2];
		}
		return datatimes;
	}
}
