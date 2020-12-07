package com.genuinedeveloper.db_entities;

import java.sql.Date;

public class Users extends Entity{

	private Integer id;
	
	private String name_first;
	
	private String name_last;
	
	private Date dob;
	
	private String hashed_username;
	
	private String hashed_password;
	
	private String security_question1;
	
	private String security_question2;
	
	private String security_question3;
	
	private String security_answer1;
	
	private String security_answer2;
	
	private String security_answer3;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName_first() {
		return name_first;
	}

	public void setName_first(String name_first) {
		this.name_first = name_first;
	}

	public String getName_last() {
		return name_last;
	}

	public void setName_last(String name_last) {
		this.name_last = name_last;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getHashed_username() {
		return hashed_username;
	}

	public void setHashed_username(String hashed_username) {
		this.hashed_username = hashed_username;
	}

	public String getHashed_password() {
		return hashed_password;
	}

	public void setHashed_password(String hashed_password) {
		this.hashed_password = hashed_password;
	}

	public String getSecurityQuestion1() {
		return security_question1;
	}

	public void setSecurityQuestion1(String security_question1) {
		this.security_question1 = security_question1;
	}

	public String getSecurityQuestion2() {
		return security_question2;
	}

	public void setSecurityQuestion2(String security_question2) {
		this.security_question2 = security_question2;
	}

	public String getSecurityQuestion3() {
		return security_question3;
	}

	public void setSecurity_question3(String security_question3) {
		this.security_question3 = security_question3;
	}

	public String getSecurityAnswer1() {
		return security_answer1;
	}

	public void setSecurityAnswer1(String security_answer1) {
		this.security_answer1 = security_answer1;
	}

	public String getSecurityAnswer2() {
		return security_answer2;
	}

	public void setSecurityAnswer2(String security_answer2) {
		this.security_answer2 = security_answer2;
	}

	public String getSecurityAnswer3() {
		return security_answer3;
	}

	public void setSecurityAnswer3(String security_answer3) {
		this.security_answer3 = security_answer3;
	}
	
	
}
