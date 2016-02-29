package pomonitor.test;

import java.util.ArrayList;

import pomonitor.analyse.HotWordDiscoveryAnalyse;
import pomonitor.analyse.entity.RetHotWord;
import pomonitor.analyse.entity.RetLink;

public class TestRelvanceMat {

	public static void main(String[] args) {
		HotWordDiscoveryAnalyse tdDiscovery = new HotWordDiscoveryAnalyse();
		
		tdDiscovery.discoverHotWords("2014-01-01", "2015-03-10", 1);
	
		ArrayList<RetHotWord> retNodes = tdDiscovery.getRetHotWords();
		double[][] relevanceMat = tdDiscovery.getRelevanceMat();
		for (int i = 0; i < relevanceMat.length; i++) {
			for (int j = i + 1; j < relevanceMat.length; j++) {

				if (relevanceMat[i][j] < 0.3)
					continue;
				else {
					System.out.print(retNodes.get(i).getName()+"\t"+retNodes.get(j).getName()+"\t");
					System.out.println(relevanceMat[i][j] * 100+" ");
				}

			}
		}
	}

}
