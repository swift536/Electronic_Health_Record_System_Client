package com.genuinedeveloper.controllers;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

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
import javafx.scene.layout.AnchorPane;

public class PatientsPageController {
	@FXML
	private ScrollPane insert_scrollpane;
	@FXML
	private Accordion accordion;

	
    @Autowired
    public RESTAPI restApiObject;
    
    private final HostServices hostServices;
    
    @Autowired
    private ApplicationContext context;
    
    ClassLoader cl;
    
    FXMLLoader loader;
    
    URL url;

    public PatientsPageController(HostServices hostServices) {
        this.hostServices = hostServices;
    }

	
	@FXML
	public void initialize () {
		

		try {
			
			insert_scrollpane.setFitToWidth(true);
			
			Patients[] patients = restApiObject.getPatients();
			
			ClassLoader cl = getClass().getClassLoader();
			
            URL url = cl.getResource("Scenes/sub_scenes/patient_accordion.fxml");
			
        	AnchorPane anchorPane = (AnchorPane) insert_scrollpane.getContent();
        	
        	Accordion accordion = (Accordion) anchorPane.getChildren().get(0);
            
        	/*
        	 * Based on research it is my understanding that a FXMLLoader cannot be used more than once because
        	 * changing the root would break the functionality, thus creating a new loader per instance
        	 * of the fxml needed is required. To save time in the future perhaps creating the nodes programmatically
        	 * would save processing time.
        	 */
            for (int i=0; i<patients.length; i++) {
            	
                FXMLLoader loader = new FXMLLoader(url);
                
                TitledPane pane = (TitledPane)loader.load();
                
                pane.setText(patients[i].getNameLast() + ", " + patients[i].getNameFirst() + " : " + patients[i].getDobDate().toString());
                
            	accordion.getPanes().add(pane);
            	
            }
			
			
		} catch (Exception e) {
			
		}
		
	}
}
