package pomonitor.test;

import pomonitor.util.DateUnify;

public class TestDataUn {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DateUnify d = new DateUnify();
		String st = "....2011-1-1 7935487879384378";
		String dd = d.DataUn(st);
		System.out.print(dd);

	}

}
