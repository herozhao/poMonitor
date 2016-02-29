package pomonitor.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import pomonitor.entity.News;
import pomonitor.entity.NewsEntity;

/**
 * 
 * @author 市委
 * 
 */
public class NewsAndNewsEnriryTran {
	public static NewsEntity newsToNewsEntity(News news) {
		NewsEntity newsEntity = new NewsEntity();
		newsEntity.setId(news.getId());
		newsEntity.setTitle(news.getTitle());
		newsEntity.setUrl(news.getUrl());
		newsEntity.setContent(news.getContent());
		newsEntity.setWeb(news.getWeb());
		newsEntity.setTime(news.getTime().toLocaleString().substring(0, 9));
		newsEntity.setAllContent(news.getAllContent());
		newsEntity.setContentPath(news.getContentPath());
		newsEntity.setFailedCount(news.getFailedCount());
		String key = news.getKeyWords();
		java.util.List<String> keyw = new ArrayList<String>();
		String[] keys = key.split("#");
		for (int j = 0; j < keys.length; j++) {
			if (keys[j].equals("#") || keys[j].equals(""))
				;
			else
				keyw.add(keys[j]);
		}
		// List<String> list = java.util.Arrays.asList(key);
		// for(int i=0;i<list.size();i++){
		System.out.println(keyw + "!!!!!");
		newsEntity.setKeywords(keyw);
		if (news.getIsFinsh() == 1) {

			newsEntity.setFinish(true);

		} else {
			newsEntity.setFinish(false);
		}
		if (news.getIsFailed() == 1) {
			newsEntity.setFailed(true);
		} else {
			newsEntity.setFailed(false);
		}
		if (news.getIsWorking() == 1) {
			newsEntity.setWorking(true);
		} else {
			newsEntity.setWorking(false);
		}
		return newsEntity;
	}

	public static News newsEntityToNews(NewsEntity newsentity) {
		News news = new News();
		news.setId(newsentity.getId());
		news.setTitle(newsentity.getTitle());
		news.setUrl(newsentity.getUrl());
		news.setContent(newsentity.getContent());
		news.setWeb(newsentity.getWeb());
		// 对日期的过滤处理
		String dateStr = newsentity.getTime();
		dateStr = DateUnify.DataUn(dateStr);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = simpleDateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		news.setTime(date);
		news.setAllContent(newsentity.getAllContent());
		news.setContentPath(newsentity.getContentPath());
		news.setFailedCount(newsentity.getFailedCount());
		// 将newsentity中的list类型的 keywords转化为String类型
		String keyWords = "";
		for (String s : newsentity.getKeywords()) {
			keyWords += s + "#";
		}
		news.setKeyWords(keyWords);
		if (newsentity.isFinish() == true) {
			news.setIsFinsh(1);
		} else {
			news.setIsFinsh(0);
		}
		if (newsentity.isFailed() == true) {
			news.setIsFailed(1);
		} else {
			news.setIsFailed(0);
		}
		if (newsentity.isWorking() == true) {
			news.setIsWorking(1);
		} else {
			news.setIsWorking(0);
		}
		return news;

	}
}
