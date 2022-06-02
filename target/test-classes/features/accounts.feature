Feature: Accounts

  # Scenario's for getting all accounts
  Scenario: Get all accounts without login
    When I request /accounts without login token
    Then I get a response of 400 bad request

  Scenario: Get all accounts as an employee
    Given I have a valid "employee" bearer token
    When I request the accounts endpoint
    Then I get a response of 200 ok
    Then I get a list of 2 accounts

  Scenario: Get all accounts as an user
    Given I have a valid "user" bearer token
    When I request the accounts endpoint
    Then I get a response of 200 ok
    Then I get a list of 1 account