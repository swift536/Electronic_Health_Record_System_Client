package com.genuinedeveloper.restfulclient.controllers.sub_controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.genuinedeveloper.restfulclient.controllers.VisitPageController;

@SpringBootTest
class RecordsPageControllerTest {

	@Autowired
	VisitPageController vpController;
	
	@Test
	void test() {

		String str = vpController.getCurrentRecord().getDate().toString();

	}

}
