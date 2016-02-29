package pomonitor.util;

import java.util.HashMap;
import java.util.List;

import pomonitor.entity.Emotionalword;
import pomonitor.entity.EmotionalwordDAO;

/**
 * 
 * @author xiaoyulun 2015��12��14�� ����3:26:33
 */
public class EmotionalDictionary {
	private HashMap<String, Emotionalword> hashMap;
	private EmotionalwordDAO emotionalwordDAO;

	public EmotionalDictionary() {
		hashMap = new HashMap<String, Emotionalword>();
		emotionalwordDAO = new EmotionalwordDAO();
		initDictionary();
	}

	// ��ʼ����д��ֵ�
	public void initDictionary() {
		List<Emotionalword> list = emotionalwordDAO.findAll();
		for (Emotionalword emotionalword : list) {
			hashMap.put(emotionalword.getWord(), emotionalword);
		}
	}

	// ��ȡ��д���
	public Emotionalword getWord(String word) {
		return hashMap.get(word);
	}

}
