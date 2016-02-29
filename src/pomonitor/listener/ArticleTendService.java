package pomonitor.listener;

import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import pomonitor.analyse.TendDiscoveryAnalyse;

public class ArticleTendService implements ServletContextListener {
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
		String tendIntervalValue = context.getInitParameter("tendInterval");
		int tendInterval = Integer.parseInt(tendIntervalValue);
		// ��ȡ���ò����������Է����Ŀ�ʼʱ��
		String tendStartTime = context.getInitParameter("tendStartTime");
		int tendStart = Integer.parseInt(tendStartTime);
		// ��ʱ���Ϊdate��ʽ,�ӵ�ǰʱʱ������õ㿪ʼ
		Date startDate = new Date(System.currentTimeMillis());
		startDate.setHours(tendStart);
		//
		System.out.println(startDate.getTime());
		// ����������ǰʱ��������ִ��һ�Σ��Ժ���ʱ������ʱִ��
		// ����û�г���ʱ������ȵ���ʼ��ʱ׼ʱִ��
		timer.schedule(new MyTask(), startDate, tendInterval * 60 * 60 * 1000);

	}

	class MyTask extends java.util.TimerTask {
		public MyTask() {
		}

		@Override
		public void run() {
			System.out.println("�����Է�������ʼִ��" + new Date().toString());
			// ������������
			long now = System.currentTimeMillis();
			new TendDiscoveryAnalyse().startTendAnalyse();
			long end = System.currentTimeMillis();
			System.out.println("�ܹ����У�" + (end - now) / 1000 + "��");
			System.out.println("�����Է�������ִ�н���" + new Date().toString());
		}
	}

}
