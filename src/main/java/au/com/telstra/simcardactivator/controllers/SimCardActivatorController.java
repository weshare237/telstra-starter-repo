package au.com.telstra.simcardactivator.controllers;

import au.com.telstra.simcardactivator.models.NewSimCardRequest;
import au.com.telstra.simcardactivator.models.SimCard;
import au.com.telstra.simcardactivator.models.SimCardDTO;
import au.com.telstra.simcardactivator.services.SimCardActivatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/v1/sim-cards")
public class SimCardActivatorController {

    private final SimCardActivatorService simCardActivatorService;

    public SimCardActivatorController(SimCardActivatorService simCardActivatorService) {
        this.simCardActivatorService = simCardActivatorService;
    }

    @PostMapping
    public ResponseEntity<Long> activateSimCard(@RequestBody NewSimCardRequest simCardRequest) {
        return new ResponseEntity<>(simCardActivatorService.activateSimCard(simCardRequest), CREATED);
    }

    @GetMapping("{simCardId}")
    public ResponseEntity<SimCardDTO> getSingleSimCard(@PathVariable("simCardId") Long simCardId) {
        return new ResponseEntity<>(simCardActivatorService.getSingleSimCard(simCardId), OK);
    }
}
