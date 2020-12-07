package com.genuinedeveloper.db_entities.db_sub_entitities;

import java.util.Vector;

public class Plan extends Note{

	private String[] tests;

	private String[] procedures;
	
	private String[] consultations;
	
	private String patientEducation;

	public String[] getTests() {
		return tests;
	}

	public void setTests(String[] tests) {
		this.tests = tests;
	}

	public String[] getProcedures() {
		return procedures;
	}

	public void setProcedures(String[] procedures) {
		this.procedures = procedures;
	}

	public String[] getConsultations() {
		return consultations;
	}

	public void setConsultations(String[] consultations) {
		this.consultations = consultations;
	}

	public String getPatientEducation() {
		return patientEducation;
	}

	public void setPatientEducation(String patientEducation) {
		this.patientEducation = patientEducation;
	}
	
}
