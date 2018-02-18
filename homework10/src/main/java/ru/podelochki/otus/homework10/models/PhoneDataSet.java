package ru.podelochki.otus.homework10.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="phones")
public class PhoneDataSet extends DataSet{
	
	@Column(name="phone")
	private String phone;
	
	@ManyToOne
    @JoinColumn(name = "user_id")
    private UsersDataSet user;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public UsersDataSet getUser() {
		return user;
	}

	public void setUser(UsersDataSet user) {
		this.user = user;
	}
	
	
	
}
