package com.genuinedeveloper.db_entities;

public class Allergies extends Entity{

	private Integer allergies_id;
	
	private Integer patients_id;
	
	private String name;
	
	private ThreatLevel threat_level;
	
	private String description;
	
	public enum ThreatLevel {
		low,
		medium,
		high
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

	public ThreatLevel getThreatLevel() {
		return threat_level;
	}

	public void setThreatLevel(ThreatLevel threat_level) {
		this.threat_level = threat_level;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString() {
		return patients_id.toString() + " " + name + " " + threat_level.toString() + " " + description;
	}
	
}
