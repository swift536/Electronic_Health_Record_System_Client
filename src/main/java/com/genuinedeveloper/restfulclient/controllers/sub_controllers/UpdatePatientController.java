package com.genuinedeveloper.restfulclient.controllers.sub_controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.genuinedeveloper.db_entities.Patients;
import com.genuinedeveloper.db_entities.Patients.Gender;
import com.genuinedeveloper.restfulclient.RESTAPI;
import com.genuinedeveloper.restfulclient.controllers.VisitPageController;
import com.genuinedeveloper.restfulclient.controllers.VisitPageController.Page;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

@Component
public class UpdatePatientController {

	@Autowired
	VisitPageController vpController;
	
	@Autowired
	RESTAPI restApiObject;

	Patients currentPatient;

	EventHandler<ActionEvent> updatePatientButtonEvent;

	public void intialize () {
		
	updatePatientButtonEvent = new EventHandler<ActionEvent>() {
		

		@Override
		public void handle(ActionEvent event) {

			try {

				ClassLoader cl = getClass().getClassLoader();

				URL url = cl.getResource("Scenes/patient_info_page.fxml");

				FXMLLoader loader = new FXMLLoader(url);

				GridPane gridPane = (GridPane) loader.load();
				
				currentPatient = vpController.getCurrentPatient();

				if (vpController.isPatientSelected()) {

					((TextField) gridPane.getChildren().get(14)).setText(new String(currentPatient.getNameFirst()));

					((TextField) gridPane.getChildren().get(15))
							.setText(new String(currentPatient.getNameMiddle()));

					((TextField) gridPane.getChildren().get(16)).setText(new String(currentPatient.getNameLast()));

					((ChoiceBox) gridPane.getChildren().get(17)).setItems(FXCollections.observableArrayList(

							Gender.MALE, Gender.FEMALE

					));

					if (currentPatient.getGender() != null) {

						((ChoiceBox) gridPane.getChildren().get(17)).setValue(currentPatient.getGender());

					}

					if (currentPatient.getDobDate() != null) {

						((TextField) gridPane.getChildren().get(18))
								.setText(new String(currentPatient.getDobDate().toString()));

					}

					if (currentPatient.getDobTime() != null) {

						((TextField) gridPane.getChildren().get(19))
								.setText(new String(currentPatient.getDobTime().toString()));

					}

					if (currentPatient.getPhone() != null) {

						((TextField) gridPane.getChildren().get(20)).setText(new String(currentPatient.getPhone()));

					}

					if (currentPatient.getAddressLine1() != null) {

						((TextField) gridPane.getChildren().get(21))
								.setText(new String(currentPatient.getAddressLine1()));

					}

					if (currentPatient.getAddressLine2() != null) {

						((TextField) gridPane.getChildren().get(22))
								.setText(new String(currentPatient.getAddressLine2()));

					}

					if (currentPatient.getCity() != null) {

						((TextField) gridPane.getChildren().get(23)).setText(currentPatient.getCity());

					}

					if (currentPatient.getState() != null) {

						((TextField) gridPane.getChildren().get(24)).setText(currentPatient.getState());

					}

					if (currentPatient.getCountry() != null) {

						((TextField) gridPane.getChildren().get(25)).setText(currentPatient.getCountry());

					}

					if (currentPatient.getPostalCode() != null) {

						((TextField) gridPane.getChildren().get(26))
								.setText(currentPatient.getPostalCode().toString());

					}

					if (currentPatient.getImageUrl() != null) {

						((TextField) gridPane.getChildren().get(27)).setText(currentPatient.getImageUrl());

					}

					((Button) gridPane.getChildren().get(28)).setOnAction(new EventHandler<ActionEvent>() {

						@SuppressWarnings("deprecation")
						@Override
						public void handle(ActionEvent event) {

							String tempStr;

							try {

								tempStr = ((TextField) gridPane.getChildren().get(14)).getText();

								currentPatient.setNameFirst(tempStr.toCharArray());

								tempStr = "";

								tempStr = ((TextField) gridPane.getChildren().get(15)).getText();

								if (tempStr != null && tempStr != "") {

									currentPatient.setNameMiddle(tempStr.toCharArray());

									tempStr = "";

								}

								tempStr = ((TextField) gridPane.getChildren().get(16)).getText();

								currentPatient.setNameLast(tempStr.toCharArray());

								tempStr = "";

								Gender gender = (Gender) ((ChoiceBox) gridPane.getChildren().get(17)).getValue();

								if (gender != null) {

									currentPatient.setGender(gender);

								}

								// Reformat date input
								tempStr = ((TextField) gridPane.getChildren().get(18)).getText();

								if (tempStr != null && tempStr != "") {

									String[] date = tempStr.split("-");

									if (date.length == 3) {

										currentPatient.setDobDate(new Date(Integer.parseInt(date[0])-1900,
												Integer.parseInt(date[1])-1, Integer.parseInt(date[2])-1));

										tempStr = "";

									} else {

										Alert alert = new Alert(AlertType.INFORMATION);

										alert.setTitle("Invalid format: date of birth");

										alert.setHeaderText(null);

										alert.setContentText("date format invalid");

										alert.showAndWait();

										return;

									}

								}

								// Reformate time input
								tempStr = ((TextField) gridPane.getChildren().get(19)).getText();

								if (tempStr != null && tempStr != "") {

									String[] time = tempStr.split(":");

									if (time.length == 3) {

										currentPatient.setDobTime(new Time(Integer.parseInt(time[0]),
												Integer.parseInt(time[1]), Integer.parseInt(time[2])));

										tempStr = "";

									} else {

										Alert alert = new Alert(AlertType.INFORMATION);

										alert.setTitle("Invalid format: time of birth");

										alert.setHeaderText(null);

										alert.setContentText("date format invalid");

										alert.showAndWait();

										return;

									}

								}

								// Phone
								tempStr = ((TextField) gridPane.getChildren().get(20)).getText();

								if (tempStr != null && tempStr != "") {

									if (tempStr.length() == 7 || tempStr.length() == 8 || tempStr.length() == 0) {

										for (char character : tempStr.toCharArray()) {

											if (!Character.isDigit(character)) {

												Alert alert = new Alert(AlertType.INFORMATION);

												alert.setTitle("Invalid format: phone number");

												alert.setHeaderText(null);

												alert.setContentText("No special characters or letters");

												alert.showAndWait();

												return;

											}

										}

										tempStr = "";

									} else {

										Alert alert = new Alert(AlertType.INFORMATION);

										alert.setTitle("Invalid format: phone number");

										alert.setHeaderText(null);

										alert.setContentText("No special characters or letters");

										alert.showAndWait();

										return;

									}

									currentPatient.setPhone(tempStr.toCharArray());

								}

								tempStr = ((TextField) gridPane.getChildren().get(21)).getText();

								if (tempStr != null && tempStr != "") {

									currentPatient.setAddressLine1(tempStr.toCharArray());

								}

								tempStr = ((TextField) gridPane.getChildren().get(22)).getText();

								if (tempStr != null && tempStr != "") {

									currentPatient.setAddressLine2(tempStr.toCharArray());

								}

								tempStr = ((TextField) gridPane.getChildren().get(23)).getText();

								if (tempStr != null && tempStr != "") {

									currentPatient.setCity(tempStr);

								}

								tempStr = ((TextField) gridPane.getChildren().get(24)).getText();

								if (tempStr != null && tempStr != "") {

									currentPatient.setState(tempStr);

								}

								tempStr = ((TextField) gridPane.getChildren().get(25)).getText();

								if (tempStr != null && tempStr != "") {

									currentPatient.setCountry(tempStr);

								}

								tempStr = ((TextField) gridPane.getChildren().get(26)).getText();

								if (tempStr != null && tempStr != "") {

									currentPatient.setPostalCode(Integer.parseInt(tempStr));

								}

								tempStr = ((TextField) gridPane.getChildren().get(27)).getText();

								if (tempStr != null && tempStr != "") {

									currentPatient.setImageUrl(tempStr);

									/*
									 * Get image from memory and add it to patient as byte[] so it can be passed to
									 * server for updated/new images
									 */

								}

								restApiObject.postPatient(currentPatient);

								vpController.patientSelected();

							} catch (Exception e) {

								// TODO Auto-generated catch block
								e.printStackTrace();

							}

						}

					});

				}

				vpController.setCenterContent(gridPane);

				vpController.setTitle("Update Patient:");

				vpController.setCurrentPage(Page.UpdatePatient);

			} catch (IOException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}

	};
	
}

	
	public EventHandler<ActionEvent> getUpdatePatientEvent() {
		
		return updatePatientButtonEvent;
		
	}
}
