package com.genuinedeveloper.db_entities;

public class Surgeries extends Entity{

	private Integer surgeries_id;
	
	private Integer patients_id;
	
	private String name;
	
	private String notes;

	public Integer getSurgeries_id() {
		return surgeries_id;
	}

	public void setSurgeries_id(Integer surgeries_id) {
		this.surgeries_id = surgeries_id;
	}

	public Integer getId() {
		return patients_id;
	}

	public void setId(Integer id) {
		this.patients_id = id;
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
