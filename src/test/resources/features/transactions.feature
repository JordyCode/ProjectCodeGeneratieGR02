Feature: Transactions

  Scenario: Get all transactions without login
    When I request /transactions without login token
    Then I get a response of 403 for transactions

  Scenario: Get all transactions as an employee
    Given I have a valid token for "employee"
    When I request the transactions endpoint
    Then I get a list of 4 transactions

  Scenario: Get all transactions as an user
    Given I have a valid "user" bearer token
    When I request the transactions endpoint
    Then I get a response of 200
    Then I get a list of 1 transactions

  Scenario: Create a transaction as an employee
  Scenario: Create a valid transaction as a user
  Scenario: Create an invalid transaction as a user
  Scenario:
  Scenario:
  Scenario:
  Scenario:
  Scenario:
  Scenario:
