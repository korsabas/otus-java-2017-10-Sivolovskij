package ru.podelochki.otus.homework9.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserDataSet extends DataSet{
	
	@Column(name="name")
	private String name;
	
	@Column(name = "age")
	private int age;
	public UserDataSet() {
		
	}
	
	public UserDataSet(String name, int age) {
		super();
		this.age = age;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}
