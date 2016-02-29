package pomonitor.test;

import java.util.ArrayList;
import java.util.List;

import pomonitor.entity.EntityManagerHelper;
import pomonitor.entity.News;
import pomonitor.entity.NewsDAO;
import pomonitor.entity.Sensword;
import pomonitor.entity.SenswordDAO;
import pomonitor.entity.User;
import pomonitor.entity.UserDAO;

public class TestUserAndSensword {

	public static void main(String[] args) {
		NewsDAO nd = new NewsDAO();
		List<News> newsList = nd.findBetweenDate("2009-07-20", "2011-02-20");
		System.out.println(newsList.size());
	}
}
