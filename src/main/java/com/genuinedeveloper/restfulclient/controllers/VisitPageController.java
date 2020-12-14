package com.genuinedeveloper.restfulclient.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.genuinedeveloper.db_entities.Allergies;
import com.genuinedeveloper.db_entities.Medications;
import com.genuinedeveloper.db_entities.Patients;
import com.genuinedeveloper.db_entities.Records;
import com.genuinedeveloper.db_entities.db_sub_entitities.Note;
import com.genuinedeveloper.db_entities.db_sub_entitities.Subjective;
import com.genuinedeveloper.db_entities.db_sub_entitities.Symptom;
import com.genuinedeveloper.restfulclient.RESTAPI;
import com.genuinedeveloper.restfulclient.controllers.sub_controllers.MedicationPageController;
import com.genuinedeveloper.restfulclient.controllers.sub_controllers.RecordsPageController;
import com.genuinedeveloper.restfulclient.controllers.sub_controllers.UpdatePatientController;

import javafx.application.HostServices;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

@Component
public class VisitPageController {

	@FXML
	private Accordion center_accordion;
	@FXML
	private VBox center_vbox;
	@FXML
	private ScrollPane center_scrollpane;
	@FXML
	private BorderPane border_pane;
	@FXML
	private HBox border_pane_bottom_hbox;
	@FXML
	private Button alerts_button;
	@FXML
	private Button mail_button;
	@FXML
	private Button my_account_button;
	@FXML
	private Button report_problem_button;
	@FXML
	private Button post_record_button;
	@FXML
	private HBox border_pane_top_hbox;
	@FXML
	private MenuButton records_menu_button;
	@FXML
	private MenuItem current_visit_menu_buton;
	@FXML
	private MenuItem past_records_menu_button;
	@FXML
	private MenuButton medications_menu_button;
	@FXML
	private MenuItem current_medications_menu_button;
	@FXML
	private MenuItem past_medications_menu_button;
	@FXML
	private MenuButton conditions_menu_button;
	@FXML
	private MenuItem current_conditions_menu_button;
	@FXML
	private MenuItem past_conditions_menu_button;
	@FXML
	private Button allergies_menu_button;
	@FXML
	private Button surgeries_menu_button;
	@FXML
	private TextField date_time_textfield;
	@FXML
	private HBox border_pane_left_hbox;
	@FXML
	private VBox patient_info_vbox;
	@FXML
	private ImageView patient_photo_image;
	@FXML
	private TextField first_name_textfield;
	@FXML
	private TextField last_name_textfield;
	@FXML
	private TextField gender_textfield;
	@FXML
	private TextField age_textfield;
	@FXML
	private TextField dob_textfield;
	@FXML
	private TextField allergies_textfield;
	@FXML
	private TextField conditions_textfield;
	@FXML
	private Button update_patient_button;
	@FXML
	private Button patient_info_open_close_button;

	@Autowired
	public RESTAPI restApiObject;
	
	@Autowired
	RecordsPageController rpController;
	
	@Autowired
	MedicationPageController mpController;
	
	@Autowired
	UpdatePatientController upController;

	@Autowired
	private ApplicationContext context;
	
	private final HostServices hostServices;

	private Node centerReplacementNode;

	private Node centerHeader;

	private Node currentVisit;

	private Patients[] patients;

	private Patients currentPatient;

	private Records[] patientRecords;

	private Records currentRecord;

	boolean patientSelected;

	private TextField recordTimeTextField;

	// Used to determine which record to display in past records
	private int recordsIndex;

	// Subjective Components
	private TitledPane subjective;

	private VBox symptomsVbox;

	private TextArea subjectiveTextArea;

	// Objective Components
	private TitledPane objective;

	private Spinner<Integer> heightFt;

	private Spinner<Integer> heightIn;

	private Spinner<Double> weight;

	private Spinner<Double> oxygen;

	private Spinner<Double> temp;

	private VBox observablesVbox;

	private TextArea objectiveTextArea;

	// Assessment Components
	private TitledPane assessment;

	private VBox diagnosisVbox;

	private TextArea assessmentTextArea;

	// Plan Components
	private TitledPane plan;

	private VBox testsVbox;

	private VBox proceduresVbox;

	private VBox consultationsVbox;

	private TextArea planTextArea;

	private Medications[] patientMedications;

	private int currentPatientIndex;

	private Page currentPage;


	public enum Page {

		CurrentVisit, PatientRecords, PatientMedications, Patients, UpdatePatient

	}

	/*
	 * This is the primary controller for the system. The login controller is
	 * attached for one stage while this controller is attached to the functional
	 * stage. While only one controller can be attached to a hierarchy, future
	 * expansion of this project will include the development of sub-controller
	 * classes that would take the place of several of the methods within.
	 */

	public VisitPageController(HostServices hostServices) {

		this.hostServices = hostServices;

	}

	@FXML
	public void initialize() {
		
		//Debug
		System.out.println("Entered VPController initialize()");
		
		rpController.intialize();
		
		mpController.initialize();
		
		upController.intialize();
		
		currentPage = Page.Patients;

		recordsIndex = 0;

		loadPatientsPage();

		loadButtonActions();

		center_scrollpane.setFitToWidth(true);
		center_scrollpane.setHbarPolicy(ScrollBarPolicy.NEVER);

		// Set visit date and time
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		java.util.Date date = new java.util.Date();

		date_time_textfield.setText(formatter.format(date));

		post_record_button.setOnAction(rpController.getPostRecordEvent());

	}

	private void loadPatientsPage() {

		try {

			ClassLoader cl = getClass().getClassLoader();

			// URL url = cl.getResource("Scenes/patients_page.fxml");
			URL url = cl.getResource("Scenes/patients_page2.fxml");

			FXMLLoader loader = new FXMLLoader(url);

			loader.setControllerFactory(context::getBean);

			ScrollPane root = loader.load();

			root.setFitToWidth(true);
			root.setHbarPolicy(ScrollBarPolicy.NEVER);

			List<Node> list = center_vbox.getChildren();

			// Labeled TextField at the top for displaying current navigation
			centerHeader = list.get(0);

			setTitle("Choose a patient:");

			list.set(1, root);

			ScrollPane listOfPatientsScrollPane = (ScrollPane) list.get(1);
			listOfPatientsScrollPane.setPrefHeight(700);

			loadPatients(listOfPatientsScrollPane);

		} catch (IOException e) {

			System.out.println("Error in VisitPageController-loadPatients();");

		}

	}

	private void loadPatients(ScrollPane scrollPane) {

		try {

			scrollPane.setFitToWidth(true);

			patients = restApiObject.getPatients();

			ClassLoader cl = getClass().getClassLoader();

			URL url = cl.getResource("Scenes/sub_scenes/patient_accordion.fxml");

			Accordion accordion = (Accordion) scrollPane.getContent();

			/*
			 * Based on research it is my understanding that a FXMLLoader cannot be used
			 * more than once because changing the root would break the functionality, thus
			 * creating a new loader per instance of the fxml needed is required. To save
			 * time in the future perhaps creating the nodes programmatically would save
			 * processing time.
			 */
			for (int i = 0; i < patients.length; i++) {

				FXMLLoader loader = new FXMLLoader(url);

				TitledPane pane = (TitledPane) loader.load();

				// pane.setPrefWidth(700);

				String firstName = new String(patients[i].getNameFirst());
				String lastName = new String(patients[i].getNameLast());
				String dob = patients[i].getDobDate().toString();

				pane.setText(lastName + ", " + firstName + " : " + dob);

				AnchorPane ap = (AnchorPane) pane.getContent();

				GridPane gp = (GridPane) ap.getChildren().get(0);

				accordion.getPanes().add(pane);

				List<Node> gpList = gp.getChildren();

				// sets components of accordian files: patient image, first name, middle name,
				// last name, id, gender, dob day, dob time, button

				if (patients[i].getImage() != null) {

					((ImageView) gpList.get(0))
							.setImage(new Image(getPatientImageFileURL(patients[i], "patient_image" + i)));

				}

				((TextField) gpList.get(1)).setText(firstName);

				if (patients[i].getNameMiddle() != null) {

					((TextField) gpList.get(2)).setText(new String(patients[i].getNameMiddle()));

				}

				((TextField) gpList.get(3)).setText(lastName);

				((TextField) gpList.get(4)).setText(patients[i].getId().toString());

				if (patients[i].getGender() != null) {

					((TextField) gpList.get(5)).setText(patients[i].getGender().toString());

				}

				if (patients[i].getDobDate() != null) {

					((TextField) gpList.get(6)).setText(patients[i].getDobDate().toString());

				}

				if (patients[i].getDobTime() != null) {

					((TextField) gpList.get(7)).setText(patients[i].getDobTime().toString());

				}

				((Button) gpList.get(8)).setId("selectorButton:" + i);
				((Button) gpList.get(8)).setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {

						String[] buttonName = ((Button) event.getSource()).getId().split(":");

						currentPatientIndex = Integer.parseInt(buttonName[1]);

						patientSelected();

					}

				});

			}

			currentPage = Page.Patients;

		} catch (Exception e) {

		}

	}

	private String getPatientImageFileURL(Patients patient, String outfileName) {

		String response = "";

		try {

			byte[] imageByteArray = patient.getImage();

			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageByteArray);

			BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);

			ClassLoader cl = getClass().getClassLoader();

			URL url = cl.getResource("temp_data/");

			File outfile = new File("temp_data/" + outfileName + ".png");

			outfile.createNewFile();

			ImageIO.write(bufferedImage, "png", outfile);

			response = outfile.toURI().toURL().toString();

			patient.setImageUrl(response);

		} catch (IOException e) {

			e.printStackTrace();

		}

		return response;

	}

	/*
	 * Loads the selected patient information
	 */
	public void patientSelected() {

		try {

			// set current patient and related information
			currentPatient = patients[currentPatientIndex];

			String tempStr;

			List<Node> list = ((VBox) border_pane_left_hbox.getChildren().get(0)).getChildren();

			/*
			 * Load the selected patient's information into current patient
			 * 
			 * remove if broken
			 */
			if (((TextField) list.get(1)).getText() != null
					&& ((((TextField) list.get(1)).getText() != currentPatient.getNameFirst().toString())
							&& ((TextField) list.get(2)).getText() != currentPatient.getNameLast().toString())
					&& ((TextField) list.get(5)).getText() != currentPatient.getDobDate().toString()) {

				if (currentPatient.getImage() != null) {
					
					Image img = new Image(getPatientImageFileURL(currentPatient,
							currentPatient.getNameLast() + "_" + currentPatient.getNameFirst()));

						((ImageView) list.get(0)).setImage(img);
					
				}

				/*
				 * No input validation for first name, last name or DOB because those are
				 * required fields.
				 */

				// First name
				((TextField) list.get(1)).setText(new String(currentPatient.getNameFirst()));

				// Last name
				((TextField) list.get(2)).setText(new String(currentPatient.getNameLast()));

				// Gender
				Patients.Gender gender = currentPatient.getGender();

				if (gender != null) {

					((TextField) list.get(3)).setText(gender.toString());

				}

				// Age and DOB
				Date dobDate = currentPatient.getDobDate();

				if (dobDate != null) {

					Integer age = Calendar.getInstance().get(Calendar.YEAR)
							- (currentPatient.getDobDate().getYear() + 1900);
					((TextField) list.get(4)).setText(age.toString());

					((TextField) list.get(5)).setText(dobDate.toString());

				}

				Allergies[] allergies = restApiObject.getAllergies(currentPatient.getId());

				// Allergies
				if (allergies != null) {

					String str = "";

					for (Allergies allergy : allergies) {

						str += allergy.getName() + "\n";

					}

					((TextArea) list.get(7)).setText(str);

				}

				patientMedications = restApiObject.getMedications(currentPatient.getId());

				// Allergies
				if (patientMedications != null) {

					String str = "";

					for (Medications meds : patientMedications) {

						str += meds.getName() + "\n";

					}

					((TextArea) list.get(9)).setText(str);

				}

			}

			/*
			 * Load the border pane's new center page and set content
			 */
			ClassLoader cl = getClass().getClassLoader();

			URL url = cl.getResource("Scenes/current_visit.fxml");

			FXMLLoader loader = new FXMLLoader(url);

			Accordion accordion = (Accordion) loader.load();

			/*
			 * Set current visit handlers
			 * 
			 * Subjective
			 */
			subjective = accordion.getPanes().get(0);

			HBox subjectiveHbox = (HBox) ((AnchorPane) subjective.getContent()).getChildren().get(0);

			// Symptoms vbox
			ScrollPane subjectiveScrollPane = (ScrollPane) subjectiveHbox.getChildren().get(0);

			symptomsVbox = (VBox) ((AnchorPane) subjectiveScrollPane.getContent()).getChildren().get(0);

			Button addSymptomButton = (Button) symptomsVbox.getChildren().get(0);

			addSymptomButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					symptomsVbox.getChildren().add(new TextField());

				}

			});

			subjectiveTextArea = (TextArea) subjectiveHbox.getChildren().get(1);

			/*
			 * Objective
			 */
			objective = accordion.getPanes().get(1);

			HBox objectiveHbox = (HBox) ((AnchorPane) objective.getContent()).getChildren().get(0);

			// Observables
			ScrollPane objectiveScrollPane = (ScrollPane) objectiveHbox.getChildren().get(1);

			observablesVbox = (VBox) objectiveScrollPane.getContent();

			Button observableButton = (Button) observablesVbox.getChildren().get(0);

			observableButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					observablesVbox.getChildren().add(new TextField());

				}

			});

			/*
			 * Set spinners
			 */
			heightFt = (Spinner) ((GridPane) objectiveHbox.getChildren().get(0)).getChildren().get(5);

			heightFt.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 14));

			heightIn = (Spinner) ((GridPane) objectiveHbox.getChildren().get(0)).getChildren().get(6);

			heightIn.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 11));

			weight = (Spinner) ((GridPane) objectiveHbox.getChildren().get(0)).getChildren().get(7);

			weight.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 600.0));

			oxygen = (Spinner) ((GridPane) objectiveHbox.getChildren().get(0)).getChildren().get(8);

			oxygen.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(60.0, 100.0));

			temp = (Spinner) ((GridPane) objectiveHbox.getChildren().get(0)).getChildren().get(9);

			oxygen.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(92.0, 110.0));

			objectiveTextArea = (TextArea) objectiveHbox.getChildren().get(2);

			/*
			 * Assessment
			 */
			assessment = accordion.getPanes().get(2);

			HBox assessmentHbox = (HBox) ((AnchorPane) assessment.getContent()).getChildren().get(0);

			ScrollPane assessmentScrollPane = (ScrollPane) assessmentHbox.getChildren().get(0);

			diagnosisVbox = (VBox) assessmentScrollPane.getContent();

			Button diagnosisButton = (Button) diagnosisVbox.getChildren().get(0);

			diagnosisButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					diagnosisVbox.getChildren().add(new TextField());

				}

			});

			assessmentTextArea = (TextArea) assessmentHbox.getChildren().get(1);

			/*
			 * Plan
			 */
			plan = accordion.getPanes().get(3);

			HBox planHbox = (HBox) ((AnchorPane) plan.getContent()).getChildren().get(0);

			// Tests
			ScrollPane testScrollPane = (ScrollPane) planHbox.getChildren().get(0);

			VBox testVbox = (VBox) testScrollPane.getContent();

			Button addTestButton = (Button) testVbox.getChildren().get(1);

			addTestButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					testVbox.getChildren().add(new TextField());

				}

			});

			// Procedures
			ScrollPane procedureScrollPane = (ScrollPane) planHbox.getChildren().get(1);

			VBox procedureVbox = (VBox) procedureScrollPane.getContent();

			Button addProcedureButton = (Button) procedureVbox.getChildren().get(1);

			addProcedureButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					procedureVbox.getChildren().add(new TextField());

				}

			});

			// Consultations
			ScrollPane consultationsScrollPane = (ScrollPane) planHbox.getChildren().get(2);

			VBox consultationVbox = (VBox) consultationsScrollPane.getContent();

			Button addConsultationButton = (Button) consultationVbox.getChildren().get(1);

			addConsultationButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					consultationVbox.getChildren().add(new TextField());

				}

			});

			testsVbox = (VBox) ((ScrollPane) planHbox.getChildren().get(0)).getContent();

			proceduresVbox = (VBox) ((ScrollPane) planHbox.getChildren().get(1)).getContent();

			consultationsVbox = (VBox) ((ScrollPane) planHbox.getChildren().get(2)).getContent();

			planTextArea = (TextArea) ((VBox) planHbox.getChildren().get(3)).getChildren().get(1);

			// accordion.setPrefWidth(1100);
			accordion.setPrefHeight(700);

			// Attach event handlers to currentRecord buttons

			if (currentRecord == null) {

				currentRecord = new Records();

			}

			setCenterContent(accordion);

			currentPage = Page.CurrentVisit;

			setTitle("Todays Visit:");

			patientSelected = true;

		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			for (Patients patient : patients) {

				if (patient != currentPatient) {

					patient.nullify();

				}

			}

		}

	}

	public void loadButtonActions() {

		// Adds navigation back to current visit
		current_visit_menu_buton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (currentPage != Page.CurrentVisit) {

					patientSelected();

				}

			}

		});

		
		past_records_menu_button.setOnAction(rpController.getPastRecordsEvent());
		
		past_medications_menu_button.setOnAction(mpController.getPastMedicationEvent());
		
		current_medications_menu_button.setOnAction(mpController.getCurrentMedicationEvent());
		
		update_patient_button.setOnAction(upController.getUpdatePatientEvent());

	}

	public void setTitle(String title) {

		((TextField) centerHeader).setText(title);

	}

	public void setCenterContent(Node node) {

		//Debug
		System.out.println("SetCenterContent entered");
		
		ScrollPane sc = ((ScrollPane) center_vbox.getChildren().get(1));

		sc.setContent(node);

	}

	/*private boolean postRecord() {

		boolean response = false;

		String tempStr;

		// Create and populate new record
		// Records record = new Records();

		currentRecord.setId(currentPatient.getId());

		String[] dateTime = date_time_textfield.getText().split(" ");

		String[] date = dateTime[0].split("/");

		String[] time = dateTime[1].split(":");

		// Date was stored in dd/mm/yyyy so must inverse
		currentRecord.setDate(
				new java.sql.Date(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0])));

		// Time was stored as hh:mm:ss so must inverse as well
		currentRecord.setDateTime(
				new java.sql.Time(Integer.parseInt(time[2]), Integer.parseInt(time[1]), Integer.parseInt(time[0])));

		
		//Subjective Components
		Subjective currentSubjective = new Subjective();

		if (subjectiveTextArea.getText() != null && subjectiveTextArea.getText() != "") {

			tempStr = subjectiveTextArea.getText();

			currentSubjective.setNotes(tempStr);

		}

		// Collect non-empty symptoms
		Symptom[] symptoms = (Symptom[]) getTextFieldsToArray(currentSubjective, symptomsVbox.getChildren());

		currentSubjective.setSymptoms(symptoms);


		//Objective Components
		Objective currentObjective = new Objective();

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
		String[] observables = (String[]) getTextFieldsToArray(currentObjective, observablesVbox.getChildren());

		currentObjective.setObservables(observables);

		 //Assessment Components
		Assessment currentAssessment = new Assessment();

		if (assessmentTextArea.getText() != null && assessmentTextArea.getText() != "") {

			tempStr = assessmentTextArea.getText();

			currentAssessment.setNotes(tempStr);

		}

		// Collect non-empty diagnosis
		String[] diagnosis = (String[]) getTextFieldsToArray(currentAssessment, diagnosisVbox.getChildren());

		currentAssessment.setDiagnosis(diagnosis);

		//Plan Components
		Plan currentPlan = new Plan();

		if (planTextArea.getText() != null && planTextArea.getText() != "") {

			tempStr = planTextArea.getText();

			currentPlan.setPatientEducation(tempStr);

		}

		// Collect non-empty tests
		String[] tests = (String[]) getTextFieldsToArray(currentPlan, testsVbox.getChildren());

		currentPlan.setTests(tests);

		// Collect non-empty procedures
		String[] procedures = (String[]) getTextFieldsToArray(currentPlan, proceduresVbox.getChildren());

		currentPlan.setProcedures(procedures);

		// Collect non-empty consultations
		String[] consultations = (String[]) getTextFieldsToArray(currentPlan, consultationsVbox.getChildren());

		currentPlan.setConsultations(consultations);

		//Creating SOAP note
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

	}*/

	public Object[] getTextFieldsToArray(Note entity, ObservableList<Node> arrayList) {

		String tempStr;

		List newList;

		if (entity instanceof Subjective) {

			newList = new ArrayList<Symptom>();

			for (int i = 1; i < arrayList.size(); i++) {

				tempStr = ((TextField) arrayList.get(i)).getText();

				if (tempStr != null && tempStr != "") {

					Symptom symptom = new Symptom(tempStr);

					newList.add(symptom);

				}

			}

			Symptom[] returnArray = new Symptom[newList.size()];

			for (int i = 0; i < returnArray.length; i++) {

				returnArray[i] = (Symptom) newList.get(i);

			}

			return returnArray;

		} else {

			newList = new ArrayList<String>();

			for (int i = 2; i < arrayList.size(); i++) {

				tempStr = ((TextField) arrayList.get(i)).getText();

				if (tempStr != null && tempStr != "") {

					newList.add(tempStr);

				}

			}

			String[] returnArray = new String[newList.size()];

			for (int i = 0; i < returnArray.length; i++) {

				returnArray[i] = (String) newList.get(i);

			}

			return returnArray;

		}

	}
	
	public boolean isPatientSelected() {
		
		return patientSelected;
		
	}
	
	public void setPatientSelected(boolean bool) {
		
		patientSelected = bool;
		
	}
	
	public Patients getCurrentPatient () {
		
		return currentPatient;
		
	}
	
	public TitledPane[] getNotesNodes () {
		
		return new TitledPane[] {subjective,objective,assessment,plan};
		
	}
	
	public Page getCurrentPage() {
		
		return currentPage;
		
	}
	
	public void setCurrentPage(Page page) {
		
		currentPage = page;
		
	}
	
	public void setCurrentRecord(Records record) {
		
		currentRecord = record;
		
	}
	
	public Records getCurrentRecord () {
		
		return currentRecord;
		
	}
	
	public String[] getRecordDateTime () {
		
		String[] dateTime = date_time_textfield.getText().split(" ");
		
		System.out.println(dateTime);
		
		return dateTime;
		
	}
	
	public int getRecordsIndex() {
		
		return recordsIndex;
		
	}

}
