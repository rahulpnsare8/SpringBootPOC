package com.synechron.mymart.cucumber.glue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Arrays;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synechron.mymart.model.AuthRequest;
import com.synechron.mymart.model.Order;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class OrderSteps extends BaseStep{

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private ObjectMapper mapper;
	
	private AuthRequest authRequest;
	
	private String jwtToken;
	
	private List<Object> orderList;
	
	private Order order;
	
	private ResponseEntity<String> response;
	
	public OrderSteps(TestContext testContext) {
		super(testContext);
	}
	
	@Before
	public void setup() {
		orderList = new ArrayList<Object>();
		authRequest = new AuthRequest();
	}
	
	@When("^client calls jwt login using username (.*) with password (.*)$")
	public void whenClientCallsJwtLogin(String username, String password) {
		authRequest.setPassword(password);
		authRequest.setUsername(username);
		jwtToken = testRestTemplate.postForEntity("/login", authRequest, String.class).getBody();
		getScenarioContext().setContext("jwtToken", jwtToken);
	}
	
	@Then("^a jwt token returned$")
	public void theJwtTokenReturned() {
		Assertions.assertNotNull(jwtToken);
	}
	
	@Given("^the following are the order details by map$")
	public void givenFollowingAreTheOrderDatailsByMap(DataTable table) {
		order = new Order();
		List<Map<String, String>> rows = table.asMaps(String.class, String.class);
		for (Map<String, String> columns : rows) {
			order.setOrderStatus(columns.get("orderStatus"));
			order.setPaymentType(columns.get("paymentType")); 
			order.setProductId(Integer.parseInt(columns.get("productId")));
			order.setUserId(Integer.parseInt(columns.get("userId")));
	    }
	}
	
	@When("^client calls place order$")
	public void whenClientCallsPlaceOrder() throws JsonMappingException, JsonProcessingException, JSONException {
		String url = "/order/save";
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    JSONObject orderJsonObject = new JSONObject();
	    orderJsonObject.put("orderStatus", order.getOrderStatus());
	    orderJsonObject.put("paymentType", order.getPaymentType());
	    orderJsonObject.put("userId", order.getUserId());
	    orderJsonObject.put("productId", order.getProductId());
	    
	    headers.add("Authorization", "Bearer " + getScenarioContext().getContext("jwtToken"));
	    HttpEntity<String> request = new HttpEntity<String>(orderJsonObject.toString(),headers);
	    
		response = testRestTemplate.postForEntity(url, request, String.class);
	}
	
	@Then("^check the API call http status$")
	public void thenRegisteredUserReturned() {
		Assertions.assertEquals(200, response.getStatusCode().value());
	}
	
	@When("^client calls get all orders$")
	public void whenClientCallsGetAllOrders() throws IOException {
		String url = "/order/getAll";
	    ResponseEntity<String> response = testRestTemplate.exchange(url, HttpMethod.GET, configureHttpEntity(), String.class);
	    JsonFactory factory = new JsonFactory();
	    JsonParser jsonParser = factory.createParser( response.getBody());
	    List<Object> asList = Arrays.asList(mapper.readValue(jsonParser,Order[].class));
	    orderList.addAll(asList);
	    
	}
	
	@Then("^all the orders returned$")
	public void thenAllTheOrdersReturned() {
		Assertions.assertEquals(16, orderList.size());
	} 
	
	@When("client calls a get order using orderId {int}")
	public void client_calls_a_get_order_using_order_id(Integer orderId) throws IOException {
			String url = "/order/get?orderId="+orderId;
		    ResponseEntity<String> response = testRestTemplate.exchange(url, HttpMethod.GET, configureHttpEntity(), String.class);
		    JsonFactory factory = new JsonFactory();
		    JsonParser jsonParser = factory.createParser( response.getBody());
		    order = mapper.readValue(jsonParser, Order.class);
	}
	
	@Then("^the order returned$")
	public void thenTheUserReturned() {
		Assertions.assertEquals(10001,order.getProductId());
	}
	
	private HttpEntity<?> configureHttpEntity() {
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "Bearer " + getScenarioContext().getContext("jwtToken"));
	    HttpEntity<?> request = new HttpEntity<>(headers);
	    return request;
	}

	

}
