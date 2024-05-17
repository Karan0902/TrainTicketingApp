package trainTicketApp.model;

import lombok.Data;

@Data
public class PurchaseRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String fromLocation;
    private String toLocation;

    public PurchaseRequest(String firstName, String lastName, String email, String fromLocation, String toLocation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
    }
}
