package com.synechron.mymart.cucumber.glue;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class ScenarioContext {

	private Map<String, Object> scenarioContext;

    public ScenarioContext(){
        scenarioContext = new HashMap<String, Object>();
    }

    public void setContext(String key, Object value) {
        scenarioContext.put(key, value);
    }

    public Object getContext(String key){
        return scenarioContext.get(key);
    }

    public Boolean isContains(String key){
        return scenarioContext.containsKey(key);
    }
	
}


