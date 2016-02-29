package pomonitor.analyse.articletend;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import pomonitor.analyse.entity.TendWord;
import pomonitor.util.JsonContentGetter;
import pomonitor.util.SomeStaticValues;

import com.alibaba.fastjson.JSON;

/**
 * 句子分析器，将String形态的句子转变为自定义的TendSentence对象
 * 
 * @author zhaolong 2015年12月16日 下午9:27:44
 */
public class SentenceSplier {

	/**
	 * 将String形态的句子预处理为自定义的TendSentence对象(只能是一句话) >>>>>>> branch 'develop' of
	 * 
	 * @param sentence
	 * @return
	 */
	public List<TendWord> spil(String sentence) {
		String utfUrlStr = "";
		try {
			utfUrlStr = SomeStaticValues.url;
			System.out.println(sentence);

			// 对汉字做转码处理
			sentence = URLEncoder.encode(sentence + "。", "utf-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("句子转换编码集错误转换失败");
			e.printStackTrace();
		}

		// 拼凑url请求的参数
		String urlStr = utfUrlStr + sentence;
		String jsonStr = JsonContentGetter.getJsonContent(urlStr);
		jsonStr = jsonStr.substring(4, jsonStr.length() - 3);
		System.out.println("分析完的jsonStr:" + jsonStr);
		List<TendWord> list = JSON.parseArray(jsonStr, TendWord.class);
		for (TendWord tw : list) {
			System.out.println("id:" + tw.getId());
			System.out.println("cont:" + tw.getCont());
			System.out.println("ne:" + tw.getNe());
			System.out.println("parent:" + tw.getParent());
			System.out.println("pos:" + tw.getPos());
			System.out.println("args" + tw.getArg());
		}
		return list;
	}
}
