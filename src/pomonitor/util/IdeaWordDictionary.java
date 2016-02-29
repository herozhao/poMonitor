package pomonitor.util;

import java.util.HashMap;
import java.util.List;

import pomonitor.entity.IdeaWord;
import pomonitor.entity.IdeaWordDAO;

/**
 * ���������ʵ�
 * 
 * @author zhaolong 2015��12��17�� ����6:04:02
 */
public class IdeaWordDictionary {
	public HashMap<String, String> map;
	private IdeaWordDAO wordDao;

	public IdeaWordDictionary() {
		map = new HashMap<>();
		wordDao = new IdeaWordDAO();
		List<IdeaWord> list = wordDao.findAll();
		for (int i = 0; i < list.size(); i++) {
			map.put(list.get(i).getWord(), null);
		}
	}

}
