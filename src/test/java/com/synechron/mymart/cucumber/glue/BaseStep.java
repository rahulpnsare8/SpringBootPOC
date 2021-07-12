package com.synechron.mymart.cucumber.glue;

public class BaseStep {

	 private ScenarioContext scenarioContext;
	 
    public BaseStep(TestContext testContext) {
     scenarioContext = testContext.getScenarioContext();
    }
    
    public ScenarioContext getScenarioContext() {
        return scenarioContext;
    }
}
