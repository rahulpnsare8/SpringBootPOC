package com.synechron.mymart.cucumber.glue;

import org.springframework.stereotype.Component;

@Component
public class TestContext {
	
	private ScenarioContext scenarioContext;
	 
	 public TestContext() {
		 scenarioContext = new ScenarioContext();
	 }
	 
	 public ScenarioContext getScenarioContext() {
		 return scenarioContext;
	 }

}
