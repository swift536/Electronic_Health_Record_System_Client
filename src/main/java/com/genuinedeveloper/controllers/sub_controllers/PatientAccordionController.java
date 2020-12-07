package com.genuinedeveloper.controllers.sub_controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.genuinedeveloper.db_entities.Patients;
import com.genuinedeveloper.restfulclient.RESTAPI;

import javafx.application.HostServices;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;

import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@Component
public class PatientAccordionController {
	@FXML
	private TitledPane title_pane;
	@FXML
	private ImageView patient_image;
	@FXML
	private TextField first_name_textfield;
	@FXML
	private TextField middle_name_textfield;
	@FXML
	private TextField last_name_textfield;
	@FXML
	private TextField id_textfield;
	@FXML
	private TextField gender_textfield;
	@FXML
	private TextField dob_day_textfield;
	@FXML
	private TextField dob_time_textfield;

    @Autowired
    public RESTAPI restApiObject;
    
    private final HostServices hostServices;
    
    @Autowired
    private ApplicationContext context;

    public PatientAccordionController(HostServices hostServices) {
        this.hostServices = hostServices;
    }
	
	@FXML
	public void initialize () {
		
	}
	
	public void setPatient (Patients patient) throws IOException {
		
		byte[] imageByteArray = patient.getImage();
		
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageByteArray);
		
		BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
		
		ClassLoader cl = getClass().getClassLoader();
		
		URL url = cl.getResource("temp_data/");
		
		File outfile = new File("temp_data/patient_image.png");
		outfile.createNewFile();
		
		ImageIO.write(bufferedImage, "png", outfile);
		
		patient_image.setImage(new Image(outfile.toURI().toURL().toString()));
		
	}
}
