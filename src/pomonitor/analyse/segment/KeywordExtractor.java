package pomonitor.analyse.segment;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.common.Term;

/**
 * 
 * @author luoxu 2015年12月15日 下午1:44:49 去杂
 */
public class KeywordExtractor {
	/**
	 * 是否应当将这个term纳入计算，词性属于名词n、动词v、副词d、形容词a
	 * 
	 * @param term
	 * @return 是否应当
	 */
	public boolean shouldInclude(Term term) {
		// 除掉停用词
		if (term.nature == null)
			return false;
		String nature = term.nature.toString();
		char firstChar = nature.charAt(0);
		switch (firstChar) {
		case 'm':
		case 'b':
		case 'c':
		case 'e':
		case 'o':
		case 'p':
		case 'q':
		case 'u':
		case 'y':
		case 'z':
		case 'r':
		case 'w':
		case 'x':
		case 't':
		case 's':
		case 'g':
		case 'h':
		case 'k':
		case 'f': {
			return false;
		}
		default: {
			if (term.word.trim().length() > 1
					&& !CoreStopWordDictionary.contains(term.word)) {
				return true;
			}
		}
			break;
		}
		return false;
	}

	public List<Term> wipeoffWords(List<Term> termList) {
		Iterator<Term> iter = termList.iterator();
		while (iter.hasNext()) {
			Term term = iter.next();
			Pattern p = Pattern.compile("^[0-9]*$");
			Matcher m = p.matcher(term.word);
			if (m.matches() == true) {
				iter.remove();
				continue;
			}
			if (!shouldInclude(term)) {
				iter.remove();
			}
		}
		return termList;
	}
}
