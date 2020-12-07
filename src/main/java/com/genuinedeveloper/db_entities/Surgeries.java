package com.genuinedeveloper.db_entities;

public class Surgeries {

	private Integer surgeries_id;
	
	private Integer id;
	
	private String name;
	
	private String notes;

	public Integer getSurgeries_id() {
		return surgeries_id;
	}

	public void setSurgeries_id(Integer surgeries_id) {
		this.surgeries_id = surgeries_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
}
