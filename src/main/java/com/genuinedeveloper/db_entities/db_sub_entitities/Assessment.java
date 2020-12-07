package com.genuinedeveloper.db_entities.db_sub_entitities;

import java.util.Vector;

public class Assessment extends Note{
	
	private String[] diagnosis;
	
	private String notes;

	public String[] getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String[] diagnosis) {
		this.diagnosis = diagnosis;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes (String notes) {
		this.notes = notes;
	}
	
	
	
}
