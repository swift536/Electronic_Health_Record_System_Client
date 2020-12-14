package com.genuinedeveloper.restfulclient.controllers.sub_controllers;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.genuinedeveloper.db_entities.Medications;
import com.genuinedeveloper.db_entities.Patients;
import com.genuinedeveloper.restfulclient.RESTAPI;
import com.genuinedeveloper.restfulclient.controllers.VisitPageController;
import com.genuinedeveloper.restfulclient.controllers.VisitPageController.Page;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;

@Component
public class MedicationPageController {

	@Autowired
	VisitPageController vpController;
	
	@Autowired
	RESTAPI restApiObject;

	EventHandler<ActionEvent> pastMedicationsButtonEvent;

	EventHandler<ActionEvent> currentMedicationsButtonEvent;
	
	Patients currentPatient;
	
	Medications[] patientMedications;
	
	int currentPatientIndex;

	public void initialize() {

		currentPatientIndex = 0;
		
		// Adds navigation to complete list of medications (inactive)
		pastMedicationsButtonEvent = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (!vpController.isPatientSelected()) {

					return;

				}

				try {

					currentPatient = vpController.getCurrentPatient();
					
					patientMedications = restApiObject.getMedications(currentPatient.getId());

					ClassLoader cl = getClass().getClassLoader();

					URL url = cl.getResource("Scenes/sub_scenes/medications_pane.fxml");

					Accordion accordion = new Accordion();

					for (Medications medication : patientMedications) {

						if (!medication.getActiveStatus()) {

							FXMLLoader loader = new FXMLLoader(url);

							TitledPane pane = (TitledPane) loader.load();

							pane.setText(patientMedications[currentPatientIndex].getActiveDrug().toString());

							GridPane gridPane = (GridPane) pane.getContent();

							((TextField) gridPane.getChildren().get(0))
									.setText(patientMedications[currentPatientIndex].getName());

							((TextField) gridPane.getChildren().get(1))
									.setText(patientMedications[currentPatientIndex].getActiveDrug());

							((TextField) gridPane.getChildren().get(2))
									.setText(patientMedications[currentPatientIndex].getAmount().toString());

							((TextField) gridPane.getChildren().get(3))
									.setText(patientMedications[currentPatientIndex].getAmountUnit().toString());

							((TextField) gridPane.getChildren().get(4)).setText(
									"Every " + patientMedications[currentPatientIndex].getFrequencyAmount().toString());

							((TextField) gridPane.getChildren().get(5))
									.setText(patientMedications[currentPatientIndex].getFrequencyUnit().toString());

							((TextField) gridPane.getChildren().get(6))
									.setText(patientMedications[currentPatientIndex].getPrescribedDate().toString());

							accordion.getPanes().add(pane);

						}

					}

					vpController.setCenterContent(accordion);

					vpController.setTitle("Inactive Medications:");

					vpController.setCurrentPage(Page.PatientMedications);

				} catch (Exception e) {

					// TODO Auto-generated catch block
					e.printStackTrace();

				}

			}

		};

		currentMedicationsButtonEvent = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (!vpController.isPatientSelected()) {

					return;

				}

				try {
					
					currentPatient = vpController.getCurrentPatient();

					patientMedications = restApiObject.getMedications(currentPatient.getId());

					ClassLoader cl = getClass().getClassLoader();

					URL url = cl.getResource("Scenes/sub_scenes/medications_pane.fxml");

					Accordion accordion = new Accordion();

					for (Medications medication : patientMedications) {

						if (medication.getActiveStatus()) {

							FXMLLoader loader = new FXMLLoader(url);

							TitledPane pane = (TitledPane) loader.load();

							pane.setText(patientMedications[currentPatientIndex].getActiveDrug().toString());

							GridPane gridPane = (GridPane) pane.getContent();

							((TextField) gridPane.getChildren().get(0))
									.setText(patientMedications[currentPatientIndex].getName());

							((TextField) gridPane.getChildren().get(1))
									.setText(patientMedications[currentPatientIndex].getActiveDrug());

							((TextField) gridPane.getChildren().get(2))
									.setText(patientMedications[currentPatientIndex].getAmount().toString());

							((TextField) gridPane.getChildren().get(3))
									.setText(patientMedications[currentPatientIndex].getAmountUnit().toString());

							((TextField) gridPane.getChildren().get(4)).setText(
									"Every " + patientMedications[currentPatientIndex].getFrequencyAmount().toString());

							((TextField) gridPane.getChildren().get(5))
									.setText(patientMedications[currentPatientIndex].getFrequencyUnit().toString());

							((TextField) gridPane.getChildren().get(6))
									.setText(patientMedications[currentPatientIndex].getPrescribedDate().toString());

							accordion.getPanes().add(pane);

						}

					}

					vpController.setCenterContent(accordion);

					vpController.setTitle("Current Medications:");

					vpController.setCurrentPage(Page.PatientMedications);

				} catch (Exception e) {

					// TODO Auto-generated catch block
					e.printStackTrace();

				}

			}

		};

	}
	
	public EventHandler<ActionEvent> getPastMedicationEvent() {
		
		return pastMedicationsButtonEvent;
		
	}
	
	public EventHandler<ActionEvent> getCurrentMedicationEvent() {
		
		return currentMedicationsButtonEvent;
		
	}

}
