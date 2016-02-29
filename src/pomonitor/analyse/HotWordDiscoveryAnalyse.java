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
 * 话题发现模块, 介于Controller与具体的话题发现模块之间;向上对controller服务; 向下调用话题发现具体分析包提供的功能
 * 
 * @author caihengyi 2015年12月15日 下午4:12:07
 */
public class HotWordDiscoveryAnalyse {
	private double[][] relevanceMat;
	private ArrayList<RetHotWord> retHotWords;

	/**
	 * 根据特定用户的敏感词库，获取一段时间内新闻文本的热词集合(以及其关联的新闻集合)
	 * 
	 * @param startDateStr
	 * @param endDateStr
	 * @param userId
	 * @return
	 */
	public List<HotWord> discoverHotWords(String startDateStr,
			String endDateStr, int userId) {
		// 调用话题发现功能模块，返回话题集合
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
		// 根据起止时间获取数据库中的新闻文本
		NewsDAO nd = new NewsDAO();
		long start = System.currentTimeMillis();
		List<News> newsList = nd.findBetweenDate(startDateStr, endDateStr);
		long end = System.currentTimeMillis();
		ConsoleLog.PrintInfo(HotWordDiscoveryAnalyse.class, "从数据库中取出"
				+ startDateStr + "到" + endDateStr + "的文本，花费时间为" + (end - start)
				+ "毫秒");
		// 作分词，过滤预处理
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
