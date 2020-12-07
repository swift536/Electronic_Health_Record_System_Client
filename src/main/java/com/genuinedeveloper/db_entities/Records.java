package com.genuinedeveloper.db_entities;

import java.sql.Date;
import java.sql.Time;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genuinedeveloper.db_entities.db_sub_entitities.SOAPNote;

public class Records extends Entity{
	
	private Integer record_id;
	
	private Integer id;
	
	private Date date_date;
	
	private Time date_time;
	
	private SOAPNote soap_note;

	public Integer getRecordId() {
		return record_id;
	}
	
	public void setRecordId(Integer recordId) {
		this.record_id = recordId;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date_date;
	}

	public void setDate(Date date_date) {
		this.date_date = date_date;
	}

	public Time getDateTime() {
		return date_time;
	}

	public void setDateTime(Time date_time) {
		this.date_time = date_time;
	}
	
	public void setSoapNote (SOAPNote soapNote) {
		soap_note = soapNote;
	}
	
	public SOAPNote getSoapNote () {
		return soap_note;
	}
	
}
