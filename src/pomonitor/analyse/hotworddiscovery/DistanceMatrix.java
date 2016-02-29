package pomonitor.analyse.hotworddiscovery;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import pomonitor.analyse.entity.ArticleShow;
import pomonitor.analyse.entity.HotWord;
import pomonitor.analyse.entity.TDArticle;
import pomonitor.analyse.entity.TDCentroid;
import pomonitor.util.ConsoleLog;
import pomonitor.util.PropertiesReader;

/**
 * 
 * @author luoxu
 *         2016年1月14日 下午4:34:21
 */
public class DistanceMatrix {
	private double[][] classDistMat;
	private double[][] overlapMat;
	private double[][] sameArticleMat;
	private final double[][] globalTDCentroidDist;
	private final double sameClassVal;
	private final double classDistWeight;
	private final double overlapDistWeight;
	private final double sameArticleMatWeight;
	private List<HotWord> hotWords;
	private List<TDArticle> articleLists;
	private List<TDCentroid> resTDCentroid ;
	
	public DistanceMatrix(List<HotWord> sumHotWords,
			double[][] globalTDCentroidDist,List<TDArticle> articleLists,List<TDCentroid> resTDCentroid) {
		PropertiesReader propertiesReader = new PropertiesReader();
		this.sameClassVal = Double.parseDouble(propertiesReader
				.getPropertyByName("SameClassVal"));
		this.classDistWeight = Double.parseDouble(propertiesReader
				.getPropertyByName("ClassDistWeight"));
		this.overlapDistWeight = Double.parseDouble(propertiesReader
				.getPropertyByName("OverlapDistWeight"));
		this.hotWords = sumHotWords;
		
		
		this.globalTDCentroidDist = globalTDCentroidDist;
		this.articleLists=articleLists;
		this.resTDCentroid=resTDCentroid;
		this.sameArticleMatWeight=Double.parseDouble(propertiesReader
				.getPropertyByName("SameArticleMatWeight"));
	}

	public double[][] getRelevanceMat() {
		classDistMat = this.calClassDistMat();
		overlapMat = this.calOverlapMat();
		sameArticleMat=this.calSameArticleMat();
		Map<Double,Integer> remove=new HashMap<Double,Integer>();
		int len = hotWords.size();
		double[][] relevanceMat = new double[len][len];
		for (int i = 0; i < len; i++)
			for (int j = 0; j < len; j++) {
//				relevanceMat[i][j]=overlapMat[i][j]* overlapDistWeight +
//						sameArticleMat[i][j];
				relevanceMat[i][j] =classDistMat[i][j] * classDistWeight
						+ overlapMat[i][j] * overlapDistWeight
						+sameArticleMat[i][j]*sameArticleMatWeight;
				int count=0;
				if(remove.get(relevanceMat[i][j])!=null)
					count=remove.get(relevanceMat[i][j]);
				remove.put(relevanceMat[i][j],(count+1));
			}
//		int maxMode=-1;
//		double mode=-1;
//		for(Entry<Double, Integer> m:remove.entrySet()){
//			if(maxMode<m.getValue()){
//				maxMode=m.getValue();
//				mode=m.getKey();
//			}
//		}
//		for (int i = 0; i < len; i++)
//			for (int j = 0; j < len; j++){
//				if(relevanceMat[i][j]==mode){
//					relevanceMat[i][j]=0;
//				}
//			}
		
		/******************* 输出新闻同现率距离和类别距离矩阵 **********************/
		
		int alignWidth = 20;
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("D:\\RelevanceMat.txt", "UTF-8");
			writer.println("新闻同现率矩阵：");
			writer.println(ConsoleLog.StringMulti(
					ConsoleLog.AlignStrWithPlaceholder("*", "*", alignWidth),
					len));
			writer.print(ConsoleLog.AlignStrWithPlaceholder(" ", " ",
					alignWidth));
			for (int i = 0; i < len; i++) {
				writer.print(ConsoleLog.AlignHanZiWithPlaceholder(
						hotWords.get(i).getContent(), " ", alignWidth));
			}
			writer.println();
			for (int i = 0; i < len; i++) {
				writer.print(ConsoleLog.AlignHanZiWithPlaceholder(
						hotWords.get(i).getContent(), " ", alignWidth));
				for (int j = 0; j < len; j++) {
					writer.print(ConsoleLog.AlignStrWithPlaceholder(
							overlapMat[i][j] + "", " ", alignWidth));
				}
				writer.println();
			}
			writer.println(ConsoleLog.StringMulti(
					ConsoleLog.AlignStrWithPlaceholder("*", "*", alignWidth),
					len));

			writer.println("类别距离矩阵：");
			writer.println(ConsoleLog.StringMulti(
					ConsoleLog.AlignStrWithPlaceholder("*", "*", alignWidth),
					len));
			writer.print(ConsoleLog.AlignStrWithPlaceholder(" ", " ",
					alignWidth));
			for (int i = 0; i < len; i++) {
				writer.print(ConsoleLog.AlignHanZiWithPlaceholder(
						hotWords.get(i).getContent(), " ", alignWidth));
			}
			writer.println();
			for (int i = 0; i < len; i++) {
				writer.print(ConsoleLog.AlignHanZiWithPlaceholder(
						hotWords.get(i).getContent(), " ", alignWidth));
				for (int j = 0; j < len; j++) {
					writer.print(ConsoleLog.AlignStrWithPlaceholder(
							classDistMat[i][j] + "", " ", alignWidth));
				}
				writer.println();
			}
			writer.println(ConsoleLog.StringMulti(
					ConsoleLog.AlignStrWithPlaceholder("*", "*", alignWidth),
					len));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
		
		/*********************************************************************/
		
		return relevanceMat;
	}

	/**
	 * 类别距离矩阵
	 * 
	 * @param globalTDCentroidDist
	 * @return
	 */
	private double[][] calClassDistMat() {
		int len = hotWords.size();
		classDistMat = new double[len][len];
//		for (int i = 0; i < len; i++) {
//			for (int j = 0; j < len; j++) {
//				HotWord word1 = hotWords.get(i), word2 = hotWords.get(j);
//				if (word1.getBelongto() != word2.getBelongto()) {
//					classDistMat[i][j] = globalTDCentroidDist[word1
//							.getBelongto()][word2.getBelongto()];
//				} else
//					classDistMat[i][j] = sameClassVal;
//			}
//		}
		for (TDCentroid tdc : resTDCentroid) {
			if(tdc.GroupedArticle.size()==0)
				break;
			double[] tdcWeight=tdc.GroupedArticle.get(0).vectorSpace;
			double sum = 0, maxVal = tdcWeight[0], minVal = tdcWeight[0];
			double maxWordWeight=tdcWeight[0];
			int maxWordIndex=0;
			for(int i=0;i<len;i++){
				if(tdcWeight[i]>maxWordWeight){
					maxWordWeight=tdcWeight[i];
					maxWordIndex=i;
				}
				maxVal = Math.max(maxVal, tdcWeight[i]);
  				minVal = Math.min(minVal,tdcWeight[i]);
  				sum+=tdcWeight[i];
			}
			double avgVal = sum / len ;
			for (int i = 0; i < len; i++) {
				tdcWeight[i]=Math.abs((tdcWeight[i] - minVal) / (maxVal - minVal));
			}
			for(int i=0;i<len;i++){
				if(i!=maxWordIndex){
					classDistMat[i][maxWordIndex]+= round(tdcWeight[i]*10, 3);
					classDistMat[maxWordIndex][i]+= round(tdcWeight[i]*10, 3);
				}
			}
			for(int i=0;i<len;i++){
				for(int j=0;j<len;j++){
					if(classDistMat[i][j]==0&&
							tdcWeight[i]>=avgVal&&tdcWeight[j]>=avgVal){
						classDistMat[i][j]+=0.1;
					}
				}
					
			}
		}
		// 归一化
		//classDistMat = normalizeMat(classDistMat, len);
		
		
		return classDistMat;
	}
	/**
	 *  两个热词在同一文章出现
	 * @return
	 */
	
	private double[][] calSameArticleMat(){
		int len = hotWords.size();
		sameArticleMat=new double[len][len];
		for (int i = 0; i < len; i++)
			for (int j = 0; j < len; j++) {
				for (TDArticle article : articleLists) {
					Map<String, Double> map=article.getTermsWeights();
					if(map.containsKey(hotWords.get(i).getContent())&&
							map.containsKey(hotWords.get(j).getContent())){
						sameArticleMat[i][j]+=1;
					}
				}
			}
		sameArticleMat=normalizeMat(sameArticleMat,len);
		return sameArticleMat;
	}
	
	/**
	 * 新闻同现率距离矩阵
	 * 
	 * @return
	 */
	private double[][] calOverlapMat() {
		int len = hotWords.size();
		overlapMat = new double[len][len];
		for (int i = 0; i < len; i++)
			for (int j = 0; j < len; j++) {
				overlapMat[i][j]=0;
				double overlap = 0;
				// 两个热词代表的新闻集合，求交集
				List<ArticleShow> articlesi = hotWords.get(i).articleViews;
				List<ArticleShow> articlesj = hotWords.get(j).articleViews;
				double sum = articlesi.size() + articlesj.size();
				for (ArticleShow si : articlesi) {
					for (ArticleShow sj : articlesj) {
						if (si.equals(sj)) {
							overlap++;
							sum--;
						}
					}
				}
				if(sum!=0)
					overlapMat[i][j] = overlap / sum;
			}
		// 归一化
		overlapMat = normalizeMat(overlapMat, len);
		return overlapMat;
	}

	/**
	 * 矩阵归一化 [0,1]
	 * 
	 * @param mat
	 * @param len
	 * @return
	 */
	private double[][] normalizeMat(double[][] mat, int len) {
		double maxVal = mat[0][0], minVal = mat[0][0];
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				maxVal = Math.max(maxVal, mat[i][j]);
				minVal = Math.min(minVal, mat[i][j]);
			}
		}
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				if(mat[i][j]!=0&&maxVal != minVal){
					mat[i][j] = (mat[i][j] - minVal) / (maxVal - minVal);
					mat[i][j] = round(mat[i][j], 3);
				}
			}
		}
		return mat;
	}

	public static double round(double value, int places) {
		if (places < 0)
			return 0;
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
