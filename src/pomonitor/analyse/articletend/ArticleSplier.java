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
 * ���ӷ���������String��̬������ת��Ϊ�Զ����TendSentence�б�
 * 
 * @author zhaolong 2015��12��16�� ����9:27:44
 */
public class ArticleSplier {

	/**
	 * ��String��̬������Ԥ����Ϊ�Զ����TendSentence�б�
	 * 
	 * @param sentence
	 * @return
	 */
	public List<TendSentence> spil(String aricleStr) {
		List<TendSentence> sentenceList = new ArrayList<>();
		try {
			// ������³�����������ܷ������������ƣ������ضϴ���û�취֮�ٶ�
			if (aricleStr.length() > 6876) {
				aricleStr = aricleStr.substring(0, 6876);
				int last = aricleStr.lastIndexOf("��");
				aricleStr = aricleStr.substring(0, last + 1);
			}
			// System.out.println(aricleStr);
			// System.out.println(aricleStr.length());
			// System.out.println(aricleStr.getBytes().length);
			// �Ժ�����ת�봦��
			aricleStr = URLEncoder.encode(aricleStr, "utf-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("����ת�����뼯����ת��ʧ��");
			e.printStackTrace();
		}

		// ƴ��url����Ĳ���
		// String urlStr = utfUrlStr + aricleStr;
		// String jsonStr = JsonContentGetter.getJsonContent(urlStr);
		// System.out.println(urlStr);
		// String jsonStr = UrlSender.sendGetForJson(urlStr);

		String jsonStr = UrlSender.sendPostForJson(SomeStaticValues.seedUrl,
				SomeStaticValues.params + aricleStr);

		System.out.println("jsonStr:" + jsonStr);
		// ���ӵ����
		int count = 0;
		JSONArray rootList = JSON.parseArray(jsonStr);
		for (int i = 0; i < rootList.size(); i++) {
			JSONArray fatherList = rootList.getJSONArray(i);
			for (int j = 0; j < fatherList.size(); j++) {
				JSONArray thisList = fatherList.getJSONArray(j);
				String thisListString = thisList.toJSONString();

				// jsonStr=jsonStr.substring(2,jsonStr.length()-2);
				// System.out.println("һ�仰Json"+thisListString);
				List<TendWord> wordList = JSON.parseArray(thisListString,
						TendWord.class);
				// ��������㹻����������¶�������������
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
