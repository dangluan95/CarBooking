package com.dangluan.bean;

public class User 
{
	private int id;
	private String userName;
	private String passWord;
	private String name;
	private String numberPhone;
	private int age;
	private String address;
	private String email;
	private int categoryMemmber;
	public User() {
		super();
	}
	public User(String userName, String passWord, String name, String numberPhone, int age, String address,
			String email) {
		super();
		this.userName = userName;
		this.passWord = passWord;
		this.name = name;
		this.numberPhone = numberPhone;
		this.age = age;
		this.address = address;
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumberPhone() {
		return numberPhone;
	}
	public void setNumberPhone(String numberPhone) {
		this.numberPhone = numberPhone;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getCategoryMemmber() {
		return categoryMemmber;
	}
	public void setCategoryMemmber(int categoryMemmber) {
		this.categoryMemmber = categoryMemmber;
	}

}
