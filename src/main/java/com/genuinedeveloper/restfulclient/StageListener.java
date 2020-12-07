package com.genuinedeveloper.restfulclient;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.net.URL;

@Component
public class StageListener implements ApplicationListener<StageReadyEvent> {

    private final String applicationTitle;
    private final Resource fxml;
    private final ApplicationContext applicationContext;

    public StageListener(@Value("${spring.application.ui.title}") String applicationTitle,
                         @Value("classpath:Scenes/login_page.fxml") Resource fxml, ApplicationContext applicationContext) {
        this.applicationTitle = applicationTitle;
        this.fxml = fxml;
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
    	
    	ClassLoader cl = getClass().getClassLoader();
    	String page = stageReadyEvent.getNextPage();
    	String pagePath = "";
    	int width = 0;
    	int height = 0;
    	
    	
        try {
        	
        	switch (page) {
	        	case "login":
	        		pagePath = "Scenes/login_page.fxml";
	        		width = 600;
	        		height = 400;
	        		break;
	        	case "main":
	        		pagePath = "Scenes/main_scene2.fxml";
	        		width = 1280;
	        		height = 720;
	        		break;
        	}
        	
            Stage stage = stageReadyEvent.getStage();

            URL url = cl.getResource(pagePath);
            
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            
            Parent root = fxmlLoader.load();
            
            Scene scene = new Scene(root, width, height);

            stage.setScene(scene);
            
            stage.setTitle(this.applicationTitle);
            
            stage.show();
            
        } catch (IOException e) {
        	
            throw new RuntimeException(e);
            
        }
    }
    

}
