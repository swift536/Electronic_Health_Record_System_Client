package com.genuinedeveloper.restfulclient;

import java.nio.charset.Charset;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Consumer;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

import com.genuinedeveloper.db_entities.Patients;
import com.genuinedeveloper.db_entities.Records;
import com.genuinedeveloper.db_entities.Allergies;
import com.genuinedeveloper.db_entities.Entity;
import com.genuinedeveloper.db_entities.Medications;
import com.genuinedeveloper.utilities.UserCredentials;

import javafx.application.Application;
import redis.clients.jedis.Jedis;

public class RESTAPI {

	@Autowired
	//@Qualifier ("TLS Template")
	private RestTemplate restTemplate;
	
	@Autowired
	private UserCredentials user;
	
	private static final Logger log = LoggerFactory.getLogger(OpenEHRS.class);
	
	public static void main(String[] args) {
		
		Application.launch(OpenEHRS.class, args);
		
	}

	@Bean
	HttpHeaders createHeaders(Integer id, String username, String password) {
		
		   return new HttpHeaders() {{
			   
		         String auth = id + ":" + username + ":" + password;
		         
		         set( "AUTHORIZATION", auth );
		         
		      }};
		      
	}
	
	private HttpEntity getHttpEntity (Entity object) {
		 
		HttpEntity entity = new HttpEntity(object, createHeaders(user.getId(), user.getUsername(), user.getPassword()));
			
		return entity;
		
	}
	
	private HttpEntity getHttpEntity () {
		 
		HttpEntity entity = new HttpEntity(createHeaders(user.getId(), user.getUsername(), user.getPassword()));
		
		return entity;
		
	}
	
	private HttpEntity getHttpEntity (int id, String username, String password) {
		 
		HttpEntity entity = new HttpEntity(createHeaders(id, username, password));
		
		return entity;
		
	}
	
	public boolean login (int id, String username, String password) {
		
		Boolean authenticated = false;
		
		HttpEntity entity = getHttpEntity (id, username, password);
		ResponseEntity<String> response = restTemplate
				.exchange("https://localhost:8443/request/login", 
						HttpMethod.POST,
						entity,
						String.class);
		String str = response.getBody();
		
		if (str != null && str.equals("true")) {
			authenticated = true;
			user.setId(id);
			user.setUsername(username);
			user.setPassword(password);
		}
		
		return authenticated;
		
	}
	
	@SuppressWarnings("unchecked")
	public Allergies[] getAllergies (int id) throws Exception {
		
		HttpEntity entity = getHttpEntity ();
		
		ResponseEntity<Allergies[]> response = restTemplate
				.exchange("https://localhost:8443/request/allergies/{id}", 
						HttpMethod.GET,
						entity,
						Allergies[].class,
						id);
		
		return response.getBody();
			
	};
	
	@SuppressWarnings("unchecked")
	public Medications[] getMedications (int id) throws Exception {
		
		HttpEntity entity = getHttpEntity ();
		
		ResponseEntity<Medications[]> response = restTemplate
				.exchange("https://localhost:8443/request/medications/{id}", 
						HttpMethod.GET,
						entity,
						Medications[].class,
						id);
		
		return response.getBody();
			
	};
	
	@SuppressWarnings("unchecked")
	public Records[] getRecords (int id) throws Exception {
		
		HttpEntity entity = getHttpEntity ();
		
		ResponseEntity<Records[]> response = restTemplate
				.exchange("https://localhost:8443/request/records/{id}", 
						HttpMethod.GET,
						entity,
						Records[].class,
						id);
		
		return response.getBody();
			
	};
	
	@SuppressWarnings("unchecked")
	public Patients[] getPatients() throws Exception {
		
		//ArrayList<Patients> patients = new ArrayList<Patients>();
		
		HttpEntity entity = getHttpEntity ();
		
		ResponseEntity<Patients[]> response = restTemplate
				.exchange("https://localhost:8443/request/patients", 
						HttpMethod.GET,
						entity,
						Patients[].class);
		
		return response.getBody();
			
	};
	
	public boolean postPatient (Patients patient) throws Exception {
		
		boolean response = false;
		
		HttpEntity entity = getHttpEntity (patient);
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("https://localhost:8443/request/patients", 
						HttpMethod.POST,
						entity,
						String.class);
		
		if(responseEntity.getBody() == "success") {
			
			response = true;
			
		}
		
		return response;
		
	};
	
	public boolean postRecord (Records record) {
		
		boolean response = false;
		
		HttpEntity entity = getHttpEntity (record);
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("https://localhost:8443/request/records", 
						HttpMethod.POST,
						entity,
						String.class,
						record.getId());
		
		if(((String) responseEntity.getBody()).compareTo("saved") == 0) {
			
			response = true;
			
		}
		
		return response;
		
	}
	
}