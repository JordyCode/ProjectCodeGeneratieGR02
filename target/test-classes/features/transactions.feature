Feature: Transactions

#  Get all Transactions
  Scenario: Get all transactions without login
    When I request /transactions without login token
    Then I get a response of 403 for transactions

  Scenario: Get all transactions as an employee
    Given I have a valid token for employee
    When I request the /transactions endpoint
    Then I get a response of 302 for transactions
    Then I get a list of 5 transactions

  Scenario: Get all transactions as user
    Given I have a valid token for user
    When I request the /transactions endpoint
    Then I get a response of 302 for transactions
    Then I get a list of 5 transactions

  Scenario: Get all transactions as user with no transactions
    Given I have a valid user token for empty users
    When I request the /transactions endpoint
    Then I get a response of 403 for transactions

#    Get a specific transaction
  Scenario: Get a specific transaction as an employee
    Given I have a valid token for employee
    When I request the /transactions/id with id of 12
    Then I get a response of 302 for transactions

  Scenario: Get a specific transaction as user with access
    Given I have a valid token for user
    When I request the /transactions/id with id of 12
    Then I get a response of 302 for transactions

  Scenario: Get a specific transaction as user with no access
    Given I have a valid token for empty user
    When I request the /transactions/id with id of 12
    Then I get a response of 400 for transactions

  Scenario: Get a specific transaction as user with an invalid token
    Given I have an invalid token
    When I request the /transactions/id with id of 12
    Then I get a response of 403 for transactions

    #Create a transaction
  Scenario: Create a valid transaction as an employee
    Given I have a valid token for employee
    And I have a valid transaction object
    When  I request POST /transactions endpoint
    Then I get a response of 201 for transactions

  Scenario: Create a valid transaction as a valid user
    Given I have a valid token for user
    And I have a valid transaction object
    When  I request POST /transactions endpoint
    Then I get a response of 201 for transactions

  Scenario: Create an valid transaction as a user with no access
    Given I have a valid user token for empty users
    When I request POST /transactions endpoint
    Then I get a response of 403 for transactions

  Scenario: Create an invalid transaction
    Given I have an invalid token
    When I request POST /transactions endpoint
    Then I get a response of 403 for transactions

    #Create a deposit transaction
#  Only the user who the account belongs to can deposit so should give an error
  Scenario: Create a valid deposit as an employee
    Given I have a valid token for employee
    And I have a valid deposit object
    When  I request POST /transactions/deposit endpoint
    Then I get a response of 400 for transactions

    #  Only the user who the account belongs to can deposit so should give OK
  Scenario: Create a valid deposit as a valid user
    Given I have a valid token for user
    And I have a valid deposit object
    When  I request POST /transactions/deposit endpoint
    Then I get a response of 201 for transactions

    #  Only the user who the account belongs to can deposit so should give an error
  Scenario: Create an valid deposit as a user with no access
    Given I have a valid user token for empty users
    And I have a valid deposit object
    When  I request POST /transactions/deposit endpoint
    Then I get a response of 403 for transactions

    #  Only the user who the account belongs to can deposit so should give an error
  Scenario: Create an invalid deposit
    Given I have an invalid token
    When  I request POST /transactions/deposit endpoint
    Then I get a response of 403 for transactions

        #Create a withdraw transaction
    #  Only the user who the account belongs to can withdraw so should give an error
  Scenario: Create a valid withdraw as an employee
    Given I have a valid token for employee
    And I have a valid withdraw object
    When  I request POST /transactions/withdraw endpoint
    Then I get a response of 400 for transactions

    #  Only the user who the account belongs to can withdraw so should give OK
  Scenario: Create a valid withdraw as a valid user
    Given I have a valid token for user
    And I have a valid withdraw object
    When  I request POST /transactions/withdraw endpoint
    Then I get a response of 201 for transactions

    #  Only the user who the account belongs to can withdraw so should give an error
  Scenario: Create an valid withdraw as a user with no access
    Given I have a valid user token for empty users
    And I have a valid withdraw object
    When  I request POST /transactions/withdraw endpoint
    Then I get a response of 403 for transactions

    #  Only the user who the account belongs to can deposit so should give an error
  Scenario: Create an invalid deposit
    Given I have an invalid token
    When  I request POST /transactions/withdraw endpoint
    Then I get a response of 403 for transactions
