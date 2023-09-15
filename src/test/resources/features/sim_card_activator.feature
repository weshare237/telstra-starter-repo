Feature: SIM Card Activation

  Scenario: Successful SIM Card Activation
    Given a SIM card with ICCID "1255789453849037777"
    When I activate the SIM card
    Then the activation should be successful

  Scenario: Failed SIM Card Activation
    Given a SIM card with ICCID "8944500102198304826"
    When I activate the SIM card
    Then the activation should fail
