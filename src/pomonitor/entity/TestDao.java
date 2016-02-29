package pomonitor.entity;

import java.util.List;

import org.junit.Test;

public class TestDao {
	@Test
	public void add() {
		LeverWord lword = new LeverWord();
		lword.setId(1);
		lword.setScore(342.0f);
		lword.setWord("你好");

		LeverWordDAO lwd = new LeverWordDAO();
		EntityManagerHelper.beginTransaction();
		lwd.save(lword);
		EntityManagerHelper.commit();
	}

	@Test
	public void upDate() {
		LeverWord lword = new LeverWord();
		lword.setId(1);
		lword.setScore(342.0f);
		lword.setWord("我不好");

		LeverWordDAO lwd = new LeverWordDAO();
		EntityManagerHelper.beginTransaction();
		lwd.update(lword);
		EntityManagerHelper.commit();
	}

	@Test
	public void delete() {
		LeverWord lword = new LeverWord();
		lword.setId(1);
		lword.setScore(342.0f);
		lword.setWord("我不好");

		LeverWordDAO lwd = new LeverWordDAO();
		EntityManagerHelper.beginTransaction();
		lwd.update(lword);
		EntityManagerHelper.commit();
	}

	@Test
	public void find() {

		LeverWordDAO lwd = new LeverWordDAO();
		List<LeverWord> list = lwd.findByWord("我不好");
		String word = list.get(0).getWord();
		System.out.println(word);

	}

}
