package au.com.telstra.simcardactivator.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class SimCard {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String iccid;

    private String customerEmail;
    private boolean active;

    public SimCard() {
    }

    public SimCard(String iccid, String customerEmail, boolean active) {
        this.iccid = iccid;
        this.customerEmail = customerEmail;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimCard simCard = (SimCard) o;
        return Objects.equals(iccid, simCard.iccid) && Objects.equals(customerEmail, simCard.customerEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iccid, customerEmail);
    }
}
