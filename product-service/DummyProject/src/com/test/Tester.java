package com.test;

public class Tester {
	public static void main(String[] args) {

		Object obj = getObject(2);
		System.out.println(obj.getClass().getName());
		Emp e=(Emp) obj;
		e.printSal();
		e.printSal();
		
	}

	private static Object getObject(int type) {

		if (type == 1)
			return new Cust();
		else if (type == 2)
			return new Emp();
		
		return new Object();
	}

}
