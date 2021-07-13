package com.synechron.mymart.cucumber;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "{pretty}", tags = "", features = "src/main/test/resources/features",glue = "/glue")
public class OrderApiCucumberTest {

}
