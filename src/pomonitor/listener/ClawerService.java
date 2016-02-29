package pomonitor.listener;

import java.io.File;
import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import pomonitor.clawer.NewsCrawler;
import pomonitor.clawer.newsanalyse.FenghuangAnalyse;
import pomonitor.clawer.newsanalyse.GuangMing;
import pomonitor.clawer.newsanalyse.HuaShengAnalyse;
import pomonitor.clawer.newsanalyse.RedNetAnalyse;
import pomonitor.clawer.newsanalyse.RedNetAnalyseF;
import pomonitor.clawer.newsanalyse.SinaAnalyse;
import pomonitor.clawer.newsanalyse.SouHuAnalyse;
import pomonitor.clawer.newsanalyse.TengXunAnalyse;
import pomonitor.clawer.newsanalyse.WangYi;
import pomonitor.clawer.newsanalyse.WeiXinAnalyse;
import pomonitor.clawer.newsanalyse.XinHuaAnalyse;
import pomonitor.clawer.newsanalyse.ZhongXinAnalyse;
import pomonitor.util.PropertiesReader;

public class ClawerService implements ServletContextListener {

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
		String clawerIntervalStr = context.getInitParameter("clawerInterval");
		int clawerInterval = Integer.parseInt(clawerIntervalStr);
		// 获取配置参数中倾向性分析的开始时间
		String clawerStartStr = context.getInitParameter("clawerStartTime");
		int clawerStart = Integer.parseInt(clawerStartStr);
		// 将时间改为date形式,从当前时时间的设置点开始
		Date startDate = new Date(System.currentTimeMillis());
		startDate.setHours(clawerStart);
		//
		System.out.println(startDate.getTime());
		// 倘若超过当前时间则马上执行一次，以后按照时间间隔定时执行
		// 倘若没有超过时间间隔则等到开始点时准时执行
		timer.schedule(new MyTask(), startDate, clawerInterval * 60 * 60 * 1000);

	}

	public void startClawer(boolean isLatest, String filePath,
			String whatYouWant) {
		NewsCrawler clawer = new NewsCrawler(filePath);
		SinaAnalyse analyse = new SinaAnalyse("新浪", true);
		RedNetAnalyse redAnalyse = new RedNetAnalyse("红网", true);
		RedNetAnalyseF redAnalysef = new RedNetAnalyseF("红网论坛", false);
		ZhongXinAnalyse zxAnalyse = new ZhongXinAnalyse("中新网", true);
		HuaShengAnalyse hsAnalyse = new HuaShengAnalyse("华声在线", true);
		GuangMing gmAnalyse = new GuangMing("光明网", true);
		WeiXinAnalyse weixinAnalyse = new WeiXinAnalyse("微信", true);
		XinHuaAnalyse xhAnalyse = new XinHuaAnalyse("新华网", true);
		WangYi wangyi = new WangYi("网易", true);
		TengXunAnalyse tengXun = new TengXunAnalyse("腾讯", true);
		FenghuangAnalyse fenghuang = new FenghuangAnalyse("凤凰", true);
		SouHuAnalyse souhu = new SouHuAnalyse("搜狐", true);
		clawer.addAnalyse(analyse);
		clawer.addAnalyse(redAnalysef);
		clawer.addAnalyse(redAnalyse);
		clawer.addAnalyse(zxAnalyse);
		clawer.addAnalyse(gmAnalyse);
		clawer.addAnalyse(hsAnalyse);

		// 有错误 clawer.addAnalyse(weixinAnalyse);
		clawer.addAnalyse(xhAnalyse);
		clawer.addAnalyse(wangyi);
		clawer.addAnalyse(fenghuang);
		clawer.addAnalyse(souhu);
		clawer.addAnalyse(tengXun);
		clawer.clawerAll(whatYouWant, isLatest);
		clawer.start(10);

	}

	class MyTask extends java.util.TimerTask {
		public MyTask() {
		}

		@Override
		public void run() {
			System.out.println("倾向性分析服务开始执行" + new Date().toString());
			String filePath = new PropertiesReader()
					.getPropertyByName("clawerDir");
			File file = new File(filePath);
			boolean isLatest = true;
			if (!file.exists()) {
				isLatest = false;
				file.mkdir();
			}
			// 开启分析服务
			long now = System.currentTimeMillis();
			// 这里写爬虫启动关闭程序
			startClawer(isLatest, filePath, "南华大学");
			long end = System.currentTimeMillis();
			System.out.println("总共运行：" + (end - now) / 1000 + "秒");
			System.out.println("爬虫服务执行结束" + new Date().toString());
		}
	}
}
