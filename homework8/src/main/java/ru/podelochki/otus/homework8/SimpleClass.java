package ru.podelochki.otus.homework8;

import java.util.Objects;

public class SimpleClass {
	private int a;
	private String s;
	private char c;
	public SimpleClass (int a, String s, char c) {
		this.a = a;
		this.s = s;
		this.c = c;
	}
	
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleClass obj = (SimpleClass) o;
        return a == obj.a && s.equals(obj.s) && c == obj.c;
    }
	
	@Override
    public int hashCode() {
        return Objects.hash(a, s, c);
    }


	public int getA() {
		return a;
	}


	public void setA(int a) {
		this.a = a;
	}


	public String getS() {
		return s;
	}


	public void setS(String s) {
		this.s = s;
	}


	public char getC() {
		return c;
	}


	public void setC(char c) {
		this.c = c;
	}
}
