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
Feature:  User feature
	Scenario: client make a call to register user 
		Given the following are user details by map
		|name  | address | contactNumber |userName | password |
		|Rahul | Pune    | 7687767865    | user    | user1    |
		When client calls register user 
		Then the registered user returned
		
	Scenario: client make a call for login
		When client calls login using username rahul with password test
		Then the jwt token returned
		
	Scenario: client make a call to get all users
		When client calls get all users
		Then all the users returned
		
	Scenario: client make a call to get a user
		Given the following userId
			|1	   |
		When client calls get user
		Then the user returned
      