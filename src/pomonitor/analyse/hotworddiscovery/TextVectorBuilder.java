package pomonitor.analyse.hotworddiscovery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import pomonitor.analyse.entity.TDArticle;
import pomonitor.analyse.entity.TDArticleTerm;
import pomonitor.analyse.entity.TDPosition;
import pomonitor.util.PropertiesReader;

/**
 * �������ռ�ģ�ͱ�ʾ�����ı�
 * 
 * @author caihengyi 2015��12��14�� ����5:26:03
 */

public class TextVectorBuilder {

	// �����ı�����
	public List<TDArticle> globalArticleList;
	// ��ȡ�ٷֱ�
	private final double EXTRACT_PERCENT;
	// body�ʵ�Ȩ��ϵ��
	private final double BODY_WEIGHT;
	// title�ʵ�Ȩ��ϵ��
	private final double TITLE_WEIGHT;
	// meta�ʵ�Ȩ��ϵ��
	private final double META_WEIGHT;
	// �ܵ���������
	public List<String> globalFeatureCollections = new ArrayList<String>();
	
	//�ӿ��ٶȣ�����ȫ��idf
	private Map<String, Double> globalIdf;
	/**
	 * ��ʼ��������Ϣ
	 */
	public TextVectorBuilder() {
		PropertiesReader propertiesReader = new PropertiesReader();
		BODY_WEIGHT = Double.parseDouble(propertiesReader
				.getPropertyByName("BODY_WEIGHT"));
		TITLE_WEIGHT = Double.parseDouble(propertiesReader
				.getPropertyByName("TITLE_WEIGHT"));
		META_WEIGHT = Double.parseDouble(propertiesReader
				.getPropertyByName("META_WEIGHT"));
		EXTRACT_PERCENT = Double.parseDouble(propertiesReader
				.getPropertyByName("EXTRACT_PERCENT"));
	}

	/**
	 * �����ܵ��������Ϻ������ı����󼯺ϣ�������������
	 * 
	 * @param topicDisArticleList
	 * @param globalFeatureCollections
	 * @return
	 */
	public List<TDArticle> buildVectors(List<TDArticle> topicDisArticleList) {
		globalArticleList = topicDisArticleList;
		//��ʼ�����е�idfֵ
		findInverseDocumentFrequency();
		// ����ÿƪ�������д����Ȩ����Ϣ
		for (TDArticle article : globalArticleList) {
			Map<String, Double> map = new HashMap<String, Double>();
			for (TDArticleTerm _term : article.getArticleAllTerms()) {
				map.put(_term.getvalue(), getWeight(article, _term));
			}
			article.setTermsWeights(map);
		}
		// ����globalFeatureCollections ȫ�� ���������
		globalFeatureCollections = getFeatureSet();
		// ����ÿƪ�ı�������ģ��
		for (TDArticle tdArticle : globalArticleList) {
			tdArticle = buildArticleVector(tdArticle);
		}
		return globalArticleList;
	}

	/**
	 * �����ƪ�ı�������ģ��
	 * 
	 * @param TDArticle
	 * @return TDArticle
	 */

	private TDArticle buildArticleVector(TDArticle article) {
		TDArticle resTdArticle = article;
		double[] vec = new double[globalFeatureCollections.size()];
		double sumVal = 0, avgVal = 0, maxVal, minVal;
		for (int i = 0; i < vec.length; i++) {
			if (article.getTermsWeights().containsKey(
					globalFeatureCollections.get(i)))
				vec[i] = article.getTermsWeights().get(
						globalFeatureCollections.get(i));
			else
				vec[i] = 0;
			sumVal += vec[i];
		}
		// ��һ������
		avgVal = sumVal / vec.length;
		maxVal = HotWordDiscovery.getMax(vec);
		minVal = HotWordDiscovery.getMin(vec);
		for (int i = 0; i < vec.length; i++) {
			if(maxVal - minVal>=0.0000000001)
				vec[i] = (vec[i] - avgVal) / (maxVal - minVal);
		}
		resTdArticle.vectorSpace = vec;
		return resTdArticle;
	}

	/**
	 * ����ĳ�������Ȩ��ֵ����Ȩ��ֵ������ֵ�й� : 1.�ô���� (term) 2.�����ô�������� (article) 3.�������¼���
	 * (globalArticleList)
	 * 
	 * �÷�����Ҫ����
	 * 
	 * @param article
	 * @param term
	 * @return
	 */
	private double getWeight(TDArticle article, TDArticleTerm term) {
		
		if (term.getposition() == TDPosition.BODY)
			return findTFIDF(article, term) * BODY_WEIGHT;
		else if (term.getposition() == TDPosition.META)
			return findTFIDF(article, term) * META_WEIGHT;
		else
			return findTFIDF(article, term) * TITLE_WEIGHT;
	}

	/**
	 * ����ĳ���ʵ� tf-idf ֵ
	 * 
	 * @param article
	 * @param term
	 * @return
	 */
	private double findTFIDF(TDArticle article, TDArticleTerm term) {
		double tf = findTermFrequency(article, term.getvalue());
		double idf = globalIdf.get(term.getvalue());
		return  tf * idf;
	}

	/**
	 * ����ĳ������ĳƪ�����е� tf ��Ȩ��Ƶ����
	 * 
	 * @param article
	 * @param term
	 * @return
	 */
	private double findTermFrequency(TDArticle article, String term) {
		double termCount = 0;
		for (TDArticleTerm _term : article.getArticleAllTerms()) {
			if (term.equals(_term.getvalue())) {
				termCount += 1;
			}
		}
		return termCount / article.getArticleAllTerms().size();
	}

	/**
	 * ����ĳ���ʵ� idf
	 * 
	 * @param term
	 * @return
	 */
	private void findInverseDocumentFrequency() {
		this.globalIdf=new HashMap<String, Double>();
		for (TDArticle article : globalArticleList) {
			Map<String, Double> tempIdf=new HashMap<String, Double>();
			for(TDArticleTerm td:article.getArticleAllTerms())
				if(tempIdf.containsKey(td.getvalue())==false){
					double count=0.0;
					if(this.globalIdf.get(td.getvalue())!=null)
						count=this.globalIdf.get(td.getvalue());
					this.globalIdf.put(td.getvalue(), count+1);
					tempIdf.put(td.getvalue(), 1.0);
				}
		}
		for(Map.Entry<String, Double> m:globalIdf.entrySet()){
			this.globalIdf.put(m.getKey(),Math.log((globalArticleList.size()) 
					/ (m.getValue() + 0.001)));
		}
		return ;
	}

	/**
	 * ���������ı����Ϻ�ָ������ȡ�ٷֱȣ������Ч��������ϣ�������������
	 * 
	 * @param topicDisArticleList
	 * @param percentage
	 * @return ���������ϣ��䳤�ȼ�Ϊ��������
	 */
	private List<String> getFeatureSet() {
		List<String> tmpGlobalFeatureCollections = new ArrayList<String>();

		for (TDArticle article : globalArticleList) {
			ArrayList<Map.Entry<String, Double>> descSortedList = DescSort(article
					.getTermsWeights());
			// ��ȡָ��������ȫ������������
			int extractsize = (int) (article.getTermsWeights().size() * EXTRACT_PERCENT);
			for (int i = 0; i < extractsize; i++) {
				tmpGlobalFeatureCollections.add(descSortedList.get(i).getKey());
			}
		}
		// ȥ��
		globalFeatureCollections = new ArrayList<String>(new HashSet<String>(
				tmpGlobalFeatureCollections));
		
		return globalFeatureCollections;
	}

	/**
	 * �� Map ���� value �������У����� key �� ArrayList
	 * 
	 * @param map
	 * @return
	 */
	private ArrayList<Map.Entry<String, Double>> DescSort(
			Map<String, Double> map) {
		ArrayList<Map.Entry<String, Double>> mapList = new ArrayList<Map.Entry<String, Double>>(
				map.entrySet());
		Collections.sort(mapList, new Comparator<Map.Entry<String, Double>>() {
			@Override
			public int compare(Map.Entry<String, Double> o1,
					Map.Entry<String, Double> o2) {
				// ����value��������
				if ((o2.getValue() - o1.getValue()) < 0)
					return -1;
				else if ((o2.getValue() - o1.getValue()) > 0)
					return 1;
				else
					return 0;
			}
		});
		return mapList;
	}
}