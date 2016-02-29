package pomonitor.test;

import org.junit.Test;

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

public class TestNewsCrawler {
	@Test
	public void testClawlerAll() {
		NewsCrawler clawer = new NewsCrawler("D:/test/");
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
		// clawer.addAnalyse(redAnalysef);
		// clawer.addAnalyse(redAnalyse);
		// clawer.addAnalyse(zxAnalyse);
		// clawer.addAnalyse(gmAnalyse);
		// clawer.addAnalyse(hsAnalyse);

		// 有错误 clawer.addAnalyse(weixinAnalyse);
		// clawer.addAnalyse(xhAnalyse);
		// clawer.addAnalyse(wangyi);
		// clawer.addAnalyse(fenghuang);
		// clawer.addAnalyse(souhu);
		clawer.addAnalyse(tengXun);

		clawer.clawerAll("南华大学", true);
		clawer.start(10);

	}
}
