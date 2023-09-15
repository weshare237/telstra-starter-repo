package au.com.telstra.simcardactivator.services;

import au.com.telstra.simcardactivator.models.NewSimCardRequest;
import au.com.telstra.simcardactivator.models.SimCard;
import au.com.telstra.simcardactivator.models.SimCardDTO;
import au.com.telstra.simcardactivator.models.SimCardDTOMapper;
import au.com.telstra.simcardactivator.repositories.SimCardRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;


@Service
public class SimCardActivatorService {

    private final SimCardRepository simCardRepository;
    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(SimCardActivatorService.class);

    private final SimCardDTOMapper simCardDTOMapper;

    public SimCardActivatorService(SimCardRepository simCardRepository, RestTemplate restTemplate, SimCardDTOMapper simCardDTOMapper) {
        this.simCardRepository = simCardRepository;
        this.restTemplate = restTemplate;
        this.simCardDTOMapper = simCardDTOMapper;
    }

    public Long activateSimCard(@RequestBody NewSimCardRequest simCardRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the JSON payload
        String jsonPayload = "{\"iccid\": \"" + simCardRequest.getIccid() + "\"}";

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

                SimCard simCard = new SimCard();
                simCard.setIccid(simCardRequest.getIccid());
                simCard.setActive(successValue);
                simCard.setCustomerEmail(simCardRequest.getCustomerEmail());
                return simCardRepository.save(simCard).getId();
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return null;
    }

    public SimCardDTO getSingleSimCard(Long simCardId) {
        return simCardRepository.findById(simCardId)
                .map(simCardDTOMapper)
                .orElseThrow(() -> new IllegalStateException("No sim card with id: " + simCardId));
    }
}
