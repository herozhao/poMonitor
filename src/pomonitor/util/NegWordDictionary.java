package pomonitor.util;

import java.util.HashMap;
import java.util.List;

import pomonitor.entity.IdeaWord;
import pomonitor.entity.IdeaWordDAO;
import pomonitor.entity.NegWord;
import pomonitor.entity.NegWordDAO;

/**
 * 否定类词语词典
 * 
 * @author zhaolong 2015年12月17日 下午6:04:02
 */
public class NegWordDictionary {
	// 向外提供此map用来判断
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
