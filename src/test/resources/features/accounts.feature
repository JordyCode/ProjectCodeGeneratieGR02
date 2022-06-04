Feature: Accounts

  # Scenario's for getting all accounts
#  Scenario: Get all accounts without login
#    When I request /accounts without login token
#    Then I get a response of 403

#  Scenario: Get all accounts as an employee
#    Given I have a valid "employee" bearer token
#    When I request the accounts endpoint
#    Then I get a response of 200
#    Then I get a list of 2 accounts
#
#  Scenario: Get all accounts as an user
#    Given I have a valid "user" bearer token
#    When I request the accounts endpoint
#    Then I get a response of 200
#    Then I get a list of 1 account

  Scenario: Get all accounts with expired token
    Given I have an "expired" token
    When I request the accounts endpoint
    Then I get a response of 403

  Scenario: Get all accounts with invalid token
    Given I have an "invalid" token
    When I request the accounts endpoint
    Then I get a response of 403

#    Scenario: Create an account with an employee
#      Given