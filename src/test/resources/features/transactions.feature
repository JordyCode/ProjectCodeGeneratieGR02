Feature: Transactions

  Scenario: Get all transactions without login
    When I request /transactions without login token
    Then I get a response of 403 for transactions

  Scenario: Get all transactions as an employee
    Given I have a valid token for employee
    When I request the /transactions endpoint
    Then I get a response of 302 for transactions
    Then I get a list of 5 transactions

  Scenario: Get a specific transaction as an employee
    Given I have a valid token for employee
    When I request the /transactions/id with 13 endpoint
    Then I get a response of 302 for transactions
#   Then I get a list of 1 transactions
#    Then I get a specific transaction of transactions/13

  Scenario: Get all transactions as an employee
    Given I have an invalid token for employee
    When I request the /transactions endpoint
    Then I get a response of 403 for transactions

  Scenario: Get all transactions as a user who has no transactions
    Given I have a valid token for user
    When I request the /transactions endpoint
    Then I get a response of 403 for transactions

  Scenario: Create a valid transaction as an employee
    Given I have a valid token for employee
    When I request the /transactions/13 endpoint
#    And I have a valid transaction object
    Then I get a response of 201 for transactions



  Scenario: Create a valid transaction as a user
  Scenario: Create an invalid transaction as a user





  Scenario:
  Scenario:
  Scenario:
  Scenario:
  Scenario:
  Scenario:
