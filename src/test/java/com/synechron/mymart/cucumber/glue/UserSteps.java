package com.synechron.mymart.cucumber.glue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synechron.mymart.model.AuthRequest;
import com.synechron.mymart.model.User;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UserSteps extends BaseStep{

	public UserSteps(TestContext testContext) {
		super(testContext);
	}

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private ObjectMapper mapper;
	
	private AuthRequest authRequest;
	
	private String jwtToken;
	
	private List<Object> userList;
	
	private User user;
	
	private int userId=0;
	
	@Before
	public void setup() {
		userList = new ArrayList<Object>();
		authRequest = new AuthRequest();
	}
	
	@When("^client calls login using username (.*) with password (.*)$")
	public void whenClientCallsLogin(String username, String password) {
		authRequest.setPassword(password);
		authRequest.setUsername(username);
		jwtToken = testRestTemplate.postForEntity("/login", authRequest, String.class).getBody();
		getScenarioContext().setContext("jwtToken", jwtToken);
	}
	
	@Then("^the jwt token returned$")
	public void theJwtTokenReturned() {
		Assertions.assertNotNull(jwtToken);
	}
	
	@When("^client calls get all users$")
	public void whenClientCallsGetAllUsers() throws IOException {
		String url = "/user/getAll";
	    ResponseEntity<String> response = testRestTemplate.exchange(url, HttpMethod.GET, configureHttpEntity(), String.class);
	    JsonFactory factory = new JsonFactory();
	    JsonParser jsonParser = factory.createParser( response.getBody());
	    List<Object> asList = Arrays.asList(mapper.readValue(jsonParser,User[].class));
	    userList.addAll(asList);
	    
	}
	
	@Then("^all the users returned$")
	public void thenAllTheUsersReturned() {
		Assertions.assertEquals(2, userList.size());
//		IntStream.range(0, userList.size())
//			.forEach(index -> validateUser("Rahul Pansare",(User) userList.get(index)));
	}

	@Given("^the following userId$")
	public void givenTheFollowingUserId(String userId) {
		this.userId = Integer.parseInt(userId);
	}
	
	@When("^client calls get user$")
	public void whenClientCallsGetUser() throws IOException {
			String url = "/user/get?userId="+userId;
		    ResponseEntity<String> response = testRestTemplate.exchange(url, HttpMethod.GET, configureHttpEntity(), String.class);
		    JsonFactory factory = new JsonFactory();
		    JsonParser jsonParser = factory.createParser( response.getBody());
		    user = mapper.readValue(jsonParser, User.class);
	}
	
	@Then("^the user returned$")
	public void thenTheUserReturned() {
		validateUser("Rahul Pansare", user);
	}
	
	private void validateUser(String name, User user) {
		Assertions.assertEquals(name, user.getName());
	}
	
	private HttpEntity<?> configureHttpEntity() {
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "Bearer " + getScenarioContext().getContext("jwtToken"));
	    HttpEntity<?> request = new HttpEntity<>(headers);
	    return request;
	}
}
