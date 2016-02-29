package pomonitor.test;

import java.util.List;

import pomonitor.analyse.segment.ReadData;
import pomonitor.analyse.segment.TermsGenerator;

import com.hankcs.hanlp.seg.common.Term;

/**
 * 测试分词和去杂效果
 * 
 * @author luoxu 2015年12月15日 下午3:07:41
 */
public class SegmentTest {

	public static void main(String[] args) {
		String text = ReadData.getContentFromText("E:\\Temp\\test.txt");
		TermsGenerator generateTerms = new TermsGenerator();
		List<Term> termList = generateTerms.getTerms(text);
		for (Term term : termList) {
			System.out.println(term.word + "#" + term.offset + "#"
					+ term.nature);
		}
	}
}
