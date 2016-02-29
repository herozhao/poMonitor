package pomonitor.analyse;

import java.util.ArrayList;
import java.util.List;

import pomonitor.analyse.entity.Attitude;
import pomonitor.analyse.entity.HotWord;
import pomonitor.analyse.entity.RetHotWord;
import pomonitor.analyse.entity.TDArticle;
import pomonitor.analyse.entity.TDArticleTerm;
import pomonitor.analyse.entity.TDPosition;
import pomonitor.analyse.hotworddiscovery.HotWordDiscovery;
import pomonitor.analyse.segment.TermsGenerator;
import pomonitor.entity.News;
import pomonitor.entity.NewsDAO;
import pomonitor.entity.SenswordDAO;
import pomonitor.util.ConsoleLog;

import com.hankcs.hanlp.seg.common.Term;

/**
 * ���ⷢ��ģ��, ����Controller�����Ļ��ⷢ��ģ��֮��;���϶�controller����; ���µ��û��ⷢ�־���������ṩ�Ĺ���
 * 
 * @author caihengyi 2015��12��15�� ����4:12:07
 */
public class HotWordDiscoveryAnalyse {
	private double[][] relevanceMat;
	private ArrayList<RetHotWord> retHotWords;

	/**
	 * �����ض��û������дʿ⣬��ȡһ��ʱ���������ı����ȴʼ���(�Լ�����������ż���)
	 * 
	 * @param startDateStr
	 * @param endDateStr
	 * @param userId
	 * @return
	 */
	public List<HotWord> discoverHotWords(String startDateStr,
			String endDateStr, int userId) {
		// ���û��ⷢ�ֹ���ģ�飬���ػ��⼯��
		HotWordDiscovery td = new HotWordDiscovery();
		SenswordDAO sd = new SenswordDAO();
		List<HotWord> hotwords = td.getHotWords(
				getArticlesBetweenDate(startDateStr, endDateStr),
				sd.findByProperty("userid", userId));
		this.relevanceMat = td.getRelevanceMat();
		ArrayList<RetHotWord> retHotWordsList = new ArrayList<RetHotWord>();
		for (int i = 0; i < hotwords.size(); i++) {
			HotWord hotWord = hotwords.get(i);
			RetHotWord _rethw = new RetHotWord();
			if (hotWord.getAttitude() == Attitude.NEUTRAL)
				_rethw.setCategory(0);
			else if (hotWord.getAttitude() == Attitude.DEROGATORY)
				_rethw.setCategory(2);
			else if (hotWord.getAttitude() == Attitude.PRAISE)
				_rethw.setCategory(1);
			_rethw.setIndex(i);
			if (hotWord.isSensitiveWords())
				_rethw.setLabel(1);
			else
				_rethw.setLabel(0);
			_rethw.setName(hotWord.getContent());
			_rethw.setValue(hotWord.getWeight());
			retHotWordsList.add(_rethw);
		}
		this.retHotWords = retHotWordsList;
		return hotwords;
	}

	public List<TDArticle> getArticlesBetweenDate(String startDateStr,
			String endDateStr) {
		// ������ֹʱ���ȡ���ݿ��е������ı�
		NewsDAO nd = new NewsDAO();
		long start = System.currentTimeMillis();
		List<News> newsList = nd.findBetweenDate(startDateStr, endDateStr);
		long end = System.currentTimeMillis();
		ConsoleLog.PrintInfo(HotWordDiscoveryAnalyse.class, "�����ݿ���ȡ��"
				+ startDateStr + "��" + endDateStr + "���ı�������ʱ��Ϊ" + (end - start)
				+ "����");
		// ���ִʣ�����Ԥ����
		List<TDArticle> tdArticleList = new ArrayList<TDArticle>();
		TermsGenerator generateTerms = new TermsGenerator();
		for (News news : newsList) {
			TDArticle tmpArt = new TDArticle();
			List<TDArticleTerm> tmpTDArtTerms = new ArrayList<TDArticleTerm>();

			List<Term> tmpTermList_allcontent = generateTerms.getTerms(news
					.getAllContent());
			List<Term> tmpTermList_keyword = generateTerms.getTerms(news
					.getKeyWords());
			List<Term> tmpTermList_title = generateTerms.getTerms(news
					.getTitle());
			for (Term term : tmpTermList_allcontent) {
				TDArticleTerm tmpArtTerm = new TDArticleTerm();
				tmpArtTerm.setposition(TDPosition.BODY);
				tmpArtTerm.setvalue(term.word);
				tmpTDArtTerms.add(tmpArtTerm);
			}
			for (Term term : tmpTermList_keyword) {
				TDArticleTerm tmpArtTerm = new TDArticleTerm();
				tmpArtTerm.setposition(TDPosition.META);
				tmpArtTerm.setvalue(term.word);
				tmpTDArtTerms.add(tmpArtTerm);
			}
			for (Term term : tmpTermList_title) {
				TDArticleTerm tmpArtTerm = new TDArticleTerm();
				tmpArtTerm.setposition(TDPosition.TITLE);
				tmpArtTerm.setvalue(term.word);
				tmpTDArtTerms.add(tmpArtTerm);
			}
			tmpArt.setArticleAllTerms(tmpTDArtTerms);
			tmpArt.setComeFrom(news.getWeb());
			tmpArt.setDescription(news.getContent());
			tmpArt.setTimestamp(news.getTime());
			tmpArt.setTitle(news.getTitle());
			tmpArt.setUrl(news.getUrl());
			tmpArt.setId(news.getId());
			tdArticleList.add(tmpArt);
		}
		return tdArticleList;
	}

	public double[][] getRelevanceMat() {
		return relevanceMat;
	}

	public void setRelevanceMat(double[][] relevanceMat) {
		this.relevanceMat = relevanceMat;
	}

	public ArrayList<RetHotWord> getRetHotWords() {
		return retHotWords;
	}

	public void setRetHotWords(ArrayList<RetHotWord> retHotWords) {
		this.retHotWords = retHotWords;
	}

}
