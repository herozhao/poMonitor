package pomonitor.analyse.segment;

import java.util.List;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.CRF.CRFSegment;
import com.hankcs.hanlp.seg.common.Term;

/**
 * 
 * @author luoxu 2015年12月15日 下午1:44:49 分词，得到term term由 单词名称，词性构成。
 */
public class TermsGenerator {
	/*
	 * 生成Term
	 */
	public List<Term> getTerms(String Content) {
		Segment segment = new CRFSegment();
		KeywordExtractor keywordExtractor = new KeywordExtractor();

		segment.enablePartOfSpeechTagging(true);
		segment.enableAllNamedEntityRecognize(true);
		List<Term> termList = segment.seg(Content);

		termList = keywordExtractor.wipeoffWords(termList);
		return termList;
	}
}
