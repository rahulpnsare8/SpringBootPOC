Feature:  User feature
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

	