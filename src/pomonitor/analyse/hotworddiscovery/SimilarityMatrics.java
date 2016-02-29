package pomonitor.analyse.hotworddiscovery;

/**
 * 向量之间的相似度
 * 
 * @author caihengyi 2015年12月20日 下午9:33:39
 */
public class SimilarityMatrics {

	private static double ZERO_THRESHOLD = 0.00001;

	/**
	 * 两向量的余弦相似度
	 * 
	 * @param vecA
	 * @param vecB
	 * @return
	 */
	public static double FindCosineSimilarity(double[] vecA, double[] vecB) {
		double dotProduct = DotProduct(vecA, vecB);
		double magnitudeOfA = Magnitude(vecA);
		double magnitudeOfB = Magnitude(vecB);
		if (isZero(magnitudeOfA, ZERO_THRESHOLD)
				|| isZero(magnitudeOfB, ZERO_THRESHOLD))
			return 0;
		double result = dotProduct / (magnitudeOfA * magnitudeOfB);
		return result;
	}

	private static boolean isZero(double value, double threshold) {
		return value >= -threshold && value <= threshold;
	}

	/**
	 * 两向量内积
	 * 
	 * @param vecA
	 * @param vecB
	 * @return
	 */
	public static double DotProduct(double[] vecA, double[] vecB) {
		double dotProduct = 0;
		for (int i = 0; i < vecA.length; i++) {
			dotProduct += (vecA[i] * vecB[i]);
		}
		return dotProduct;
	}

	/**
	 * 向量的模
	 * 
	 * @param vector
	 * @return
	 */
	public static double Magnitude(double[] vector) {
		return Math.sqrt(DotProduct(vector, vector));
	}

	/**
	 * 两向量之间的欧式距离
	 * 
	 * @param vecA
	 * @param vecB
	 * @return
	 */
	public static double FindEuclideanDistance(double[] vecA, double[] vecB) {
		double euclideanDistance = 0;
		for (int i = 0; i < vecA.length; i++) {
			euclideanDistance += Math.pow((vecA[i] - vecB[i]), 2);
		}

		return Math.sqrt(euclideanDistance);

	}

}
