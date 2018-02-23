package ru.podelochki.otus.homework12.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name = "users")
public class UsersDataSet extends DataSet{
	
	@Column(name="name")
	private String name;
	@Column(name="age")
	private int age;
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="address")
	private AddressDataSet address;
	
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL)
	private List<PhoneDataSet> phones;
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
	public AddressDataSet getAddress() {
		return address;
	}
	public void setAddress(AddressDataSet address) {
		this.address = address;
	}
	public List<PhoneDataSet> getPhones() {
		return phones;
	}
	public void setPhones(List<PhoneDataSet> phones) {
		this.phones = phones;
	}
	

}
