Feature: Accounts

  # Scenario's for getting all accounts
  Scenario: Get all accounts without login
    When I request /accounts without login token
    Then I get a response of 403

  Scenario: Get all accounts as an employee
    Given I have an "employee" bearer token
    When I request the /accounts endpoint
    Then I get a response of 200
    Then I get a list of 6 accounts

  Scenario: Get all accounts as an user
    Given I have an "user" bearer token
    When I request the /accounts endpoint
    Then I get a response of 200
    Then I get a list of 2 accounts

  Scenario: Get all accounts with expired token
    Given I have an "expired" bearer token
    When I request the /accounts endpoint
    Then I get a response of 403

  Scenario: Get all accounts with invalid token
    Given I have an "invalid" bearer token
    When I request the /accounts endpoint
    Then I get a response of 403

  # Scenario's to get a single account
  Scenario: Get single account without login token
    When I request /accounts/id with id of 9 without login token
    Then I get a response of 403

  Scenario: Get the bank information as an employee
    Given I have an "employee" bearer token
    When I request /accounts/id with id of 1
    Then I get a response of 403

  Scenario: Get the bank information as an user
    Given I have an "user" bearer token
    When I request /accounts/id with id of 1
    Then I get a response of 403

  Scenario: Get a single account as an employee
    Given I have an "employee" bearer token
    When I request /accounts/id with id of 9
    Then I get a response of 200
    And I get JSON objects containing "iban" with value of "NL53INHO4715545128"

  Scenario: Get own account when calling /accounts as an user
    Given I have an "user" bearer token
    When I request the /accounts endpoint
    Then I get a response of 200
    And I get JSON array containing "iban" with value of "NL53INHO4715545127"

  Scenario: Get other account as an user
    Given I have an "user" bearer token
    When I request /accounts/id with id of 9
    Then I get a response of 403

  # Scenario's to update account
  Scenario: Update account without login token
    When I make a PUT request on the /accounts/id endpoint
    Then I get a response of 403

  Scenario: Update account as an user
    Given I have an "user" bearer token
    And I have all the account objects to update
    When I make a PUT request on the /accounts/id with id of 6
    Then I get a response of 403

  Scenario: Update account as an employee
    Given I have an "employee" bearer token
    And I have all the account objects to update
    When I make a PUT request on the /accounts/id with id of 6
    Then I get a response of 200

  # Scenario's to post accounts
  Scenario: Create account without login token
    When I make a POST request on the /accounts endpoint without login token
    Then I get a response of 403

  Scenario: Create account as an user
    Given I have an "user" bearer token
    And I have all the account objects
    When I make a POST request on the /accounts endpoint
    Then I get a response of 403

  Scenario: Create account as an employee
    Given I have an "employee" bearer token
    And I have all the account objects
    When I make a POST request on the /accounts endpoint
    Then I get a response of 201
