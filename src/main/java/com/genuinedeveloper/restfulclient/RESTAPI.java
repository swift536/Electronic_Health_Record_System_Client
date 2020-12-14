package com.genuinedeveloper.restfulclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.genuinedeveloper.db_entities.Allergies;
import com.genuinedeveloper.db_entities.Conditions;
import com.genuinedeveloper.db_entities.Entity;
import com.genuinedeveloper.db_entities.Medications;
import com.genuinedeveloper.db_entities.Patients;
import com.genuinedeveloper.db_entities.Records;
import com.genuinedeveloper.db_entities.Surgeries;
import com.genuinedeveloper.db_entities.Users;
import com.genuinedeveloper.utilities.UserCredentials;

import javafx.application.Application;

/*
 * Primary component for RESTful requests to server. The login request is purely to initialize the client
 * application and does not rely on session information server side (each request contains Authentication
 * information.
 */
public class RESTAPI {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private UserCredentials user;
	
	private static final Logger log = LoggerFactory.getLogger(OpenEHRS.class);
	
	
	
	public static void main(String[] args) {
		
		Application.launch(OpenEHRS.class, args);
		
	}

	/*********************************************Header Configuration**************************************************/
	@Bean
	HttpHeaders createHeaders(Integer id, String username, String password) {
		
		   return new HttpHeaders() {{
			   
		         String auth = id + ":" + username + ":" + password;
		         
		         set( "AUTHORIZATION", auth );
		         
		      }};
		      
	}
	
	//Database Post HttpEntity
	private HttpEntity getHttpEntity (Entity object) {
		 
		HttpEntity entity = new HttpEntity(object, createHeaders(user.getId(), user.getUsername(), user.getPassword()));
			
		return entity;
		
	}
	
	//Security Question HttpEntity
	private HttpEntity getHttpEntity (String[] object) {
		
		HttpEntity entity = new HttpEntity(object, createHeaders(user.getId(), user.getUsername(), user.getPassword()));
		
		return entity;
		
	}
	
	//Database GET HttpEntity
	private HttpEntity getHttpEntity () {
		 
		HttpEntity entity = new HttpEntity(createHeaders(user.getId(), user.getUsername(), user.getPassword()));
		
		return entity;
		
	}
	
	//Login HttpEntity
	private HttpEntity getHttpEntity (int id, String username, String password) {
		 
		HttpEntity entity = new HttpEntity(createHeaders(id, username, password));
		
		return entity;
		
	}
	
	/************************************************User Requests********************************************************/
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
	
	public String[] forgotPassword () {
		
		HttpEntity entity = getHttpEntity ();
		
		ResponseEntity<String[]> response = restTemplate
				.exchange("https://localhost:8443/request/users/forgot/{id}", 
						HttpMethod.GET,
						entity,
						String[].class,
						user.getId());
		
		return response.getBody();
		
	}
	
	public char[] postSecurityAnswers (String[] answers) {
		
		HttpEntity entity = getHttpEntity (answers);
		
		ResponseEntity<char[]> response = restTemplate
				.exchange("https://localhost:8443/request/users/forgot/{id}", 
						HttpMethod.POST,
						entity,
						char[].class,
						user.getId());
		
		return response.getBody();
		
	}
	
	public boolean postUser (Users user) {
		
		boolean response = false;
		
		HttpEntity entity = getHttpEntity (user);
		ResponseEntity<Boolean> responseEntity = restTemplate
				.exchange("https://localhost:8443/users/add", 
						HttpMethod.POST,
						entity,
						Boolean.class);
		
		if(responseEntity.getBody()) {
			
			response = true;
			
		}
		
		return response;
		
	}
	
	/************************************************GET Requests*******************************************************/
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
	public Conditions[] getConditions (int id) throws Exception {
		
		HttpEntity entity = getHttpEntity ();
		
		ResponseEntity<Conditions[]> response = restTemplate
				.exchange("https://localhost:8443/request/records/{id}", 
						HttpMethod.GET,
						entity,
						Conditions[].class,
						id);
		
		return response.getBody();
			
	};
	
	@SuppressWarnings("unchecked")
	public Surgeries[] getSurgeries (int id) throws Exception {
		
		HttpEntity entity = getHttpEntity ();
		
		ResponseEntity<Surgeries[]> response = restTemplate
				.exchange("https://localhost:8443/request/records/{id}", 
						HttpMethod.GET,
						entity,
						Surgeries[].class,
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
	
	/************************************************POST Requests*******************************************************/
	public boolean postAllergy (Allergies allergy) throws Exception {
		
		boolean response = false;
		
		HttpEntity entity = getHttpEntity (allergy);
		ResponseEntity<Boolean> responseEntity = restTemplate
				.exchange("https://localhost:8443/request/allergies", 
						HttpMethod.POST,
						entity,
						Boolean.class);
		
		if(responseEntity.getBody()) {
			
			response = true;
			
		}
		
		return response;
		
	};
	
	public boolean postPatient (Patients patient) throws Exception {
		
		boolean response = false;
		
		HttpEntity entity = getHttpEntity (patient);
		ResponseEntity<Boolean> responseEntity = restTemplate
				.exchange("https://localhost:8443/request/patients", 
						HttpMethod.POST,
						entity,
						Boolean.class);
		
		if(responseEntity.getBody()) {
			
			response = true;
			
		}
		
		return response;
		
	};
	
	public boolean postRecord (Records record) {
		
		boolean response = false;
		
		HttpEntity entity = getHttpEntity (record);
		ResponseEntity<Boolean> responseEntity = restTemplate
				.exchange("https://localhost:8443/request/records", 
						HttpMethod.POST,
						entity,
						Boolean.class);
		
		if(responseEntity.getBody()) {
			
			response = true;
			
		}
		
		return response;
		
	}
	
	public boolean postMedication (Medications medication) {
		
		boolean response = false;
		
		HttpEntity entity = getHttpEntity (medication);
		ResponseEntity<Boolean> responseEntity = restTemplate
				.exchange("https://localhost:8443/request/medications", 
						HttpMethod.POST,
						entity,
						Boolean.class);
		
		if(responseEntity.getBody()) {
			
			response = true;
			
		}
		
		return response;
		
	}
	
	public boolean postCondition (Conditions condition) {
		
		boolean response = false;
		
		HttpEntity entity = getHttpEntity (condition);
		ResponseEntity<Boolean> responseEntity = restTemplate
				.exchange("https://localhost:8443/request/conditions", 
						HttpMethod.POST,
						entity,
						Boolean.class,
						condition.getId());
		
		if(responseEntity.getBody()) {
			
			response = true;
			
		}
		
		return response;
		
	}
	
	public boolean postSurgery (Surgeries surgery) {
		
		boolean response = false;
		
		HttpEntity entity = getHttpEntity (surgery);
		ResponseEntity<Boolean> responseEntity = restTemplate
				.exchange("https://localhost:8443/request/surguries", 
						HttpMethod.POST,
						entity,
						Boolean.class);
		
		if(responseEntity.getBody()) {
			
			response = true;
			
		}
		
		return response;
		
	}
	
}