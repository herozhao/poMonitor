package pomonitor.analyse.articletend;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import pomonitor.analyse.entity.TendSentence;
import pomonitor.analyse.entity.TendWord;
import pomonitor.util.SomeStaticValues;
import pomonitor.util.UrlSender;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * 句子分析器，将String形态的文章转变为自定义的TendSentence列表
 * 
 * @author zhaolong 2015年12月16日 下午9:27:44
 */
public class ArticleSplier {

	/**
	 * 将String形态的文章预处理为自定义的TendSentence列表
	 * 
	 * @param sentence
	 * @return
	 */
	public List<TendSentence> spil(String aricleStr) {
		List<TendSentence> sentenceList = new ArrayList<>();
		try {
			// 如果文章超过了最大所能分析的字数限制，就做截断处理，没办法之举动
			if (aricleStr.length() > 6876) {
				aricleStr = aricleStr.substring(0, 6876);
				int last = aricleStr.lastIndexOf("。");
				aricleStr = aricleStr.substring(0, last + 1);
			}
			// System.out.println(aricleStr);
			// System.out.println(aricleStr.length());
			// System.out.println(aricleStr.getBytes().length);
			// 对汉字做转码处理
			aricleStr = URLEncoder.encode(aricleStr, "utf-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("句子转换编码集错误转换失败");
			e.printStackTrace();
		}

		// 拼凑url请求的参数
		// String urlStr = utfUrlStr + aricleStr;
		// String jsonStr = JsonContentGetter.getJsonContent(urlStr);
		// System.out.println(urlStr);
		// String jsonStr = UrlSender.sendGetForJson(urlStr);

		String jsonStr = UrlSender.sendPostForJson(SomeStaticValues.seedUrl,
				SomeStaticValues.params + aricleStr);

		System.out.println("jsonStr:" + jsonStr);
		// 句子的序号
		int count = 0;
		JSONArray rootList = JSON.parseArray(jsonStr);
		for (int i = 0; i < rootList.size(); i++) {
			JSONArray fatherList = rootList.getJSONArray(i);
			for (int j = 0; j < fatherList.size(); j++) {
				JSONArray thisList = fatherList.getJSONArray(j);
				String thisListString = thisList.toJSONString();

				// jsonStr=jsonStr.substring(2,jsonStr.length()-2);
				// System.out.println("一句话Json"+thisListString);
				List<TendWord> wordList = JSON.parseArray(thisListString,
						TendWord.class);
				// 如果句子足够长则存入文章对象用来作分析
				if (wordList.size() > 5) {
					TendSentence sentence = new TendSentence();
					sentence.setId(count++);
					sentence.setWords(wordList);
					sentenceList.add(sentence);
					for (TendWord tw : wordList) {
						System.out.println("id:" + tw.getId());
						System.out.println("cont:" + tw.getCont());
						System.out.println("ne:" + tw.getNe());
						System.out.println("parent:" + tw.getParent());
						System.out.println("pos:" + tw.getPos());
						System.out.println("args" + tw.getArg());
					}
				}
			}
		}
		return sentenceList;
	}
}
