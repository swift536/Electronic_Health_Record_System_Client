package com.genuinedeveloper.restfulclient;

import org.springframework.context.ApplicationEvent;

import javafx.stage.Stage;

public class StageReadyEvent extends ApplicationEvent {

	String nextPage;
	
    public Stage getStage() {
        return Stage.class.cast(getSource());
    }

    public StageReadyEvent(Object source) {
        super(source);
    }
    
    public void setNextPage (String page) {
    	nextPage = page;
    }
    
    public String getNextPage () {
    	return nextPage;
    }
}