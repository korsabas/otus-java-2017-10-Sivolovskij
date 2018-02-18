package ru.podelochki.otus.homework10.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="addresses")
public class AddressDataSet extends DataSet{
	
	@Column(name="address")
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
