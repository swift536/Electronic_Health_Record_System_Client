package com.genuinedeveloper.restfulclient;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.genuinedeveloper.restfulclient.controllers.LoginPageController;
import com.genuinedeveloper.restfulclient.controllers.VisitPageController;
import com.genuinedeveloper.restfulclient.controllers.sub_controllers.MedicationPageController;
import com.genuinedeveloper.restfulclient.controllers.sub_controllers.RecordsPageController;
import com.genuinedeveloper.restfulclient.controllers.sub_controllers.UpdatePatientController;
import com.genuinedeveloper.utilities.UserCredentials;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;

@SpringBootApplication
public class OpenEHRS extends Application {

	private ConfigurableApplicationContext springContext;
	
	public void init () throws Exception {
		
		ApplicationContextInitializer<GenericApplicationContext> initializer = new ApplicationContextInitializer<GenericApplicationContext> () {

			@Override
			public void initialize(GenericApplicationContext applicationContext) {
				applicationContext.registerBean(Application.class,  () -> OpenEHRS.this);
				applicationContext.registerBean(Parameters.class, () -> getParameters());
				applicationContext.registerBean(HostServices.class, () -> getHostServices());
				applicationContext.registerBean(RESTConfig.class);
				applicationContext.registerBean(UserCredentials.class);
				applicationContext.registerBean(LoginPageController.class);
				applicationContext.registerBean(VisitPageController.class);
				applicationContext.registerBean(RecordsPageController.class);
				applicationContext.registerBean(MedicationPageController.class);
				applicationContext.registerBean(UpdatePatientController.class);
			}
			
		};
		
		springContext = new SpringApplicationBuilder()
				.sources(OpenEHRS.class)
				.initializers(initializer)
				.run(getParameters().getRaw().toArray(new String[0]));
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		StageReadyEvent event = new StageReadyEvent(primaryStage);
		event.setNextPage("login");
		
		this.springContext.publishEvent(event);
	}
	
	@Override
	public void stop () throws Exception{
		this.springContext.close();
		Platform.exit();
	}
	
}