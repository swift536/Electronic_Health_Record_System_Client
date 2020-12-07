package com.genuinedeveloper.db_entities.db_sub_entitities;

import java.util.Vector;

public class Objective extends Note{

	private Integer heightFeet;
	
	private Integer heightInches;
	
	private Double weight;//In pounds
	
	private Double oxygen;
	
	private Double temp;
	
	private String[] observables;
	
	private String notes;

	public Integer getHeightFeet() {
		return heightFeet;
	}

	public void setHeightFeet(Integer heightFeet) {
		this.heightFeet = heightFeet;
	}

	public Integer getHeightInches() {
		return heightInches;
	}

	public void setHeightInches(Integer heightInches) {
		this.heightInches = heightInches;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getOxygen() {
		return oxygen;
	}

	public void setOxygen(Double oxygen) {
		this.oxygen = oxygen;
	}

	public Double getTemp() {
		return temp;
	}

	public void setTemp(Double temp) {
		this.temp = temp;
	}

	public String[] getObservables() {
		return observables;
	}

	public void setObservables(String[] observables) {
		this.observables = observables;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes (String notes) {
		this.notes = notes;
	}

	
}
