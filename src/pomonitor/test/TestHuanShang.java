package pomonitor.test;

import java.util.HashMap;
import java.util.Set;

import org.junit.Test;

import pomonitor.clawer.newsanalyse.HuaShengAnalyse;
import pomonitor.clawer.newsanalyse.HuaShengAnalyse;
import pomonitor.entity.NewsEntity;

public class TestHuanShang {
	@Test
	public void testGetPageCount() {
		HuaShengAnalyse analyse = new HuaShengAnalyse("���", true);
		int count = analyse.getPageCount("�ϻ���ѧ", false);
		System.out.println(count);
	}

	@Test
	public void testUrlAnalyse() {
		HuaShengAnalyse analyse = new HuaShengAnalyse("���", true);
		analyse.getPageCount("�ϻ���ѧ", false);
		String newUrl = analyse.urlAnalyse(1);
		System.out.println(newUrl);
	}

	@Test
	public void tesAnalyseAnyPage() {
		HuaShengAnalyse analyse = new HuaShengAnalyse("���", true);
		analyse.getPageCount("�ϻ���ѧ", false);
		String newUrl = analyse.urlAnalyse(1);
		analyse.analyseAnyPage(newUrl);
		// List<Object> list=analyse.analyseAnyPage(newUrl);
		// for(Object o:list){
		// SinaNews sinaEntity=(SinaNews) o;
		// System.out.println(sinaEntity.getTitle());
		// System.out.println(sinaEntity.getUrl());
		// System.out.println(sinaEntity.getWeb());
		// System.out.println(sinaEntity.getTime());
		// System.out.println(sinaEntity.getContent());
		// }

	}

	@Test
	public void tesAnalyseAllPage() {
		// RedNetAnalyse analyse=new
		// RedNetAnalyse("http://s.rednet.cn/?scope=1&q=%E5%8D%97%E5%8D%8E%E5%A4%A7%E5%AD%A6&title=0&page_size=10&date_range=4&orderby=1&page=1");
		HuaShengAnalyse analyse = new HuaShengAnalyse("���", true);
		HashMap<String, Object> map = analyse.analyseAllPage("�ϻ���ѧ", false);
		Set<String> set = map.keySet();
		for (String s : set) {
			NewsEntity sinaEntity = (NewsEntity) map.get(s);
			System.out.println(sinaEntity.getTitle());
			System.out.println(sinaEntity.getUrl());
			System.out.println(sinaEntity.getWeb());
			System.out.println(sinaEntity.getTime());
			System.out.println(sinaEntity.getContent());
			System.out.println(map.size());
		}

	}

}
