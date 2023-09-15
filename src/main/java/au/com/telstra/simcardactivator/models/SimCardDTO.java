package au.com.telstra.simcardactivator.models;

public class SimCardDTO {
    private String iccid;

    private String customerEmail;

    private boolean active;

    public SimCardDTO() {
    }

    public SimCardDTO(String iccid, String customerEmail, boolean active) {
        this.iccid = iccid;
        this.customerEmail = customerEmail;
        this.active = active;
    }

    public String getIccid() {
        return iccid;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }


    public boolean isActive() {
        return active;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
