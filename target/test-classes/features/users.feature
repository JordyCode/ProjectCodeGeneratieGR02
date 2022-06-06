Feature: Users

#  Get all Users
  Scenario: Get all users without login
    When I request /users without login token
    Then I get a response of 403 for users

  Scenario: Get all users as an employee
    Given I have a valid employee token for users
    When I request the /users endpoint
    Then I get a response of 200 for users
    Then I get a list of 5 users

  Scenario: Get all users as an user
    Given I have a valid user token for users
    When I request the /users endpoint
    Then I get a response of 200 for users
    Then I get a list of 1 users

  Scenario: Get all users as an employee with no valid token
    Given I have a invalid token for users
    When I request the /users endpoint
    Then I get a response of 403 for users

#    Get specific User
  Scenario: Get a specific user as an employee
    Given I have a valid employee token for users
    When I request the /users/userId with 5 endpoint
    Then I get a response of 200 for users

  Scenario: Get a specific user as an employee
    Given I have a valid employee token for users
    When I request the /users/userId with 5 endpoint
    Then I get a response of 200 for users

  Scenario: Get a specific user as a user with access
    Given I have a invalid token for users
    When I request the /users/userId with 5 endpoint
    Then I get a response of 403 for users

#    Create User
  Scenario: Create a user as an employee
    Given I have a valid employee token for users
    And I have all user objects filled
    When I request the POST /users endpoint
    Then I get a response of 200 for users

  Scenario: Create a user as a user
    Given I have a valid user token for users
    And I have all user objects filled
    When I request the POST /users endpoint
    Then I get a response of 403 for users

  Scenario: Create a user without login
    Given I have a invalid token for users
    When I request the POST /users endpoint
    Then I get a response of 403 for users

#    Update User
  Scenario: Update a user as an employee
    Given I have a valid employee token for users
    And I have user objects that need to be updated
    When I request the PUT /users/id with id of 5 endpoint
    Then I get a response of 200 for users

  Scenario: Update a user as user
    Given I have a valid user token for users
    And I have user objects that need to be updated
    When I request the PUT /users/id with id of 5 endpoint
    Then I get a response of 403 for users

  Scenario: Update a user as user with no access
    Given I have a valid user token for empty users
    And I have user objects that need to be updated
    When I request the PUT /users/id with id of 3 endpoint
    Then I get a response of 403 for users