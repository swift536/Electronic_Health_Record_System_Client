package com.genuinedeveloper.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.genuinedeveloper.restfulclient.RESTAPI;
import com.genuinedeveloper.restfulclient.StageReadyEvent;
import com.genuinedeveloper.restfulclient.OpenEHRS;
import com.genuinedeveloper.utilities.UserCredentials;

import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;

@Component
public class LoginPageController {
	@FXML
	private TextField id_textfield;
	@FXML
	private TextField username_textfield;
	@FXML
	private TextField password_textfield;
	@FXML
	private Button submit_button;
	@FXML
	private TextField error_textfield;

    @Autowired
    public RESTAPI restApiObject;
    
    private final HostServices hostServices;
    
    @Autowired
    private ApplicationContext context;

    public LoginPageController(HostServices hostServices) {
        this.hostServices = hostServices;
    }
	
	@FXML
	public void initialize () {

    	//Debug login info 1, "test hash-user", "test hash-pass"
		id_textfield.setText("1");
		username_textfield.setText("test hash-user");
		password_textfield.setText("test hash-pass");
    	
		
		submit_button.setOnAction (new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				boolean result = restApiObject.login(Integer.parseInt(id_textfield.getText()),
						username_textfield.getText(),
						password_textfield.getText());
				
				if (!result) {
					error_textfield.setStyle("-fx-text-fill: red");
					error_textfield.setText("Incorrect credentials, please try again");
				} else {
					StageReadyEvent loginPageEvent = new StageReadyEvent(id_textfield.getParent().getScene().getWindow());
					loginPageEvent.setNextPage("main");
					
					context.publishEvent(loginPageEvent);
				}
				
			}
			
		});
		
	}
}
