package pomonitor.analyse.articletend;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import pomonitor.analyse.entity.TendWord;
import pomonitor.util.JsonContentGetter;
import pomonitor.util.SomeStaticValues;

import com.alibaba.fastjson.JSON;

/**
 * ���ӷ���������String��̬�ľ���ת��Ϊ�Զ����TendSentence����
 * 
 * @author zhaolong 2015��12��16�� ����9:27:44
 */
public class SentenceSplier {

	/**
	 * ��String��̬�ľ���Ԥ����Ϊ�Զ����TendSentence����(ֻ����һ�仰) >>>>>>> branch 'develop' of
	 * 
	 * @param sentence
	 * @return
	 */
	public List<TendWord> spil(String sentence) {
		String utfUrlStr = "";
		try {
			utfUrlStr = SomeStaticValues.url;
			System.out.println(sentence);

			// �Ժ�����ת�봦��
			sentence = URLEncoder.encode(sentence + "��", "utf-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("����ת�����뼯����ת��ʧ��");
			e.printStackTrace();
		}

		// ƴ��url����Ĳ���
		String urlStr = utfUrlStr + sentence;
		String jsonStr = JsonContentGetter.getJsonContent(urlStr);
		jsonStr = jsonStr.substring(4, jsonStr.length() - 3);
		System.out.println("�������jsonStr:" + jsonStr);
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
