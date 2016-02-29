package pomonitor.analyse.hotworddiscovery;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import pomonitor.analyse.entity.TDArticle;
import pomonitor.analyse.entity.TDCentroid;
import pomonitor.util.PropertiesReader;

/**
 * K-means ����ʵ��
 * 
 * @author caihengyi 2015��12��20�� ����10:03:32
 */
public class KmeansCluster {

	private static int globalCounter = 0;
	private static int counter;
	private static final int MAX_NUM;

	// ��ʼ��������Ϣ
	static {
		PropertiesReader propertiesReader = new PropertiesReader();
		MAX_NUM = Integer.parseInt(propertiesReader
				.getPropertyByName("KMeansMaxIter"));
	}

	/**
	 * �����ı��������ϣ����� k-means ���࣬������𼯺�
	 * 
	 * @param k
	 * @param articleCollection
	 * @return
	 */
	public static List<TDCentroid> ArticleCluster(int k,
			List<TDArticle> articleCollection) {
		/******************* �����ʼ�� k ����� ***********************/
		// ����һ ���K������

//		List<TDCentroid> centroidCollection = new ArrayList<TDCentroid>();
//		TDCentroid c;
//		HashSet<Integer> uniqRand = GenerateRandomNumber(k,
//				articleCollection.size());
//		for (int i : uniqRand) {
//			c = new TDCentroid();
//			c.GroupedArticle = new ArrayList<TDArticle>();
//			c.GroupedArticle.add(articleCollection.get(i));
//			centroidCollection.add(c);
//		}

		/***********************************************************/
		// ������ ѡ�����ξ��뾡����Զ��K����
		 List<TDCentroid>
		 centroidCollection=getInitKCentroid(k,articleCollection);

		boolean stoppingCriteria = false;
		List<TDCentroid> resultSet;
		List<TDCentroid> prevClusterCenter;
		resultSet = InitializeClusterCentroid(centroidCollection.size());

		do {
			prevClusterCenter = centroidCollection;
			for (TDArticle tdArticle : articleCollection) {
				int index = FindClosestClusterCenter(centroidCollection,
						tdArticle);
				resultSet.get(index).GroupedArticle.add(tdArticle);
			}
			
			
			centroidCollection = InitializeClusterCentroid(centroidCollection
					.size());
			centroidCollection = CalculateMeanPoints(resultSet);
			stoppingCriteria = CheckStoppingCriteria(prevClusterCenter,
					centroidCollection);
			if (!stoppingCriteria) {
				resultSet = InitializeClusterCentroid(centroidCollection.size());
			}
		} while (!stoppingCriteria);

		// ������
		for (int i = 0; i < resultSet.size(); i++) {
			resultSet.get(i).CentroidNumber = i;
		}
		return resultSet;
	}

	/**
	 * ѡ�����ξ��뾡����Զ��K����
	 * 
	 * @param k
	 * @param articleCollection
	 * 
	 */
	private static List<TDCentroid> getInitKCentroid(int k,
			List<TDArticle> articleCollection) {
		List<TDCentroid> centroidCollection = new ArrayList<TDCentroid>();
		TDCentroid c = null;
		// ��ֹk�������ֵ
		if (k > articleCollection.size())
			k = articleCollection.size();
		do {
			c = new TDCentroid();
			c.GroupedArticle = new ArrayList<TDArticle>();
			// ��һ�����ĵ������������
			if (centroidCollection.size() == 0) {
				int theFirstCent = new Random().nextInt(articleCollection
						.size());
				c.GroupedArticle.add(articleCollection.get(theFirstCent));
			}
			// ������ƪ���¾������ĵ���Զ
			else {
				TDArticle theFararticle = null;
				double theFarDist = 0.0;
				for (TDArticle article : articleCollection) {
					double theDist = 0.0;
					for (TDCentroid cent : centroidCollection) {
						theDist += SimilarityMatrics.FindEuclideanDistance(
								article.vectorSpace, cent.getGroupedArticle()
										.get(0).vectorSpace);
					}
					// �ܾ���ȵ�ǰ�� �����
					if (theDist > theFarDist) {
						theFarDist = theDist;
						theFararticle = article;
					}
				}
				if (theFararticle != null)
					c.GroupedArticle.add(theFararticle);
			}
			if (c != null)
				centroidCollection.add(c);
		} while (centroidCollection.size() != k);
		return centroidCollection;
	}

	/**
	 * ���ɸ�����ͬ�� k ����������
	 * 
	 * @param k
	 * @param articleCount
	 * @return
	 */
	private static HashSet<Integer> GenerateRandomNumber(int k, int articleCount) {
		HashSet<Integer> uniqRand = new HashSet<Integer>();
		Random r = new Random();

		if (k > articleCount) {
			do {
				// ����һ�� [0,articleCount) ֮�������� (ƽ���ֲ�)
				int pos = r.nextInt(articleCount);
				uniqRand.add(pos);

			} while (uniqRand.size() != articleCount);
		} else {
			do {
				int pos = r.nextInt(articleCount);
				uniqRand.add(pos);

			} while (uniqRand.size() != k);
		}
		return uniqRand;
	}

	/**
	 * ��ʼ����������
	 * 
	 * @param count
	 * @return
	 */
	private static List<TDCentroid> InitializeClusterCentroid(int count) {
		TDCentroid c;
		List<TDCentroid> centroid = new ArrayList<TDCentroid>();
		for (int i = 0; i < count; i++) {
			c = new TDCentroid();
			c.GroupedArticle = new ArrayList<TDArticle>();
			centroid.add(c);
		}
		return centroid;
	}

	/**
	 * �жϾ����Ƿ�Ӧ��ֹͣ������ֹͣ��������������� 1.�������Ĳ����ƶ� 2.���������ﵽָ������
	 * 
	 * @param prevClusterCenter
	 * @param newClusterCenter
	 * @return
	 */
	private static boolean CheckStoppingCriteria(
			List<TDCentroid> prevClusterCenter,
			List<TDCentroid> newClusterCenter) {
		globalCounter++;
		counter = globalCounter;
		if (globalCounter > MAX_NUM)
			return true;
		else {
			int[] changeIndex = new int[newClusterCenter.size()];
			int index = 0;
			do {
				int count = 0;
				if (newClusterCenter.get(index).GroupedArticle.size() == 0
						&& prevClusterCenter.get(index).GroupedArticle.size() == 0)
					index++;
				else if (newClusterCenter.get(index).GroupedArticle.size() != 0
						&& prevClusterCenter.get(index).GroupedArticle.size() != 0) {
					for (int j = 0; j < newClusterCenter.get(index).GroupedArticle
							.get(0).vectorSpace.length; j++) {
						if (newClusterCenter.get(index).GroupedArticle.get(0).vectorSpace[j] == prevClusterCenter
								.get(index).GroupedArticle.get(0).vectorSpace[j]) {
							count++;
						}
					}
					if (count == newClusterCenter.get(index).GroupedArticle
							.get(0).vectorSpace.length) {
						changeIndex[index] = 0;
					} else {
						changeIndex[index] = 1;
					}
					index++;
				} else {
					index++;
					continue;
				}
			} while (index < newClusterCenter.size());

			// ��� changeIndex �к���1 ����Ҫ�������࣬�ʷ���false
			for (int i : changeIndex) {
				if (changeIndex[i] != 0)
					return false;
			}
			return true;
		}
	}

	/**
	 * ���������������
	 * 
	 * @param _clusterCenter
	 * @return
	 */
	private static List<TDCentroid> CalculateMeanPoints(
			List<TDCentroid> clusterCenter) {
		List<TDCentroid> _clusterCenter = clusterCenter;
		for (int i = 0; i < _clusterCenter.size(); i++) {
			if (_clusterCenter.get(i).GroupedArticle.size() > 0) {
				for (int j = 0; j < _clusterCenter.get(i).GroupedArticle.get(0).vectorSpace.length; j++) {
					BigDecimal total = BigDecimal.ZERO;
					for (TDArticle vSpace : _clusterCenter.get(i).GroupedArticle){
						if(!Double.isNaN(vSpace.vectorSpace[j])){
							 BigDecimal bd = new BigDecimal(vSpace.vectorSpace[j]);  
							 total = total.add(bd);
						}
						
					}
					total = total.setScale(8, RoundingMode.HALF_UP); 
					/*************************** �Ծ�ֵ���� *******************************/
					_clusterCenter.get(i).GroupedArticle.get(0).vectorSpace[j] = total.doubleValue()
							/ _clusterCenter.get(i).GroupedArticle.size();

				}
			}
		}
		return _clusterCenter;
	}

	/**
	 * @param clusterCenters
	 *            �������ĵļ���:ArrayList
	 * @param article
	 *            ĳƪ�ı�
	 * @return index �͸�ƪ�ı���������ľ������ĵ�����
	 * 
	 */
	private static int FindClosestClusterCenter(
			List<TDCentroid> clusterCenters, TDArticle article) {
		double[] similarityMeasure = new double[clusterCenters.size()];
		for (int i = 0; i < clusterCenters.size(); i++) {
			if(clusterCenters.get(i).GroupedArticle.size()!=0){
			similarityMeasure[i] = SimilarityMatrics.FindCosineSimilarity(
					clusterCenters.get(i).GroupedArticle.get(0).vectorSpace,
					article.vectorSpace);
		}
		}
		int index = 0;
		double maxValue = similarityMeasure[0];
		for (int i = 0; i < similarityMeasure.length; i++) {
			if (similarityMeasure[i] > maxValue) {
				maxValue = similarityMeasure[i];
				index = i;
			}
		}
		return index;
	}
}
