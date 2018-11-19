package model;

import java.sql.Date;

public class Staff {
	private int id;
	private String email;
	private String password;
	private String name;
	private String surname;
	private String id_number;
	private int role;
	private int branch;
	private Date date;
	private String mobile_number;
	private String address;
	private String picture;
	
	public Staff(int id,String email,String password,String name,String surname,String id_number,int role,int branch,Date date,String mobile_number,String address,String picture){
		this.id=id;
		this.email=email;
		this.password=password;
		this.name=name;
		this.surname=surname;
		this.id_number=id_number;
		this.branch=branch;
		this.date=date;
		this.picture=picture;
		this.mobile_number=mobile_number;
		this.address=address;
		this.role=role;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getId_number() {
		return id_number;
	}
	public void setId_number(String id_number) {
		this.id_number = id_number;
	}
	public int getBranch_num() {
		return branch;
	}
	public void setBranch_num(int branch_num) {
		this.branch = branch_num;
	}

	public String getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
	
}
