package com.genuinedeveloper.db_entities.db_sub_entitities;

public class Symptom {
	
	private String complaint;
	
	private Integer severity;
	
	public Symptom (String complaint, Integer severity) {
		this.complaint = complaint;
		this.severity = severity;
	}
	
	public Symptom (String complaint) {
		this.complaint = complaint;
	}
	
	public Symptom () {
		
	}
	
	public String getComplaint() {
		return complaint;
	}
	
	public void setComplaint(String complaint) {
		this.complaint = complaint;
	}
	
	public Integer getSeverity() {
		return severity;
	}
	
	public void setSeverity(Integer severity) {
		this.severity = severity;
	}
	
	public String toString () {
		
		return complaint + ": " + severity.toString();
		
	}
}
