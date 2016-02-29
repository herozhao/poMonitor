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

	// �������÷���ʱ�����Ķ�ʱ��
	private Timer timer = new Timer();

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("ϵͳ����");
		// �رռ�ʱ
		timer.cancel();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// ��ȡϵͳ������
		ServletContext context = arg0.getServletContext();
		// ��ȡ���ò����е������Է�����ʱ����
		String clawerIntervalStr = context.getInitParameter("clawerInterval");
		int clawerInterval = Integer.parseInt(clawerIntervalStr);
		// ��ȡ���ò����������Է����Ŀ�ʼʱ��
		String clawerStartStr = context.getInitParameter("clawerStartTime");
		int clawerStart = Integer.parseInt(clawerStartStr);
		// ��ʱ���Ϊdate��ʽ,�ӵ�ǰʱʱ������õ㿪ʼ
		Date startDate = new Date(System.currentTimeMillis());
		startDate.setHours(clawerStart);
		//
		System.out.println(startDate.getTime());
		// ����������ǰʱ��������ִ��һ�Σ��Ժ���ʱ������ʱִ��
		// ����û�г���ʱ������ȵ���ʼ��ʱ׼ʱִ��
		timer.schedule(new MyTask(), startDate, clawerInterval * 60 * 60 * 1000);

	}

	public void startClawer(boolean isLatest, String filePath,
			String whatYouWant) {
		NewsCrawler clawer = new NewsCrawler(filePath);
		SinaAnalyse analyse = new SinaAnalyse("����", true);
		RedNetAnalyse redAnalyse = new RedNetAnalyse("����", true);
		RedNetAnalyseF redAnalysef = new RedNetAnalyseF("������̳", false);
		ZhongXinAnalyse zxAnalyse = new ZhongXinAnalyse("������", true);
		HuaShengAnalyse hsAnalyse = new HuaShengAnalyse("��������", true);
		GuangMing gmAnalyse = new GuangMing("������", true);
		WeiXinAnalyse weixinAnalyse = new WeiXinAnalyse("΢��", true);
		XinHuaAnalyse xhAnalyse = new XinHuaAnalyse("�»���", true);
		WangYi wangyi = new WangYi("����", true);
		TengXunAnalyse tengXun = new TengXunAnalyse("��Ѷ", true);
		FenghuangAnalyse fenghuang = new FenghuangAnalyse("���", true);
		SouHuAnalyse souhu = new SouHuAnalyse("�Ѻ�", true);
		clawer.addAnalyse(analyse);
		clawer.addAnalyse(redAnalysef);
		clawer.addAnalyse(redAnalyse);
		clawer.addAnalyse(zxAnalyse);
		clawer.addAnalyse(gmAnalyse);
		clawer.addAnalyse(hsAnalyse);

		// �д��� clawer.addAnalyse(weixinAnalyse);
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
			System.out.println("�����Է�������ʼִ��" + new Date().toString());
			String filePath = new PropertiesReader()
					.getPropertyByName("clawerDir");
			File file = new File(filePath);
			boolean isLatest = true;
			if (!file.exists()) {
				isLatest = false;
				file.mkdir();
			}
			// ������������
			long now = System.currentTimeMillis();
			// ����д���������رճ���
			startClawer(isLatest, filePath, "�ϻ���ѧ");
			long end = System.currentTimeMillis();
			System.out.println("�ܹ����У�" + (end - now) / 1000 + "��");
			System.out.println("�������ִ�н���" + new Date().toString());
		}
	}
}
