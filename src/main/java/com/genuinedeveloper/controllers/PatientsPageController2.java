package com.genuinedeveloper.controllers;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.genuinedeveloper.controllers.sub_controllers.PatientAccordionController;
import com.genuinedeveloper.db_entities.Patients;
import com.genuinedeveloper.restfulclient.RESTAPI;

import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;

@Component
public class PatientsPageController2 {
	@FXML
	private ScrollPane insert_scrollpane;
	@FXML
	private Accordion accordion;

    @Autowired
    public RESTAPI restApiObject;
    
    private final HostServices hostServices;
    
    @Autowired
    private ApplicationContext context;

    public PatientsPageController2(HostServices hostServices) {
        this.hostServices = hostServices;
    }

	
	@FXML
	public void initialize () {
		
		ClassLoader cl = getClass().getClassLoader();
		
		insert_scrollpane.setFitToWidth(true);
		
		try {
			
			Patients[] patients = restApiObject.getPatients();
			
			URL url = cl.getResource("Scenes/sub_scenes/patient_accordion.fxml");
			
			FXMLLoader loader = new FXMLLoader(url);
			
            loader.setControllerFactory(context::getBean);
            
            for (int i=0; i<patients.length; i++) {
            	
            	Parent root = (Parent)loader.load();
            	
            	PatientAccordionController controller = loader.<PatientAccordionController>getController();
            	controller.setPatient(patients[i]);
            	
            }
			
			
		} catch (Exception e) {
			
		}
		
	}
}
