package pomonitor.listener;

import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import pomonitor.analyse.TendDiscoveryAnalyse;

public class ArticleTendService implements ServletContextListener {
	// 用于设置服务定时启动的定时器
	private Timer timer = new Timer();

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("系统结束");
		// 关闭计时
		timer.cancel();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// 获取系统上下文
		ServletContext context = arg0.getServletContext();
		// 获取配置参数中的倾向性分析的时间间隔
		String tendIntervalValue = context.getInitParameter("tendInterval");
		int tendInterval = Integer.parseInt(tendIntervalValue);
		// 获取配置参数中倾向性分析的开始时间
		String tendStartTime = context.getInitParameter("tendStartTime");
		int tendStart = Integer.parseInt(tendStartTime);
		// 将时间改为date形式,从当前时时间的设置点开始
		Date startDate = new Date(System.currentTimeMillis());
		startDate.setHours(tendStart);
		//
		System.out.println(startDate.getTime());
		// 倘若超过当前时间则马上执行一次，以后按照时间间隔定时执行
		// 倘若没有超过时间间隔则等到开始点时准时执行
		timer.schedule(new MyTask(), startDate, tendInterval * 60 * 60 * 1000);

	}

	class MyTask extends java.util.TimerTask {
		public MyTask() {
		}

		@Override
		public void run() {
			System.out.println("倾向性分析服务开始执行" + new Date().toString());
			// 开启分析服务
			long now = System.currentTimeMillis();
			new TendDiscoveryAnalyse().startTendAnalyse();
			long end = System.currentTimeMillis();
			System.out.println("总共运行：" + (end - now) / 1000 + "秒");
			System.out.println("倾向性分析服务执行结束" + new Date().toString());
		}
	}

}
