package au.com.telstra.simcardactivator.controllers;

import au.com.telstra.simcardactivator.models.SimCard;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("api/v1/sim-cards")
public class SimCardActivatorController {

    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(SimCardActivatorController.class);

    public SimCardActivatorController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public void activateSimCard(@RequestBody SimCard simCard) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the JSON payload
        String jsonPayload = "{\"iccid\": \"" + simCard.getIccid() + "\"}";

        // Create a RequestEntity with the JSON payload and headers
        RequestEntity<String> requestEntity = new RequestEntity<>(jsonPayload, headers, HttpMethod.POST,
                URI.create("http://localhost:8444/actuate"));

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                requestEntity,
                String.class
        );

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseEntity.getBody());
            JsonNode successNode = rootNode.get("success");
            if (successNode != null) {
                boolean successValue = successNode.asBoolean();

                if(successValue) {
                    logger.info("Sim card successfully activated");
                } else {
                    logger.info("Sim card not successfully activated");
                }
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }

    }
}
