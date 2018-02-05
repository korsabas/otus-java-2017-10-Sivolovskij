package ru.podelochki.otus.homework8;

import java.util.List;
import java.util.Objects;

public class ComplexClass {
	private SimpleClass sClass;
	private List<SimpleClass> list;
	private int[] items;
	
	public ComplexClass() {

	}
	
	@Override
    public int hashCode() {
        return Objects.hash(sClass, list, items);
    }
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexClass obj = (ComplexClass) o;
        boolean result = true;
        for (int i = 0; i < this.list.size(); i++) {
        	result = result && this.list.get(i).equals(obj.list.get(i));
        }
        return Objects.equals(sClass, obj.sClass) && result;
    }

	public SimpleClass getsClass() {
		return sClass;
	}

	public void setsClass(SimpleClass sClass) {
		this.sClass = sClass;
	}

	public List<SimpleClass> getList() {
		return list;
	}

	public void setList(List<SimpleClass> list) {
		this.list = list;
	}

	public int[] getItems() {
		return items;
	}

	public void setItems(int[] items) {
		this.items = items;
	}

}
