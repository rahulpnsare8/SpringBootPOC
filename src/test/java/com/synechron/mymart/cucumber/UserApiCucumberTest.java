package com.synechron.mymart.cucumber;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.synechron.mymart.MymartApplication;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@SpringBootTest(classes = {MymartApplication.class,
				UserApiCucumberTest.class},
				webEnvironment = WebEnvironment.RANDOM_PORT)
@CucumberOptions(plugin = "{pretty}", tags = "", features = "src/main/test/resources/features",glue = "/glue")
public class UserApiCucumberTest {

}
