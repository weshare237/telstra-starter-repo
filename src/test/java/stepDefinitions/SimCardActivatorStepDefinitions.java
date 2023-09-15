package stepDefinitions;

import au.com.telstra.simcardactivator.SimCardActivator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import static org.junit.Assert.*;

import java.net.URI;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = SimCardActivator.class, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {
    private final TestRestTemplate restTemplate;

    public SimCardActivatorStepDefinitions(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private ResponseEntity<String> activationResponse;
    private ResponseEntity<String> checkStatusResponse;

    private String simCardICCID;
    private String activationResult;

    @Given("a SIM card with ICCID {string}")
    public void a_SIM_card_with_ICCID(String iccid) {
        this.simCardICCID = iccid;
    }

    @When("I activate the SIM card")
    public void i_activate_the_SIM_card() {
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the JSON payload
        String jsonPayload = "{\"iccid\": \"" + this.simCardICCID + "\"}";

        // Create a RequestEntity with the JSON payload and headers
        RequestEntity<String> requestEntity = new RequestEntity<>(jsonPayload, headers, HttpMethod.POST,
                URI.create("http://localhost:8080/api/v1/sim-cards"));

        this.activationResponse = restTemplate.exchange(
                requestEntity,
                String.class
        );

        this.activationResult = activationResponse.getBody();
    }

    @Then("the activation should be successful")
    public void the_activation_should_be_successful() {
        assertNotNull(activationResponse);
        assertEquals(HttpStatus.OK, activationResponse.getStatusCode());
        // Assuming the response contains a "success" field
        assertEquals("true", activationResult);
    }

    @Then("the activation should fail")
    public void the_activation_should_fail() {
        assertNotNull(activationResponse);
        assertEquals(HttpStatus.BAD_REQUEST, activationResponse.getStatusCode());
        // Assuming the response contains a "success" field
        assertEquals("false", activationResult);
    }

    @When("I check the activation status")
    public void i_check_the_activation_status() {
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Use activationResult to construct a request to check status based on the previous activation

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        this.checkStatusResponse = restTemplate.exchange(
                "http://localhost:8080/api/v1/sim-cards/{simCardId}", // Replace with your microservice's endpoint
                HttpMethod.GET,
                requestEntity,
                String.class,
                "8944500102198304826" // Replace with the appropriate activation record ID
        );
    }

    @Then("the activation status should be successful")
    public void the_activation_status_should_be_successful() {
        assertNotNull(checkStatusResponse);
        assertEquals(HttpStatus.OK, checkStatusResponse.getStatusCode());
        // Assuming the response contains a "status" field
        assertEquals("successful", checkStatusResponse.getBody());
    }

    @Then("the activation status should fail")
    public void the_activation_status_should_fail() {
        assertNotNull(checkStatusResponse);
        assertEquals(HttpStatus.OK, checkStatusResponse.getStatusCode());
        // Assuming the response contains a "status" field
        assertEquals("failed", checkStatusResponse.getBody());
    }
}