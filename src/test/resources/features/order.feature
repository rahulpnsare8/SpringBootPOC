#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: Order feature
	
	Scenario: client make a call for jwt login
		When client calls jwt login using username rahul with password test
		Then a jwt token returned
		
	Scenario: client make a call to place order
		Given the following are the order details by map
			|  orderStatus | paymentType | userId | productId |
			|  In process  | online	     | 1      | 10001     |
		When client calls place order
		Then check the API call http status
			
 
  Scenario: client make a call get all orders
    When client calls get all orders
    Then all the orders returned
    
  Scenario: client make a call get a order
    When client calls a get order using orderId 69
    Then the order returned

  
