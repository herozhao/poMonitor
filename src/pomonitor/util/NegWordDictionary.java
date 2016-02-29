package pomonitor.util;

import java.util.HashMap;
import java.util.List;

import pomonitor.entity.IdeaWord;
import pomonitor.entity.IdeaWordDAO;
import pomonitor.entity.NegWord;
import pomonitor.entity.NegWordDAO;

/**
 * �������ʵ�
 * 
 * @author zhaolong 2015��12��17�� ����6:04:02
 */
public class NegWordDictionary {
	// �����ṩ��map�����ж�
	public HashMap<String, String> map;
	private NegWordDAO wordDao;

	public NegWordDictionary() {
		map = new HashMap<>();
		wordDao = new NegWordDAO();
		List<NegWord> list = wordDao.findAll();
		for (int i = 0; i < list.size(); i++) {
			map.put(list.get(i).getWord(), null);
		}
	}

}
