package au.com.telstra.simcardactivator.models;

import java.util.Objects;

public class SimCard {

    private String iccid;

    private String customerEmail;

    public SimCard(String iccid, String customerEmail) {
        this.iccid = iccid;
        this.customerEmail = customerEmail;
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
