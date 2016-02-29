package pomonitor.util;

public class ConsoleLog {
	public static void PrintInfo(Class clazz, String info) {
		System.out.println("###### Info from " + clazz.getName() + ": " + info);
	}

	public static void PrintErr(Class clazz, String info) {
		System.out
				.println("###### Error from " + clazz.getName() + ": " + info);
	}

	public static void PrintMat(Class clazz, double[][] mat, int width,
			int heigth) {
		System.out.println("###### Mat from " + clazz.getName());
		int AlignWidth = 5;
		System.out.println(StringMulti(
				AlignStrWithPlaceholder("*", "*", AlignWidth), width));
		for (int i = 0; i < heigth; i++) {
			for (int j = 0; j < width; j++) {
				System.out.print(AlignStrWithPlaceholder(mat[i][j] + "", " ",
						AlignWidth));
			}
			System.out.println();
		}
		System.out.println(StringMulti(
				AlignStrWithPlaceholder("*", "*", AlignWidth), width));
	}

	public static String StringMulti(String str, int count) {
		String retStr = "";
		if (str == "" || count == 0)
			return "";
		if (count < 2)
			return str;
		for (int i = 0; i < count; i++) {
			retStr = retStr + str;
		}
		return retStr;
	}

	public static String AlignStrWithPlaceholder(String str,
			String placeholder, int width) {
		if (str.length() >= width)
			return str;
		String placeholders = "";
		for (int i = 0; i < (width - str.length()); i++) {
			placeholders += placeholder;
		}
		return str + placeholders;
	}

	public static String AlignHanZiWithPlaceholder(String str,
			String placeholder, int width) {
		if (str.length() >= width)
			return str;
		String placeholders = "";
		for (int i = 0; i < (width - (str.length()) * 2); i++) {
			placeholders += placeholder;
		}
		return str + placeholders;
	}

}
