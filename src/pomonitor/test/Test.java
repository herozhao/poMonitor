package pomonitor.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pomonitor.analyse.articletend.ArticleSplier;
import pomonitor.analyse.articletend.SentenceSplier;
import pomonitor.entity.EntityManagerHelper;
import pomonitor.entity.News;
import pomonitor.entity.NewsDAO;
import pomonitor.entity.NewsTend;
import pomonitor.entity.NewsTendDAO;
import pomonitor.util.JsonContentGetter;
import pomonitor.util.SomeStaticValues;
import pomonitor.util.UrlSender;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class Test {
	@org.junit.Test
	public void test() {
		NewsTendDAO ntd = new NewsTendDAO();
		NewsTend nt = new NewsTend();
		Date date = Calendar.getInstance().getTime();
		nt.setDate(date);
		nt.setId(1);
		nt.setNewsId(15);
		nt.setTendclass(0);
		nt.setTendscore(1.0f);
		nt.setWeb("xinlang");
		EntityManagerHelper.beginTransaction();
		ntd.save(nt);
		EntityManagerHelper.commit();
	}

	@org.junit.Test
	public void test2() {
		NewsTendDAO ntd = new NewsTendDAO();
		List<NewsTend> list = ntd.findBetweenDate("2012-10-10", "2015-8-8");
		System.out.println(list.size());

	}

	@org.junit.Test
	public void testJsion() {
		// String

		String jsonStr = UrlSender
				.sendGetForJson("http://api.ltp-cloud.com/analysis/?api_key=15k332P7iaVazlMV1ZFXUyqyoMmP7PVcgQICeDTc&pattern=all&format=json&text=2015%E5%B9%B4%E5%88%A9%E7%BE%A4%E9%98%B3%E5%85%89%E5%8A%A9%E5%AD%A6%E8%A1%8C%E5%8A%A8%EF%BC%88%E6%B9%96%E5%8D%97%EF%BC%89%E7%AC%AC%E4%BA%8C%E6%89%B9%E5%85%B130%E5%90%8D%E6%8B%9F%E8%B5%84%E5%8A%A9%E5%AD%A6%E7%94%9F%E5%90%8D%E5%8D%95%E7%A1%AE%E5%AE%9A%EF%BC%8C%E7%BB%84%E5%A7%94%E4%BC%9A%E4%BA%88%E4%BB%A5%E5%85%AC%E7%A4%BA%EF%BC%8C%E6%8E%A5%E5%8F%97%E7%A4%BE%E4%BC%9A%E7%9B%91%E7%9D%A3%EF%BC%88%E8%A7%81%E8%A1%A8%EF%BC%89%E3%80%82%E8%BF%9930%E5%90%8D%E5%93%81%E5%AD%A6%E5%85%BC%E4%BC%98%E7%9A%84%E5%AF%92%E9%97%A8%E5%AD%A6%E5%AD%90%E6%98%AF%E7%BB%84%E5%A7%94%E4%BC%9A%E4%BB%8E%E4%BC%97%E5%A4%9A%E6%8A%A5%E5%90%8D%E7%94%B3%E8%AF%B7%E8%80%85%E4%B8%AD%E7%BB%8F%E4%B8%A5%E6%A0%BC%E7%AD%9B%E9%80%89%E7%A1%AE%E5%AE%9A%E7%9A%84%EF%BC%8C%E6%AF%8F%E4%BA%BA%E5%B0%86%E8%8E%B7%E5%BE%975000%E5%85%83%E8%B5%84%E5%8A%A9%E3%80%82%E5%8A%A9%E5%AD%A6%E9%87%91%E5%8F%91%E6%94%BE%E6%97%B6%E9%97%B4%E4%B8%BA8%E6%9C%88%E4%B8%8B%E6%97%AC%EF%BC%8C%E8%AF%B7%E5%8F%97%E5%8A%A9%E5%AD%A6%E7%94%9F%E7%9C%8B%E5%88%B0%E5%85%AC%E7%A4%BA%E5%90%8E%E5%8F%8A%E6%97%B6%E4%B8%8E%E6%9C%AC%E6%8A%A5%E8%81%94%E7%B3%BB%E7%A1%AE%E8%AE%A4%E3%80%82%E5%85%B6%E4%BD%9950%E5%90%8D%E5%8F%97%E5%8A%A9%E8%80%85%E5%90%8D%E5%8D%95%E5%B0%86%E5%9C%A8%E8%BF%91%E6%97%A5%E5%88%86%E6%89%B9%E5%85%AC%E5%B8%83%E3%80%82%E5%90%8C%E6%97%B6%EF%BC%8C%E8%87%AA%E4%BB%8A%E6%97%A5%E8%B5%B7%EF%BC%8C2015%E5%B9%B4%E5%88%A9%E7%BE%A4%E9%98%B3%E5%85%89%E5%8A%A9%E5%AD%A6%E8%A1%8C%E5%8A%A8%E5%81%9C%E6%AD%A2%E6%8E%A5%E5%8F%97%E6%8A%A5%E5%90%8D%E7%94%B3%E8%AF%B7%E3%80%82%0A%E5%8A%A9%E5%AD%A6%E7%83%AD%E7%BA%BF%EF%BC%9A%0A%E7%BC%96%E5%8F%B7+%E5%A7%93%E5%90%8D+%E6%80%A7%E5%88%AB+%E8%BA%AB%E4%BB%BD%E8%AF%81+%E4%BD%8F%E5%9D%80+%E9%AB%98%E8%80%83%E6%88%90%E7%BB%A9+%E5%BD%95%E5%8F%96%E9%99%A2%E6%A0%A1+%E8%B5%84%E5%8A%A9%E5%8E%9F%E5%9B%A0%0A1+%E9%82%B1%E4%BC%9F+%E7%94%B7+431382****12070174+%E6%B6%9F%E6%BA%90%E5%B8%82%E9%BE%99%E5%A1%98%E9%95%87+668%28%E5%8F%A6%E6%9C%8950%E5%88%86%E8%87%AA%E4%B8%BB%E6%8B%9B%E7%94%9F%E5%8A%A0%E5%88%86%EF%BC%89+%E6");
		System.out.println(jsonStr);
		// String jsonStr =
		// getJsonContent("http://api.ltp-cloud.com/analysis/?api_key=15k332P7iaVazlMV1ZFXUyqyoMmP7PVcgQICeDTc&text=%E6%88%91%E6%98%AF%E4%B8%AD%E5%9B%BD%E4%BA%BA%E3%80%82&pattern=all&format=json");
		// System.out.println(jsonStr);
		// jsonStr = jsonStr.substring(4, jsonStr.length() - 3);
		// System.out.println("~~~~~" + jsonStr + "~~~");
		//
		// List<TendWord> list = JSON.parseArray(jsonStr, TendWord.class);
		//
		// // List list2=JSON.parseArray(jsonStr);
		//
		// System.out.println(list.get(1).getArg().get(1).getBeg());
		// System.out.println(list.get(1).getArg().get(1).getEnd());

	}

	public static String getJsonContent(String urlStr) {
		try {// 获取HttpURLConnection连接对象
			URL url = new URL(urlStr);
			HttpURLConnection httpConn = (HttpURLConnection) url
					.openConnection();
			// 设置连接属性
			httpConn.setConnectTimeout(3000);
			httpConn.setDoInput(true);
			httpConn.setRequestMethod("GET");
			// 获取相应码
			int respCode = httpConn.getResponseCode();
			if (respCode == 200) {
				return ConvertStream2Json(httpConn.getInputStream());
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	private static String ConvertStream2Json(InputStream inputStream) {
		String jsonStr = "";
		// ByteArrayOutputStream相当于内存输出流
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		// 将输入流转移到内存输出流中
		try {
			while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
				out.write(buffer, 0, len);
			}
			// 将内存流转换为字符串
			jsonStr = new String(out.toByteArray(), "utf-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr;
	}

	@org.junit.Test
	public void testDate() {
		Date date = new Date();
		System.out.println(date.toGMTString());
		System.out.println(date.toLocaleString());
		System.out.println(date.toString());
	}

	@org.junit.Test
	public void testJson() {
		NewsDAO nd = new NewsDAO();
		News news = nd.findById(1);
		String url = SomeStaticValues.url;
		String content = news.getAllContent();
		try {
			content = URLEncoder.encode(content, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String jsonStrResult = JsonContentGetter.getJsonContent(url + content);
		System.out.println(jsonStrResult);
		JSONArray rootList = JSON.parseArray(jsonStrResult);

		JSONArray fatherList = rootList.getJSONArray(0);

		JSONArray thisList = fatherList.getJSONArray(0);
		System.out.println("当前jsonStr:" + thisList.toJSONString());
	}

	@org.junit.Test
	public void testArticleSplier() {
		NewsDAO nd = new NewsDAO();
		News news = nd.findById(1);
		String content = news.getAllContent();
		ArticleSplier splier = new ArticleSplier();
		splier.spil(content);
	}

	@org.junit.Test
	public void testSentenceSplier() {
		new SentenceSplier().spil("你长得人高马大，貌美如花##郴州第一人民医院中心医院妇科##健康守护神#");
	}

}
