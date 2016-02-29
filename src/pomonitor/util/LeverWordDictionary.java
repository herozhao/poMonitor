package pomonitor.util;

import java.util.HashMap;
import java.util.List;

import pomonitor.entity.LeverWord;
import pomonitor.entity.LeverWordDAO;

/**
 * 
 * @author xiaoyulun 2015��12��14�� ����6:26:07
 */
public class LeverWordDictionary {
	private HashMap<String, LeverWord> hashMap;
	private LeverWordDAO leverWordDAO;

	public LeverWordDictionary() {
		this.hashMap = new HashMap<String, LeverWord>();
		this.leverWordDAO = new LeverWordDAO();
		initDictionary();
	}

	// ��ʼ�����ʴʵ�
	public void initDictionary() {
		List<LeverWord> list = leverWordDAO.findAll();
		for (LeverWord leverWord : list) {
			hashMap.put(leverWord.getWord(), leverWord);
		}
	}

	// ��ȡ������
	public LeverWord getWord(String string) {
		return hashMap.get(string);
	}

	public static void main(String[] args) {
		LeverWordDictionary lwd = new LeverWordDictionary();
		lwd.initDictionary();
		System.out.println(lwd.getWord("��").getScore());

	}
}
