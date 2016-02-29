package pomonitor.util;

import java.util.HashMap;
import java.util.List;

import pomonitor.entity.LeverWord;
import pomonitor.entity.LeverWordDAO;

/**
 * 
 * @author xiaoyulun 2015年12月14日 下午6:26:07
 */
public class LeverWordDictionary {
	private HashMap<String, LeverWord> hashMap;
	private LeverWordDAO leverWordDAO;

	public LeverWordDictionary() {
		this.hashMap = new HashMap<String, LeverWord>();
		this.leverWordDAO = new LeverWordDAO();
		initDictionary();
	}

	// 初始化副词词典
	public void initDictionary() {
		List<LeverWord> list = leverWordDAO.findAll();
		for (LeverWord leverWord : list) {
			hashMap.put(leverWord.getWord(), leverWord);
		}
	}

	// 获取副词类
	public LeverWord getWord(String string) {
		return hashMap.get(string);
	}

	public static void main(String[] args) {
		LeverWordDictionary lwd = new LeverWordDictionary();
		lwd.initDictionary();
		System.out.println(lwd.getWord("很").getScore());

	}
}
