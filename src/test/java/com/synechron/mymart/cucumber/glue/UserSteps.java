package com.synechron.mymart.cucumber.glue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synechron.mymart.model.AuthRequest;
import com.synechron.mymart.model.Login;
import com.synechron.mymart.model.User;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UserSteps extends BaseStep{
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private ObjectMapper mapper;
	
	private AuthRequest authRequest;
	
	private String jwtToken;
	
	private List<Object> userList;
	
	private User user;
	
	private User registerdUser;
	
	private int userId=0;
	

	public UserSteps(TestContext testContext) {
		super(testContext);
	}

	@Before
	public void setup() {
		userList = new ArrayList<Object>();
		authRequest = new AuthRequest();
	}
	
	@Given("^the following are user details by map$")
	public void givenFollowingAreUserDatailsByMap(DataTable table) {
		user = new User();
		List<Map<String, String>> rows = table.asMaps(String.class, String.class);
		for (Map<String, String> columns : rows) {
	        user.setName(columns.get("name"));
	        user.setAddress(columns.get("address"));
	        user.setContactNumber(Long.parseLong(columns.get("contactNumber")));
	        Login login = new Login();
	        login.setUserName(columns.get("userName"));
	        login.setPassword(columns.get("password"));
	        user.setCredentials(login);
	    }
	}
	
	@When("^client calls register user$")
	public void whenClientCallsRegisterUser() throws JsonMappingException, JsonProcessingException {
		String url = "/user/register";
		registerdUser = mapper.readValue(testRestTemplate.postForEntity(url, user, String.class).getBody(), User.class);
	}
	
	@Then("^the registered user returned$")
	public void thenRegisteredUserReturned() {
		Assertions.assertTrue(registerdUser.getUserId() > 0);;
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
		Assertions.assertEquals(4, userList.size());
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
