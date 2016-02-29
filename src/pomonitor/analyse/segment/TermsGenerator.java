package pomonitor.analyse.segment;

import java.util.List;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.CRF.CRFSegment;
import com.hankcs.hanlp.seg.common.Term;

/**
 * 
 * @author luoxu 2015��12��15�� ����1:44:49 �ִʣ��õ�term term�� �������ƣ����Թ��ɡ�
 */
public class TermsGenerator {
	/*
	 * ����Term
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
