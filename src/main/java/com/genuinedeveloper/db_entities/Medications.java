package com.genuinedeveloper.db_entities;

import java.sql.Date;

public class Medications extends Entity{
	
	private Integer medications_id;
	
	private Integer patients_id;
	
	private String name;
	
	private String activeDrug;
	
	private Float amount;
	
	private UnitType amountUnit;
	
	private Integer frequencyAmount;
	
	private Frequency frequencyUnit;
	
	/*
	 * For patient changes to prescription or frequency, include a note
	 * in the description explaining the situation.
	 */
	private Date prescribedDate;
	
	private boolean activeStatus;
	
	private String description;
	
	public enum UnitType {
		g,
		mg,
		mcg,
		unit
	}
	
	public enum Frequency {
		minutes,
		hours,
		days,
		weeks,
		months,
		needed
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

	public String getActiveDrug() {
		return activeDrug;
	}

	public void setActiveDrug(String activeDrug) {
		this.activeDrug = activeDrug;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public UnitType getAmountUnit() {
		return amountUnit;
	}

	public void setAmountUnit(UnitType amountUnit) {
		this.amountUnit = amountUnit;
	}

	public Integer getFrequencyAmount() {
		return frequencyAmount;
	}

	public void setFrequencyAmount(Integer frequencyAmount) {
		this.frequencyAmount = frequencyAmount;
	}

	public Frequency getFrequencyUnit() {
		return frequencyUnit;
	}

	public void setFrequencyUnit(Frequency frequencyUnit) {
		this.frequencyUnit = frequencyUnit;
	}

	public Date getPrescribedDate() {
		return prescribedDate;
	}

	public void setPrescribedDate(Date prescribedDate) {
		this.prescribedDate = prescribedDate;
	}

	public boolean getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
