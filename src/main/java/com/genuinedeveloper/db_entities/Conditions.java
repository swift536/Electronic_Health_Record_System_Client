package com.genuinedeveloper.db_entities;

public class Conditions {

	private Integer conditions_id;
	
	private Integer id;
	
	private String name;
	
	private String notes;

	private Severity severity;
	
	public enum Severity {
		low, medium, high
	}

	public Integer getConditions_id() {
		return conditions_id;
	}

	public void setConditions_id(Integer conditions_id) {
		this.conditions_id = conditions_id;
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

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}
	
	
}
