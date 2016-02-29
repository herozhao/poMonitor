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
 * K-means 聚类实现
 * 
 * @author caihengyi 2015年12月20日 下午10:03:32
 */
public class KmeansCluster {

	private static int globalCounter = 0;
	private static int counter;
	private static final int MAX_NUM;

	// 初始化参数信息
	static {
		PropertiesReader propertiesReader = new PropertiesReader();
		MAX_NUM = Integer.parseInt(propertiesReader
				.getPropertyByName("KMeansMaxIter"));
	}

	/**
	 * 给定文本向量集合，进行 k-means 聚类，返回类别集合
	 * 
	 * @param k
	 * @param articleCollection
	 * @return
	 */
	public static List<TDCentroid> ArticleCluster(int k,
			List<TDArticle> articleCollection) {
		/******************* 随机初始化 k 个类别 ***********************/
		// 方法一 随机K个中心

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
		// 方法二 选择批次距离尽可能远的K个点
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

		// 分配编号
		for (int i = 0; i < resultSet.size(); i++) {
			resultSet.get(i).CentroidNumber = i;
		}
		return resultSet;
	}

	/**
	 * 选择批次距离尽可能远的K个点
	 * 
	 * @param k
	 * @param articleCollection
	 * 
	 */
	private static List<TDCentroid> getInitKCentroid(int k,
			List<TDArticle> articleCollection) {
		List<TDCentroid> centroidCollection = new ArrayList<TDCentroid>();
		TDCentroid c = null;
		// 防止k超过最大值
		if (k > articleCollection.size())
			k = articleCollection.size();
		do {
			c = new TDCentroid();
			c.GroupedArticle = new ArrayList<TDArticle>();
			// 第一个中心点由随机数决定
			if (centroidCollection.size() == 0) {
				int theFirstCent = new Random().nextInt(articleCollection
						.size());
				c.GroupedArticle.add(articleCollection.get(theFirstCent));
			}
			// 计算哪篇文章距离中心点最远
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
					// 总距离比当前大 则更新
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
	 * 生成各不相同的 k 个整数索引
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
				// 生成一个 [0,articleCount) 之间的随机数 (平均分布)
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
	 * 初始化聚类中心
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
	 * 判断聚类是否应该停止，聚类停止发生在以下情况： 1.聚类中心不再移动 2.迭代次数达到指定次数
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

			// 如果 changeIndex 中含有1 则需要继续聚类，故返回false
			for (int i : changeIndex) {
				if (changeIndex[i] != 0)
					return false;
			}
			return true;
		}
	}

	/**
	 * 计算聚类类别的中心
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
					/*************************** 以均值代替 *******************************/
					_clusterCenter.get(i).GroupedArticle.get(0).vectorSpace[j] = total.doubleValue()
							/ _clusterCenter.get(i).GroupedArticle.size();

				}
			}
		}
		return _clusterCenter;
	}

	/**
	 * @param clusterCenters
	 *            聚类中心的集合:ArrayList
	 * @param article
	 *            某篇文本
	 * @return index 和该篇文本距离最近的聚类中心的索引
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
