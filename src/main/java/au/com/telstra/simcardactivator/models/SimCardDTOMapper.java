package au.com.telstra.simcardactivator.models;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SimCardDTOMapper implements Function<SimCard, SimCardDTO> {
    @Override
    public SimCardDTO apply(SimCard simCard) {
        return new SimCardDTO(
                simCard.getIccid(),
                simCard.getCustomerEmail(),
                simCard.isActive()
        );
    }
}
