package com.genuinedeveloper.restfulclient.controllers.sub_controllers;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.genuinedeveloper.db_entities.Patients;
import com.genuinedeveloper.db_entities.Records;
import com.genuinedeveloper.db_entities.db_sub_entitities.Assessment;
import com.genuinedeveloper.db_entities.db_sub_entitities.Objective;
import com.genuinedeveloper.db_entities.db_sub_entitities.Plan;
import com.genuinedeveloper.db_entities.db_sub_entitities.SOAPNote;
import com.genuinedeveloper.db_entities.db_sub_entitities.Subjective;
import com.genuinedeveloper.db_entities.db_sub_entitities.Symptom;
import com.genuinedeveloper.restfulclient.RESTAPI;
import com.genuinedeveloper.restfulclient.controllers.VisitPageController;
import com.genuinedeveloper.restfulclient.controllers.VisitPageController.Page;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

@Component
public class RecordsPageController {

	Logger logger = LoggerFactory.getLogger(RecordsPageController.class);
	
	@Autowired
	VisitPageController vpController;
	
	@Autowired
	RESTAPI restApiObject;
	
	TitledPane[] notesTitledPanes;
	
	EventHandler<ActionEvent> pastRecordsButtonEvent;
	
	EventHandler<ActionEvent> postRecordButtonEvent;
	
	Patients currentPatient;
	
	TextField recordTimeTextField;
	
	Records[] patientRecords;
	
	private int recordsIndex;
	
	private Records currentRecord;

	//Subjective Components
	HBox subjectiveHbox;
	VBox subjectiveVbox;
	TextArea subjectiveTextArea;
	
	//Objective Components
	private Spinner<Integer> heightFt;
	private Spinner<Integer> heightIn;
	private Spinner<Double> weight;
	private Spinner<Double> oxygen;
	private Spinner<Double> temp;

	
	
	
	public EventHandler<ActionEvent> getPostRecordEvent() {
	
		return postRecordButtonEvent;
		
	}
	
	public EventHandler<ActionEvent> getPastRecordsEvent() {
		
		return pastRecordsButtonEvent;
		
	}
	
	public void intialize () {
		
		logger.info("RPController inti");
		
		recordsIndex = 0;
		
		notesTitledPanes = new TitledPane[4];
		
		postRecordButtonEvent = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				postRecord();

			}
			
		};
		
		pastRecordsButtonEvent = new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {

				if (!vpController.isPatientSelected()) {

					return;

				}

				try {
					
					//notesTitledPanes = vpController.getNotesNodes();

					currentPatient = vpController.getCurrentPatient();
					
					patientRecords = restApiObject.getRecords(currentPatient.getId());

					ClassLoader cl = getClass().getClassLoader();

					URL url = cl.getResource("Scenes/sub_scenes/records.fxml");

					if (patientRecords != null) {

						FXMLLoader loader = new FXMLLoader(url);

						VBox vbox = (VBox) loader.load();

						recordTimeTextField = (TextField) ((HBox) vbox.getChildren().get(0)).getChildren().get(1);

						// vbox.setPrefWidth(1050);

						vpController.setTitle("Past Records:");

						for (int i = 0; i < 4; i++) {

							String sourceStr = "";

							ClassLoader cl2 = getClass().getClassLoader();

							switch (i) {

							case 0:
								sourceStr = "Scenes/sub_scenes/subjective_pane.fxml";
								break;
							case 1:
								sourceStr = "Scenes/sub_scenes/objective_pane.fxml";
								break;
							case 2:
								sourceStr = "Scenes/sub_scenes/assessment_pane.fxml";
								break;
							case 3:
								sourceStr = "Scenes/sub_scenes/plan_pane.fxml";
								break;

							}

							// Temporary loaders
							URL url2 = cl.getResource(sourceStr);

							FXMLLoader loader2 = new FXMLLoader(url2);

							TitledPane tp = (TitledPane) loader2.load();
							
							switch (i) {
							case 0:
								notesTitledPanes[0] = tp;
								break;
							case 1:
								notesTitledPanes[1] = tp;
								break;
							case 2:
								notesTitledPanes[2] = tp;
								break;
							case 3:
								notesTitledPanes[3] = tp;
								break;

							}

							Accordion accordion = (Accordion) vbox.getChildren().get(1);

							Button backwardsRecordButton = (Button) ((HBox) vbox.getChildren().get(0)).getChildren()
									.get(0);

							backwardsRecordButton.setOnAction(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent event) {

									if (recordsIndex != 0) {

										recordsIndex--;

										loadRecord(false);

									} else {

										// do nothin or give warning
										Alert alert = new Alert(AlertType.INFORMATION);
										alert.setTitle("Oldest record available");
										alert.setHeaderText(null);
										alert.setContentText("There are no older records available at this time");

										alert.showAndWait();

									}

								}

							});

							Button forwardsRecordButton = (Button) ((HBox) vbox.getChildren().get(0)).getChildren()
									.get(2);

							forwardsRecordButton.setOnAction(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent e) {

									if (recordsIndex < patientRecords.length - 1) {

										recordsIndex++;

										loadRecord(false);

									} else {

										// do nothin or give warning
										Alert alert = new Alert(AlertType.INFORMATION);
										alert.setTitle("Most recent record available");
										alert.setHeaderText(null);
										alert.setContentText("There are no newer records available at this time");

										alert.showAndWait();

									}

								}

							});

							accordion.getPanes().add(tp);

							vpController.setCurrentPage(Page.PatientRecords);

						}

						vpController.setCenterContent(vbox);

						vpController.setTitle("Records:");

						loadRecord(false);

					}

				} catch (Exception e) {

					// TODO Auto-generated catch block
					e.printStackTrace();

				}

			}

		};
		
	}
	
	private void loadRecord(boolean editable) {

		String tempString = "";

		recordsIndex = 0;

		if (patientRecords.length > recordsIndex) {

			/*
			 * Load record time
			 */
			recordTimeTextField.setText(patientRecords[recordsIndex].getDate().toString() + " "
					+ patientRecords[recordsIndex].getDateTime().toString());

			/*
			 * Load subjective
			 */
			subjectiveHbox = (HBox) ((AnchorPane) notesTitledPanes[0].getContent()).getChildren().get(0);

			// Add symptoms here as TextFields
			subjectiveVbox = (VBox) ((AnchorPane) ((ScrollPane) subjectiveHbox.getChildren().get(0)).getContent())
					.getChildren().get(0);

			Symptom[] symptoms = patientRecords[recordsIndex].getSoapNote().getSubjective().getSymptoms();

			for (Symptom symptom : symptoms) {

				TextField textField = new TextField(symptom.toString());

				if (!editable) {

					textField.setEditable(false);

				}

				subjectiveVbox.getChildren().add(textField);

			}

			// Add the note here
			subjectiveTextArea = (TextArea) subjectiveHbox.getChildren().get(1);

			if (!editable) {

				subjectiveTextArea.setEditable(false);

			}

			subjectiveTextArea.setText(patientRecords[recordsIndex].getSoapNote().getSubjective().getNotes());

			/*
			 * Load Objective
			 */
			HBox objectiveHbox = (HBox) ((AnchorPane) notesTitledPanes[1].getContent()).getChildren().get(0);

			// Set objective characteristics (height, weight, temp, etc...)
			GridPane objectiveGridPane = (GridPane) objectiveHbox.getChildren().get(0);

			// Height feet
			Spinner spin1 = ((Spinner) objectiveGridPane.getChildren().get(5));
			spin1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15));
			spin1.getValueFactory().setValue(patientRecords[recordsIndex].getSoapNote().getObjective().getHeightFeet());

			// Height inches
			Spinner spin2 = ((Spinner) objectiveGridPane.getChildren().get(6));
			spin2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 11));
			spin2.getValueFactory()
					.setValue(patientRecords[recordsIndex].getSoapNote().getObjective().getHeightInches());

			// Weight pounds
			Spinner spin3 = ((Spinner) objectiveGridPane.getChildren().get(7));
			spin3.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 700.0));
			spin3.getValueFactory().setValue(patientRecords[recordsIndex].getSoapNote().getObjective().getWeight());

			// Oxygen
			Spinner spin4 = ((Spinner) objectiveGridPane.getChildren().get(8));
			spin4.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(60.0, 100.0));
			spin4.getValueFactory().setValue(patientRecords[recordsIndex].getSoapNote().getObjective().getOxygen());

			// Temperature
			Spinner spin5 = ((Spinner) objectiveGridPane.getChildren().get(9));
			spin5.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(92.0, 110.0));
			spin5.getValueFactory().setValue(patientRecords[recordsIndex].getSoapNote().getObjective().getTemp());

			if (editable) {

				spin1.setEditable(false);
				spin2.setEditable(false);
				spin3.setEditable(false);
				spin4.setEditable(false);
				spin5.setEditable(false);

			}

			// Set Observables
			VBox objectiveVBox = (VBox) ((ScrollPane) objectiveHbox.getChildren().get(1)).getContent();

			String[] observables = patientRecords[recordsIndex].getSoapNote().getObjective().getObservables();

			for (String observable : observables) {

				TextField textField = new TextField(observable);

				if (!editable) {

					textField.setEditable(false);

				}

				objectiveVBox.getChildren().add(textField);

			}

			// Set notes
			TextArea tf = (TextArea) objectiveHbox.getChildren().get(2);

			tf.setText(patientRecords[recordsIndex].getSoapNote().getObjective().getNotes());

			/*
			 * Load Assessment
			 */
			HBox assessmentHbox = (HBox) ((AnchorPane) notesTitledPanes[2].getContent()).getChildren().get(0);

			// Set diagnosis
			VBox assessmentVbox = (VBox) ((ScrollPane) assessmentHbox.getChildren().get(0)).getContent();

			String[] diagnosis = patientRecords[recordsIndex].getSoapNote().getAssessment().getDiagnosis();

			for (String diag : diagnosis) {

				TextField textField = new TextField(diag);

				if (!editable) {

					textField.setEditable(false);

				}

				assessmentVbox.getChildren().add(textField);

			}

			// Set notes
			TextArea assessmentTextArea = (TextArea) assessmentHbox.getChildren().get(1);

			if (!editable) {

				assessmentTextArea.setEditable(false);

			}

			assessmentTextArea.setText(patientRecords[recordsIndex].getSoapNote().getAssessment().getNotes());

			/*
			 * Load Plan
			 */
			HBox planHbox = (HBox) ((AnchorPane) notesTitledPanes[3].getContent()).getChildren().get(0);

			// Set tests
			VBox testsVbox = (VBox) planHbox.getChildren().get(0);

			String[] tests = patientRecords[recordsIndex].getSoapNote().getPlan().getTests();

			for (String test : tests) {

				TextField textField = new TextField(test);

				if (!editable) {

					textField.setEditable(false);

				}

				((VBox) testsVbox.getChildren().get(1)).getChildren().add(textField);

			}

			// Set procedures
			VBox proceduresVbox = (VBox) planHbox.getChildren().get(1);

			String[] procedures = patientRecords[recordsIndex].getSoapNote().getPlan().getProcedures();

			for (String procedure : procedures) {

				TextField textField = new TextField(procedure);

				if (!editable) {

					textField.setEditable(false);

				}

				((VBox) proceduresVbox.getChildren().get(1)).getChildren().add(textField);

			}

			// Set consultations
			VBox consultationsVbox = (VBox) planHbox.getChildren().get(2);

			String[] consultations = patientRecords[recordsIndex].getSoapNote().getPlan().getConsultations();

			for (String consultation : consultations) {

				TextField textField = new TextField(consultation);

				if (!editable) {

					textField.setEditable(false);

				}

				((VBox) consultationsVbox.getChildren().get(1)).getChildren().add(textField);

			}

			// Set education
			VBox educationVbox = (VBox) planHbox.getChildren().get(3);

			String education = patientRecords[recordsIndex].getSoapNote().getPlan().getPatientEducation();

			TextArea plantTextArea = (TextArea) educationVbox.getChildren().get(1);

			if (!editable) {

				plantTextArea.setEditable(false);

			}

			plantTextArea.setText(education);

		}

	}
	
	private boolean postRecord() {0
		
		notesTitledPanes = vpController.getNotesNodes();
		
		currentPatient = vpController.getCurrentPatient();

		boolean response = false;

		String tempStr;
		

		// Create and populate new record
		// Records record = new Records();

		//currentRecord = vpController.getCurrentRecord();
		
		currentRecord.setId(currentPatient.getId());

		//String[] dateTime = date_time_textfield.getText().split(" ");
		
		String[] dateTime = vpController.getRecordDateTime();

		String[] date = dateTime[0].split("/");

		String[] time = dateTime[1].split(":");

		// Date was stored in dd/mm/yyyy so must inverse
		currentRecord.setDate(
				new java.sql.Date(Integer.parseInt(date[2])-1900, Integer.parseInt(date[1])-1, Integer.parseInt(date[0])-1));

		// Time was stored as hh:mm:ss so must inverse as well
		currentRecord.setDateTime(
				new java.sql.Time(Integer.parseInt(time[2]), Integer.parseInt(time[1]), Integer.parseInt(time[0])));

		/*
		 * Subjective Components
		 */
		Subjective currentSubjective = new Subjective();

		if (subjectiveTextArea.getText() != null && subjectiveTextArea.getText() != "") {

			tempStr = subjectiveTextArea.getText();

			currentSubjective.setNotes(tempStr);

		}

		// Collect non-empty symptoms
		HBox subjectiveHbox = (HBox) ((AnchorPane) notesTitledPanes[0].getContent()).getChildren().get(0);

		// Symptoms vbox
		ScrollPane subjectiveScrollPane = (ScrollPane) subjectiveHbox.getChildren().get(0);

		VBox symptomsVbox = (VBox) ((AnchorPane) subjectiveScrollPane.getContent()).getChildren().get(0);
		
		Symptom[] symptoms = (Symptom[]) vpController.getTextFieldsToArray(currentSubjective, symptomsVbox.getChildren());

		currentSubjective.setSymptoms(symptoms);

		/*
		 * Objective Components
		 */
		HBox objectiveHbox = (HBox) ((AnchorPane) notesTitledPanes[1].getContent()).getChildren().get(0);

		// Observables
		ScrollPane objectiveScrollPane = (ScrollPane) objectiveHbox.getChildren().get(1);

		VBox observablesVbox = (VBox) objectiveScrollPane.getContent();
		
		Objective currentObjective = new Objective();
		
		TextArea objectiveTextArea = (TextArea) objectiveHbox.getChildren().get(2);

		if (objectiveTextArea.getText() != null && objectiveTextArea.getText() != "") {

			tempStr = objectiveTextArea.getText();

			currentObjective.setNotes(tempStr);

		}

		if (heightFt.getValue() != null) {

			// IntegerValueFactory is broken and returns doubles only, so swapped to double
			// factory

			currentObjective.setHeightFeet(heightFt.getValue());

		} else if (heightIn.getValue() != null) {

			currentObjective.setHeightInches(heightFt.getValue());

		} else if (weight.getValue() != null) {

			currentObjective.setWeight((Double) weight.getValue());

		} else if (oxygen.getValue() != null) {

			currentObjective.setOxygen((Double) oxygen.getValue());

		} else if (temp.getValue() != null) {

			currentObjective.setTemp((Double) temp.getValue());

		}

		// Collect non-empty observables
		String[] observables = (String[]) vpController.getTextFieldsToArray(currentObjective, observablesVbox.getChildren());

		currentObjective.setObservables(observables);

		/*
		 * Assessment Components
		 */
		HBox assessmentHbox = (HBox) ((AnchorPane) notesTitledPanes[1].getContent()).getChildren().get(0);

		ScrollPane assessmentScrollPane = (ScrollPane) assessmentHbox.getChildren().get(0);

		VBox diagnosisVbox = (VBox) assessmentScrollPane.getContent();
		
		Assessment currentAssessment = new Assessment();
		
		TextArea assessmentTextArea = (TextArea) assessmentHbox.getChildren().get(1);

		if (assessmentTextArea.getText() != null && assessmentTextArea.getText() != "") {

			tempStr = assessmentTextArea.getText();

			currentAssessment.setNotes(tempStr);

		}

		// Collect non-empty diagnosis
		String[] diagnosis = (String[]) vpController.getTextFieldsToArray(currentAssessment, diagnosisVbox.getChildren());

		currentAssessment.setDiagnosis(diagnosis);

		/*
		 * Plan Components
		 */
		HBox planHbox = (HBox) ((AnchorPane) notesTitledPanes[1].getContent()).getChildren().get(0);

		// Tests
		ScrollPane testScrollPane = (ScrollPane) planHbox.getChildren().get(0);

		VBox testVbox = (VBox) testScrollPane.getContent();
		
		VBox testsVbox = (VBox) ((ScrollPane) planHbox.getChildren().get(0)).getContent();
		
		VBox proceduresVbox = (VBox) ((ScrollPane) planHbox.getChildren().get(1)).getContent();
		
		VBox consultationsVbox = (VBox) ((ScrollPane) planHbox.getChildren().get(2)).getContent();

		Button addTestButton = (Button) testVbox.getChildren().get(1);
		
		TextArea planTextArea = (TextArea) ((VBox) planHbox.getChildren().get(3)).getChildren().get(1);
		
		Plan currentPlan = new Plan();

		if (planTextArea.getText() != null && planTextArea.getText() != "") {

			tempStr = planTextArea.getText();

			currentPlan.setPatientEducation(tempStr);

		}

		// Collect non-empty tests
		String[] tests = (String[]) vpController.getTextFieldsToArray(currentPlan, testsVbox.getChildren());

		currentPlan.setTests(tests);

		// Collect non-empty procedures
		String[] procedures = (String[]) vpController.getTextFieldsToArray(currentPlan, proceduresVbox.getChildren());

		currentPlan.setProcedures(procedures);

		// Collect non-empty consultations
		String[] consultations = (String[]) vpController.getTextFieldsToArray(currentPlan, consultationsVbox.getChildren());

		currentPlan.setConsultations(consultations);

		/*
		 * Creating SOAP note
		 */
		SOAPNote currentSoapNote = new SOAPNote();

		currentSoapNote.setSubjective(currentSubjective);

		currentSoapNote.setObjective(currentObjective);

		currentSoapNote.setAssessment(currentAssessment);

		currentSoapNote.setPlan(currentPlan);

		currentRecord.setSoapNote(currentSoapNote);

		response = restApiObject.postRecord(currentRecord);

		// If failed, alert user
		if (!response) {

			Alert alert = new Alert(AlertType.INFORMATION);

			alert.setTitle("Failed");

			alert.setHeaderText(null);

			alert.setContentText("Server failed to recieve record");

			alert.showAndWait();

		} else {

			Alert alert = new Alert(AlertType.INFORMATION);

			alert.setTitle("Success");

			alert.setHeaderText(null);

			alert.setContentText("Record has been saved");

			alert.showAndWait();

		}

		return response;

	}
	
}
