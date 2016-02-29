package pomonitor.test;

import java.util.List;
import java.util.ArrayList;

import pomonitor.entity.News;
import pomonitor.entity.NewsEntity;
import pomonitor.util.NewsAndNewsEnriryTran;

public class TestNewsandNewsentity {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		News news1 = new News();
		NewsEntity news2 = new NewsEntity();
		news1.setAllContent("yi");
		news1.setContent("er");
		news1.setContentPath("san");
		news1.setFailedCount(4);
		news1.setId("wu");
		news1.setIsFailed(1);
		news1.setIsFinsh(0);
		news1.setIsWorking(1);
		/*
		 * List<String> keyword = new ArrayList<String>(); keywod.add(0,"1");
		 * keyword.add(1,"2");
		 */
		String keyword = "1 ,2 ,3";
		news1.setKeyWords(keyword);
		news1.setRelId(6);
		// news1.setTime("2011-1-1");
		news1.setTitle("zhguo");
		news1.setUrl("http");
		news1.setWeb("hong");
		NewsAndNewsEnriryTran nn = new NewsAndNewsEnriryTran();
		// nn.newsEntityToNews(news1);
		System.out.println(nn.newsToNewsEntity(news1).getId());
		System.out.println(nn.newsToNewsEntity(news1).getAllContent());
		System.out.println(nn.newsToNewsEntity(news1).getContent());
		System.out.println(nn.newsToNewsEntity(news1).getContentPath());
		System.out.println(nn.newsToNewsEntity(news1).getFailedCount());
		System.out.println(nn.newsToNewsEntity(news1).getTime());
		System.out.println(nn.newsToNewsEntity(news1).getTitle());
		System.out.println(nn.newsToNewsEntity(news1).getUrl());
		System.out.println(nn.newsToNewsEntity(news1).getWeb());
		System.out.println(nn.newsToNewsEntity(news1).getKeywords().get(0)
				+ "~~~~~~~~~~~~~");
		System.out.println(nn.newsToNewsEntity(news1).isFailed());
		System.out.println(nn.newsToNewsEntity(news1).isFinish());
		System.out.println(nn.newsToNewsEntity(news1).isWorking());
		//
		news2.setAllContent("yi");
		news2.setContent("er");
		news2.setContentPath("san");
		news2.setFailedCount(4);
		news2.setId("wu");
		news2.setFailed(true);
		news2.setFinish(true);
		news2.setWorking(false);
		java.util.List<String> keyword2 = new ArrayList<String>();
		keyword2.add(0, "1");
		keyword2.add(1, "2");
		news2.setKeywords(keyword2);
		news2.setTime("2015-2-5");
		news2.setTitle("zhguo");
		news2.setUrl("http");
		news2.setWeb("hong");
		// System.out.print(nn.newsEntityToNews(news1).);

		System.out.println(nn.newsEntityToNews(news2).getId());
		System.out.println(nn.newsEntityToNews(news2).getAllContent());
		System.out.println(nn.newsEntityToNews(news2).getContent());
		System.out.println(nn.newsEntityToNews(news2).getContentPath());
		System.out.println(nn.newsEntityToNews(news2).getFailedCount());
		System.out.println(nn.newsEntityToNews(news2).getTime());
		System.out.println(nn.newsEntityToNews(news2).getTitle());
		System.out.println(nn.newsEntityToNews(news2).getUrl());
		System.out.println(nn.newsEntityToNews(news2).getWeb());
		System.out.println(nn.newsEntityToNews(news2).getKeyWords());
		System.out.println(nn.newsEntityToNews(news2).getIsFailed());
		System.out.println(nn.newsEntityToNews(news2).getIsFinsh());
		System.out.println(nn.newsEntityToNews(news2).getIsWorking());
		//
		List<String> ll = new ArrayList<String>();

		String a = "123";
		// String[] b=new String[5];
		for (int i = 0; i < a.length(); i++) {
			ll.add(a.substring(i, i + 1));
			// ll.add(a.charAt(i));

		}
		System.out.println(ll.get(0));
		String c = ll.toString();
		System.out.println(c);

	}

}
