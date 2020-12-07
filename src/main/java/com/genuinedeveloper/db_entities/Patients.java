package com.genuinedeveloper.db_entities;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;

import javax.imageio.ImageIO;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javafx.scene.image.Image;

public class Patients extends Entity {
	
	  private Integer id;
	  
	  private char[] name_first;
	  
	  private char[] name_middle;
	  
	  private char[] name_last;
	  
	  private Gender gender;
	  
	  private Date dob_date;
	  
	  private Time dob_time;
	  
	  private char[] phone;
	  
	  private char[] addressLine1;
	  
	  private char[] addressLine2;
	  
	  private String city;
	  
	  private String state;
	  
	  private Integer postalCode;
	  
	  private String country;
	  
	  private String imageUrl;
	  
	  private byte[] image;
	  
	  /*public static Base64 base64;
	  
	  public static Base64.Decoder decoder;
	  
	  public static Base64.Encoder encoder;*/
	  
	  
	  public enum Gender {
		  MALE,
		  FEMALE
	  }

	  //Constructor for creating a patient to push to database because the database will assign id via auto_increment
	  public Patients(char[] name_first, char[] name_middle, char[] name_last, Gender gender, Date dob_date, Time dob_time,
			  char[] phone, char[] addressLine1, char[] addressLine2, String city, String state, Integer postalCode,
			String country, byte[] image) {
		  
			super();
			this.name_first = name_first;
			this.name_middle = name_middle;
			this.name_last = name_last;
			this.gender = gender;
			this.dob_date = dob_date;
			this.dob_time = dob_time;
			this.phone = phone;
			this.addressLine1 = addressLine1;
			this.addressLine2 = addressLine2;
			this.city = city;
			this.state = state;
			this.postalCode = postalCode;
			this.country = country;
			this.image = image;
			
	  }
	  
	  //Clone constructor
	  public Patients(int id, char[] name_first, char[] name_middle, char[] name_last, Gender gender, Date dob_date, Time dob_time,
			  char[] phone, char[] addressLine1, char[] addressLine2, String city, String state, Integer postalCode,
			String country, byte[] image) {
		  
			super();
			this.id = id;
			this.name_first = name_first;
			this.name_middle = name_middle;
			this.name_last = name_last;
			this.gender = gender;
			this.dob_date = dob_date;
			this.dob_time = dob_time;
			this.phone = phone;
			this.addressLine1 = addressLine1;
			this.addressLine2 = addressLine2;
			this.city = city;
			this.state = state;
			this.postalCode = postalCode;
			this.country = country;
			this.image = image;
			
	  }
	 
	  public Patients () {
		  
	  }
	  

	  /*
	   * Begin Getters
	   */
	  public Integer getId() {
		  
	    return id;
	    
	  }
	  
	  public char[] getNameFirst() {
		  
		  return name_first;
		  
	  }
	  
	  public char[] getNameMiddle() {
		  
		  return name_middle;
		  
	  }
	  
	  public char[] getNameLast() {
		  
		  return name_last;
		  
	  }
	  
	  public Gender getGender() {
		  
		  return gender;
		  
	  }
	  
	  public Date getDobDate () {
		  
		  return dob_date;
		  
	  }
	  
	  public Time getDobTime () {
		  
		  return dob_time;
		  
	  }
	  
	  public char[] getPhone() {
		  
		  return phone;
		  
	  }

	  public char[] getAddressLine1() {
		  
		  return addressLine1;
		  
	  }

	  public char[] getAddressLine2() {
		  
		  return addressLine2;
		  
	  }

	  public String getCity() {
		  
		  return city;
		  
	  }

	  public String getState() {
		  
		  return state;
		  
	  }

	  public Integer getPostalCode() {
		  
		  return postalCode;
		  
	  }

	  public String getCountry() {
		  
		  return country;
		  
	  }
	  
	  public String getImageUrl () {
		  
		  return imageUrl;
		  
	  }
	  
	  public byte[] getImage () {
		  
		  return image;
		  
	  }
	  
	  /*
	   * Begin Setters
	   */
	  public void setId(Integer id) {
		  
		  this.id = id;
		  
	  }
	  
	  public void setNameFirst (char[] name) {
		  
		  name_first = name;
		  
	  }
	  
	  public void setNameMiddle (char[] name) {
		  
		  name_middle = name;
		  
	  }
	  
	  public void setNameLast (char[] name) {
		  
		  name_last = name;
		  
	  }
	  
	  public void setGender (Gender gender) {
		  
		  this.gender = gender;
		  
	  }
	  
	  public void setDobDate (Date date) {
		  
		  dob_date = date;
		  
	  }
	  
	  public void setDobTime (Time time) {
		  
		  dob_time = time;
		  
	  }
	  
	  public void setPhone (char[] number) {
		  
		  phone = number;
		  
	  }
	  
	  public void setAddressLine1 (char[] address) {
		  
		  addressLine1 = address;
		  
	  }
	  
	  public void setAddressLine2 (char[] address) {
		  
		  addressLine2 = address;
		  
	  }
	  
	  public void setCity (String name) {
		  city = name;
	  }
	  
	  public void setState (String name) {
		  
		  state = name;
		  
	  }
	  
	  public void setPostalCode (Integer number) {
		  
		  postalCode = number;
		  
	  }
	  
	  public void setCountry (String name) {
		  
		  country = name;
		  
	  }
	  
	  public void setImageUrl (String url) {
		  
		  imageUrl = url;
		  
	  }
	  
	  public void setImage (byte[] arr) throws IOException {
		  
		  image = arr;
	      
	  }
	  

	  @Override
	  public Patients clone() {
		  
		  return new Patients(id, name_first, name_middle, name_last, gender, dob_date, dob_time, phone, addressLine1, addressLine2, city, state, postalCode, country, image);
		  
	  }
	  
	  
	  /*
	   * Nullifies sensitive information
	   * 
	   * Things like city, state, etc. are considered non-sensitive when not paired with 
	   * unique personal identifiers (similar to how we can make primary keys with multiple fields).
	   */
	  public void nullify () {
		  
		  Arrays.fill(name_first, ' ');
		  
		  if (name_middle != null) {
			  
			  Arrays.fill(name_middle, ' ');
			  
		  }
		  Arrays.fill(name_last, ' ');
		  
		  if (phone != null) {
			  Arrays.fill(phone, ' ');
		  }
		  
		  if (addressLine1 != null) {
			  Arrays.fill(addressLine1, ' ');
		  }
		  
		  if (addressLine2 != null) {
			  Arrays.fill(addressLine2, ' ');
		  }
		  
	  }

}
